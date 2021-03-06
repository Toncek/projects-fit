/*
	filename: htable-it-begin.c
	meno: Kontra Matus	
	fakulta: FIT
	uloha: 2 - B
	17.4.2008
	compiler: GCC-4.2.3
*/

#include <stdlib.h>
#include "htable.h"

/* htable_begin
	-vrati iterator na zaciatok
	-prehladavame indexy a zoznamy kym nenarazime na prvy platny odkaz a ten vratime
	-ak je tabulka prazdna vraciame iterator zhodny s htable_end
*/

htable_iterator
htable_begin ( htable_ptr table )
{

		htable_iterator tmp =
	{
		.dest_table = table,
		.index = 0,
		.curr_ptr = NULL
	};

	while ( tmp.index < table->size )
	{
		if ( *(table->headers + tmp.index) != NULL )
		{
			tmp.curr_ptr = *(table->headers + tmp.index);
			break;
		}	
	tmp.index++;
	}

	return tmp;
}

