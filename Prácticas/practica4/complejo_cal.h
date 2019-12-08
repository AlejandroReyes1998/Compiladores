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
typedef int (*Inst)();  /* instrucción de máquina */ 

Complejo *creaComplejo(double real, double img);
typedef union Datum {   /* tipo de la pila del intérprete */
ComplejoAP  val;
Symbol  *sym; } Datum; 

extern Datum pop();

#define STOP    (Inst) 0
extern	Inst prog[];
extern	eval(), add(), sub(), mul(), divC(), negate(), power();
extern	assign(), bltin(), varpush(), constpush(), print();

