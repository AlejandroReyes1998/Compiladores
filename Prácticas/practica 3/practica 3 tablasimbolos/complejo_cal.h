#include <string.h>
struct complejo {
  double real, img;
};
typedef struct complejo Complejo;
typedef struct complejo *ComplejoAP;

typedef struct Symbol { /* entrada de la tabla de símbolos */
	char   *name;
	short   type;   /* VAR, BLTIN, UNDEF */
	union {
		ComplejoAP val;	       /* si es VAR */
		ComplejoAP  (*ptr)();      /* sí es BLTIN */
	} u;
	struct Symbol   *next;  /* para ligarse a otro */ 
} Symbol;

Symbol *install(char *s,int t, ComplejoAP d), *lookup(char *s);

/*  prototypes of the provided functions */ 
Complejo *creaComplejo(double real, double img);
Complejo *Complejo_add(Complejo *, Complejo *);
Complejo *Complejo_sub(Complejo *, Complejo *);
Complejo *Complejo_mul(Complejo *, Complejo *);
Complejo *Complejo_div(Complejo *, Complejo *);
void imprimirC(Complejo *c);
Complejo *Complejo_pow(Complejo *, double);
/*  prototypes of the provided functions */ 
/* define the return type of FLEX */

