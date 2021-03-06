#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <signal.h>
#include <time.h>
//#include <fcntl.h>
#include <unistd.h>
//#include <string.h>

 
/* definicie potrebnych datovych struktur */

// clen fronty - stacil by int toto je vsak vseobecnejsie
typedef struct intitem {
	int data;		
} int_queueitem_t , *int_queueitem_ptr;
	

// struktura fronty 
typedef struct squeue {
	int ctlflag;		//kontrolny priznak - na ukoncenie
	int curr_item;		//ukazuje na sucasny prvok vo fronte
	int items;		//pocet poloziek vo fronte pri sucasnom prechode	
	int begin_offset;	//offset do zdielanej pamete kde je umiestneny prvy prvok (najcastejsie hned za touto strukturou)
} shared_queue_t, *shared_queue_ptr;

// struktura pouzita na posunutie parametrov producentovi - potrebujeme iba identifikator bloku
// zdielanej paete po attachnuti si zvysne data vytiahneme sami
typedef struct pargs {
	int maxnums;		//maximalny pocet poloziek vo fronte - M
	int shmem_id;
} producer_args_t, *producer_args_ptr;

// parametre pre konzumenta - id aidentifikator bloku
typedef struct cargs {
	int consumer_id;
	int shmem_id;
} consumer_args_t, *consumer_args_ptr;

//struktura obsahuje pointre na dynamicky alokovane zdroje v hlavnom programe
//ktore v dcerskych procesoch nie su potrebne
//v nasom pripade kde sa jedna o pole ukazatelov malej dlzky je to v podstate zanedbatelny problem
//avsak v inych pripadoch by sa mohlo jednat o kriticky memory-leak
typedef struct dynres {	
	void * consumer_id_table;
} dyn_rscs_t , * dyn_rscs_ptr;

/* atomicke premenne ktore su updatovne v handleroch na notifikaciu 
   vlastnych funkcii cakajucich na signal
*/

static sig_atomic_t producer_status_change = 0; //atomicka premenna notifikujuca producenta program o zmene

static sig_atomic_t consumer_status_change = 0; //atomicka premenna notifikujuca konzumenta program o zmene

static sig_atomic_t main_usr1_rcvd = 0; //atomicka premenna notifikujuca hlavny program o SIGUSR1

static sig_atomic_t main_usr2_rcvd = 0; //atomicka premenna notifikujuca hlavny program o SIGUSR2

/* handlery - vpodstate len zmenia hodnotu premennej */

void producer_SIGUSR1_handler ( int sig )
{	
	producer_status_change = 1; //updatneme premennu
}

void consumer_SIGUSR1_handler ( int sig )
{	
	consumer_status_change = 1; //updatneme premennu
}

void main_SIGUSR1_handler ( int sig )
{	
	main_usr1_rcvd = 1; //updatneme premennu
}

void main_SIGUSR2_handler ( int sig )
{	
	main_usr2_rcvd = 1; //updatneme premennu
}

void main_SIGTERM_handler ( int sig )
{	
	kill(0,SIGKILL);	//zabi vsetkych potomkov a skonci
}

/*funkcia ktora vytvory novy proces a spusti v nom definovanu funkciu */

pid_t spawnProcess ( int (*task) ( void * ) , void * proc_args , dyn_rscs_ptr main_dyns)
{
	pid_t child_pid;

	child_pid = fork ();
	
	if (child_pid == -1) {
	    perror("Chyba pri volani fork(): ");
	    if ( kill( 0, SIGTERM ) == -1 ) perror("Chyba pri volani kill() ");
	    exit(1);
	}
	else if (child_pid != 0)
		return child_pid;
	else {

	if ( main_dyns->consumer_id_table != NULL ) free ( main_dyns->consumer_id_table );

	int errval = (*task) ( proc_args );

	if ( errval != 0 )
	{
		fprintf(stderr, "Chyba pri vykonavani ulohy\n");
		exit (1);
	}

	exit (0);

	}
}


/* funkcia producenta */

