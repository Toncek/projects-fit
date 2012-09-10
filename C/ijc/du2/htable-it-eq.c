/*
	filename: htable-it-eq.c
	meno: Kontra Matus	
	fakulta: FIT
	uloha: 2 - B
	17.4.2008
	compiler: GCC-4.2.3
*/

#include <stdlib.h>
#include "htable.h"

/*   htable_it_eq
	-vracia nenulovu hodnotu ak sa iteratory rovnaju
*/

inline
int
htable_it_eq ( htable_iterator i1 , htable_iterator i2 )
{


return (i1.dest_table == i2.dest_table && i1.index==i2.index && i1.curr_ptr == i2.curr_ptr);


}

