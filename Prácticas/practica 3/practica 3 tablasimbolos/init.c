#include "complejo_cal.h"
#include "y.tab.h"
#include <math.h>

extern Complejo Complejo_sin(), Complejo_cos(), Complejo_exp ( ), Complejo_log();

static struct {	/*	Predefinidos */
char *name;
ComplejoAP	(*func)();
} builtins[] =	{
"sin",	Complejo_sin,
"cos" ,	Complejo_cos,
"exp",  Complejo_exp,
"log",  Complejo_log,
0,0
};

init( )  /* instalar constantes y predefinidos en la tabla */
{
int i;
Symbol *s;
ComplejoAP c;
for (i = 0; builtins[i].name; i++) {
	s = install(builtins[i].name, BLTIN, c);
	s->u.ptr = builtins[i].func;
}
}