int producer_function ( void * proc_args )
{
	setbuf(stdout,NULL);

	producer_args_ptr parg = ( producer_args_ptr ) proc_args;		//prevezmeme argumenty z mainu()

	void * shared_buff = shmat ( parg->shmem_id , NULL , 0 );		//attachneme zdielanu pamet

	if ( (int) shared_buff == -1 ) { perror("Chyba pr shmat(): ");kill(getppid(),SIGTERM); exit(1);}

	shared_queue_ptr queue_strct = ( shared_queue_ptr ) shared_buff;	//najdeme frontu

	int_queueitem_ptr first_item = ( int_queueitem_ptr ) (shared_buff + queue_strct->begin_offset); // a prvy prvok

	struct sigaction setup_action;		//struktura akcie
        sigset_t block_mask, oldmask;		//pomocne masky

        sigemptyset (&block_mask);		//vycistime ju
	sigaddset (&block_mask, SIGUSR1);	//pridame SIGUSR1 - nechceme aby pocas spracovania handleru prisiel dalsi signal
						//pri nizkej zlozitosti nasho handleru nepotrebne ale vhodne

	/*struct shmid_ds stat;
	shmctl(parg->shmem_id, IPC_STAT, &stat);
	printf("Velkost attachu: %d\n",stat.shm_segsz);
	getc(stdin);*/

        setup_action.sa_handler = producer_SIGUSR1_handler; 	//nastavime funkciu handleru
        setup_action.sa_mask = block_mask;			//masku blokovania
        setup_action.sa_flags = 0;				//no flags
     
	if ( sigaction (SIGUSR1, &setup_action, NULL) == -1 ) { perror("Chyba pri volani sigaction(): "); kill( getppid(),SIGTERM);exit (1);}   ;

	int times = ( rand() % 4 ) + 1;

	kill ( getppid(), SIGUSR2 );	//notifikuj rodica o tom ze sme pripraveny

	/* hlvny cyklus */
	while ( 1 )		
	{	
		sigprocmask (SIG_BLOCK, &block_mask, &oldmask);	//zablokujeme SIGUSR1 v cykle 
    	
	 	while (!producer_status_change)		//cakame na signal SIGUSR1
      			 sigsuspend (&oldmask);		//cakame v cykle na to kym handler updatene premennu
							//nakolko process aj napriek tomu ze sme zablokovali vsetky signali okrem tohto
							//prebudeny aj signalmy ktore sa blokovat nedaju -> napr.: kontrola uloh
		
		producer_status_change = 0;

		if ( times == 0 ) break;

		//vygeneruj obsah pamete
		int pocet = ( rand() % parg->maxnums ) + 1;	//pocet prvkov tejto iteracie
		
		queue_strct->items = pocet;				
		queue_strct->curr_item = 0;

		printf("producent : ");

		int i = 0 ;
		while ( 1 )
		{
			int_queueitem_ptr entry =  (int_queueitem_ptr ) (first_item + i);
			entry -> data = i + 1;		//naplnenie fronty
			printf ("%d", entry->data );
			if ( i == pocet - 1 )
				break;
		printf(", ");
		i++;
		}
		times--;
		sigprocmask (SIG_UNBLOCK, &block_mask, NULL);
		
		printf("\n");

		kill ( getppid(), SIGUSR1 ); //kill pre hlavny proces -> notifikacia o naplneni fronty
	}

	if ( shmdt (shared_buff ) == -1 ) { perror("Chyba pri odpajani zdielanej pamete\n");}

	printf("producent : finished\n");

	kill ( getppid(), SIGUSR2 ); //signal o ukonceni cinnosti

	return 0;
}

