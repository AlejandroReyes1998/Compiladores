
%{
#include <stdio.h>
#include <stdlib.h>
#include "math.h"
#include "complejo_cal.h"
#define MSDOS

#define code2(c1, c2)     code(c1); code(c2);
#define code3(c1, c2, c3) code(c1); code(c2); code(c3);

void yyerror (char *s);
int yylex ();
void warning(char *s, char *t);
void execerror(char *s, char *t);
void fpecatch();
extern double Pow(double, double);
extern void init();

%}
%union {
	ComplejoAP val;
	Symbol *sym;
	Inst *inst;
	double compo;
}

%token <compo> NUMBER
%type <val> complejoE complejo asgn
%token <sym>  VAR BLTIN INDEF
%right '='
%left '+' '-'
%left '*' '/'
%left UNARYMINUS
%right '^'

%%
	//Sección de reglas, adaptamos lavas variables para la máquina de pila
      list:   
	| list'\n'
	| list asgn'\n' {code2(pop, STOP); return 1; }
          | list complejoE '\n'  { code2(print, STOP); return 1;}
          | list error '\n'	{ yyerrok; }
	;
	
      asgn:VAR '=' complejoE { code3(varpush, (Inst)$1, assign);}
	;
    

complejo: NUMBER '+' NUMBER 'i' {$$=install("",NUMBER,creaComplejo($1,$3));}
	| NUMBER '-' NUMBER 'i' {$$=creaComplejo($1,-$3);}
    	;

      complejoE: complejo { code2(constpush, (Inst)$1);}
	     | VAR             { code3(varpush, (Inst)$1, eval);}
	     | asgn
	     | BLTIN  '(' complejoE ')' { code2(bltin, (Inst)$1->u.ptr);}
	     | complejoE '+' complejoE {code(add); }
               | complejoE '-' complejoE {code(sub);}
	     | complejoE '*' complejoE {code(mul);}
	     | complejoE '/' complejoE {code(divC);}
	     | complejoE '^' NUMBER { code(power);}
	     //| '(' complejoE ')'
	    // | '-'  %prec UNARYMINUS { code(negate); }
               ;

%%

#include <stdio.h>
#include <ctype.h>
#include <signal.h>
#include <setjmp.h>

jmp_buf begin;
char *progname;
int lineno = 1;

void main (int argc, char *argv[]){
	progname=argv[0];
	init();
	setjmp(begin);
	signal(SIGFPE, fpecatch);
	for(initcode(); yyparse (); initcode())
		execute(prog);
  	return 0;
}
void execerror(char *s, char *t){
	warning(s, t);
	longjmp(begin, 0);
}

void fpecatch(){
	execerror("excepcion de punto flotante", (char *)0);
}
int yylex (){
  	int c;
  	while ((c = getchar ()) == ' ' || c == '\t')  
  		;
 	if (c == EOF)                            
    		return 0;
  	if (c == '.' || isdigit (c))                
    	{
      		ungetc (c, stdin);
      		scanf ("%lf", &yylval.compo);
      		//printf("NUMBER: (%g) ",yylval.compo);
               
	      	return NUMBER;
    	}
	if(isalpha(c)&& c!='i'){ //c=69 parte de complejo i
		Symbol *s;
		ComplejoAP inicioC;
		char sbuf[200], *p=sbuf;
		do {
			*p++=c;
		} while ((c=getchar())!=EOF && isalnum(c));
		ungetc(c, stdin);
		*p='\0';
		if((s=lookup(sbuf))==(Symbol *)NULL)
			s=install(sbuf, INDEF,inicioC);
		yylval.sym=s;   
                if(s->type == INDEF){
			return VAR;
                } else {
 		//printf("func=(%s) tipo=(%d) \n", s->name, s->type);
                        return s->type;
                }
	}
  	if(c == '\n'){
                
		lineno++;
        }
  	return c;                                
}
void yyerror (char *s) {
	warning(s, (char *) 0);
}
void warning(char *s, char *t){
	fprintf (stderr, "%s: %s", progname, s);
	if(t)
		fprintf (stderr, " %s", t);
	fprintf (stderr, "cerca de la linea %d\n", lineno);
}
Complejo *creaComplejo(double real, double img){
   Complejo *nvo = (Complejo*)malloc(sizeof(Complejo));
   nvo -> real = real;
   nvo -> img = img;
   return nvo;
}