int consumer_function ( void * proc_args )
{
    	setbuf(stdout,NULL);

	consumer_args_ptr carg = ( consumer_args_ptr ) proc_args;		//prevezmeme argumenty z mainu()

	int consumer_id = carg->consumer_id;
	
	void * shared_buff = shmat ( carg->shmem_id , NULL , 0 );		//attachneme zdielanu pamet

	if ( (int) shared_buff == -1 ) { perror("Chyba pri volani shmat(): "); kill(getppid(),SIGTERM);exit (1);}

	shared_queue_ptr queue_strct = ( shared_queue_ptr ) shared_buff;	//najdeme frontu

	int_queueitem_ptr first_item = ( int_queueitem_ptr ) (shared_buff + queue_strct->begin_offset ); // a prvy prvok

	struct sigaction setup_action;		//struktura akcie
        sigset_t block_mask, oldmask;		//pomocne masky

        sigemptyset (&block_mask);		//vycistime ju
	sigaddset (&block_mask, SIGUSR1);	//pridame SIGUSR1 - nechceme aby pocas spracovania handleru prisiel dalsi signal
						//pri nizkej zlozitosti nasho handleru nepotrebne ale vhodne

        setup_action.sa_handler = consumer_SIGUSR1_handler; 	//nastavime funkciu handleru
        setup_action.sa_mask = block_mask;			//masku blokovania
        setup_action.sa_flags = 0;				//no flags
     

	if ( sigaction (SIGUSR1, &setup_action, NULL) == -1 ) { perror("Chyba pri volani sigaction(): "); kill(getppid(),SIGTERM);exit (1);}   ;

	kill ( getppid(), SIGUSR2 );	//signal o tom ze sme pripraveny

	while ( 1 )		
	{	
		 sigprocmask (SIG_BLOCK, &block_mask, &oldmask);	//zablokujeme SIGUSR1 v cykle 
    	
	 	while (!consumer_status_change)		//cakame na signal SIGUSR1
      			 sigsuspend (&oldmask);		//cakame v cykle na to kym handler updatene premennu
							//nakolko process aj napriek tomu ze sme zablokovali vsetky signali okrem tohto
							//prebudeny aj signalmy ktore sa blokovat nedaju -> napr.: kontrola uloh
		
		consumer_status_change = 0;

		if ( queue_strct->ctlflag == 1 ) break;	//podmienka pre ukoncenie

		printf("consument %d : ", consumer_id );	

		int i;

		for ( i = queue_strct->curr_item ; ( i < ( queue_strct->curr_item + consumer_id ) ) && ( i < queue_strct->items ) ; i++ )
		{
			if ( i != queue_strct->curr_item ) printf (", ");
			int_queueitem_ptr entry =  (int_queueitem_ptr ) (first_item + i);
			printf("%d",entry -> data );
		}	
	
		printf("\n");	

		queue_strct->curr_item = i;

		sigprocmask (SIG_UNBLOCK, &block_mask, NULL);

		kill ( getppid(), SIGUSR1 ); //signal pre hlavny proces -> notifikacia o ukonceni citania
	}

	if ( shmdt (shared_buff ) == -1 ) { perror("Chyba pri odpajani zdielanej pamete\n");}
	
	printf("consument %d : finished\n", consumer_id );

	return 0;
}

int main(int argc, char *argv[])
{
    setbuf(stdout,NULL);

    srand( (unsigned long) time (NULL) );

if ( argc != 3 ) { printf ("Usage: ./proc M N \nM - maximalne cislo\nN - pocet konzumentov\n");exit(1);}

int n=atoi(argv[1]);
int m=atoi(argv[2]);

size_t queue_size = sizeof(shared_queue_t);

size_t shmem_siz = queue_size + (m + 1) * sizeof(int_queueitem_t);

int shmem_id = shmget (IPC_PRIVATE, shmem_siz, IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR | S_IROTH | S_IWOTH);

if ( shmem_id == -1 )
{
    perror("Chyba pri volani shmget(): ");
    exit(1);
}

void * shared_buff = shmat ( shmem_id , NULL , 0 );

shared_queue_ptr queue_strct = ( shared_queue_ptr ) shared_buff;

queue_strct->ctlflag = 0;
queue_strct->curr_item = 0;
queue_strct->items = 0;
queue_strct->begin_offset = queue_size;

//nasatavenie handlerov hlavneho programu

struct sigaction setup_action1, setup_action2, term_action;		//struktura akcie
sigset_t block_mask, oldmask, term_mask;		//pomocne masky

//handler pre sigterm - pouziva sa pri chybovom ukonceni od deti
sigemptyset (&term_mask);
sigaddset (&term_mask, SIGTERM);	//pocas vykonavania handleru blokujeme signal ktory osetrujeme

term_action.sa_handler = main_SIGTERM_handler;
term_action.sa_mask = term_mask;	
term_action.sa_flags = 0;		
		
sigaction (SIGTERM, &term_action, NULL);

sigemptyset (&block_mask);		
sigaddset (&block_mask, SIGUSR1);
sigaddset (&block_mask, SIGUSR2);

setup_action1.sa_handler = main_SIGUSR1_handler;
setup_action1.sa_mask = block_mask;	
setup_action1.sa_flags = 0;		
		
if (sigaction (SIGUSR1, &setup_action1, NULL) == -1 ) {perror("Chyba pri nastavovani handleru:");exit( 1 );};

setup_action2.sa_handler = main_SIGUSR2_handler;
setup_action2.sa_mask = block_mask;	
setup_action2.sa_flags = 0;		
		
if (sigaction (SIGUSR2, &setup_action2, NULL) == -1 ) {perror("Chyba pri nastavovani handleru:");exit( 1 );};

//struktura dyn. zdrojov
//pred vytvorenim producenta sme este ne-malloc-ovali tj naplnime ju nullom

dyn_rscs_t polia = { NULL };

// vytvorenie producenta
producer_args_t parg = { m, shmem_id };

pid_t producer_pid = spawnProcess (  &producer_function, &parg , &polia);	

sigprocmask (SIG_BLOCK, &block_mask, &oldmask);

//cakam kym nas informuje o pripravenosti
while (!main_usr2_rcvd)	//cakame na signal SIGUSR1 alebo SIGUSR2
	sigsuspend (&oldmask);

sigprocmask (SIG_UNBLOCK, &block_mask, NULL);

main_usr2_rcvd = 0;

//vytvorenie consumentov
//pole pid konsumentov vzhladom na ich id

pid_t * consumer_pid_list =  malloc ( n * sizeof(pid_t) );

//dynamicka polozka - treba odstranit
polia.consumer_id_table = consumer_pid_list;

//vytvarame n consumentov
for ( int i = 1 ; i <= n ; i++ )
{
	//argument = id , zdielana pamet
	consumer_args_t carg = { i , shmem_id };

	*( consumer_pid_list + ( i - 1 ) ) = spawnProcess (  &consumer_function, &carg , &polia);

	sigprocmask (SIG_BLOCK, &block_mask, &oldmask);	

	while (!main_usr2_rcvd)	
		sigsuspend (&oldmask);

	sigprocmask (SIG_UNBLOCK, &block_mask, NULL);

	main_usr2_rcvd = 0;

}

while ( 1 ) {

	sigprocmask (SIG_BLOCK, &block_mask, &oldmask);
	
	kill ( producer_pid , SIGUSR1 );

	while ( !main_usr1_rcvd && !main_usr2_rcvd)	//cakame na signal SIGUSR1 alebo SIGUSR2
		sigsuspend (&oldmask);

	if ( main_usr2_rcvd ) break;

	main_usr1_rcvd = 0;

	int next_con_id_index = 0;

	sigprocmask (SIG_UNBLOCK, &block_mask, NULL);

	while ( queue_strct->curr_item != queue_strct->items )
	{
		
		sigprocmask (SIG_BLOCK, &block_mask, &oldmask);
		
	//	printf("Volam konzumenta %d s itemom %d a maximom %d\n",next_con_id_index+1 , queue_strct->curr_item,queue_strct->items);
		kill( * ( consumer_pid_list + ( next_con_id_index % n )) , SIGUSR1);

		while ( !main_usr1_rcvd )	//cakame na signal SIGUSR1 alebo SIGUSR2
			sigsuspend (&oldmask);
		main_usr1_rcvd = 0;

		next_con_id_index++;

		sigprocmask (SIG_UNBLOCK, &block_mask, NULL);
	}


}

queue_strct->ctlflag = 1;

int status = 0;

for ( int i = 1 ; i <= n ; i++ )
{
	kill( * ( consumer_pid_list + ( i - 1 ) ) , SIGUSR1);

	waitpid( *( consumer_pid_list + ( i - 1 )) , &status , 0 );

}

if ( shmdt (shared_buff ) == -1 ) { perror("Chyba pri odpajani zdielanej pamete\n");}

struct shmid_ds shms;

if ( shmctl(shmem_id, IPC_RMID, &shms) == -1 ) { perror("Chyba pri niceni zdielanej pamete\n");};

free (consumer_pid_list);

return 0;
}

