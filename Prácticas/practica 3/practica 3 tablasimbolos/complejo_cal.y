
%{
#include <stdio.h>
#include <stdlib.h>
#include "complejo_cal.h"
#include "math.h"

void yyerror (char *s);
int yylex ();
void warning(char *s, char *t);
void execerror(char *s, char *t);
void fpecatch();
extern double Pow(double, double);

%}
%union {
	ComplejoAP val;
	Symbol *sym;
	double compo;
}

%token <compo> NUMBER
%token <sym> VAR BLTIN INDEF
%type <val> complejoE complejo asgn
%right '='
%left '+' '-'
%left '*' '/'
%left UNARYMINUS
%right '^'

%%

      list:
	| list'\n'
	| list asgn'\n'
          | list complejoE '\n'  {  imprimirC($2);}
          | list error '\n'	{ yyerrok; }
	;
	//Asignamos la variable a un determinado complejo que el usuario ingresó
      asgn:VAR '=' complejoE { $$=$1->u.val=$3; $1->type=VAR;}//variable 1
	;


complejo: NUMBER '+' NUMBER 'i' {$$=creaComplejo($1,$3);}
	| NUMBER '-' NUMBER 'i' {$$=creaComplejo($1,-$3);}
    	;
    	//Utilizamos las variables para realizar las operaciones basadas en un símbolo determinado
    	//En caso de que no se haya definido la variable aún le mandamos la advertencia
      complejoE: complejo { $$ = $1;}
	     | VAR { if($1->type == INDEF)
			execerror("variable no definida",$1->name);
			$$=$1->u.val;}//variable 2
	     | asgn
	     | BLTIN  '(' complejoE ')' { $$=(*($1->u.ptr))($3);}
	     | complejoE '+' complejoE {$$ = Complejo_add($1,$3);}
               | complejoE '-' complejoE {$$ = Complejo_sub($1,$3);}
	     | complejoE '*' complejoE {$$ = Complejo_mul($1,$3);}
	     | complejoE '/' complejoE {$$ = Complejo_div($1,$3);}
	     | complejoE '^' NUMBER { $$=Complejo_pow($1, $3);}
	     | '(' complejoE ')' {$$ = $2;}
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
  	yyparse ();
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
                //puts("NUMBER");
	      	return NUMBER;
    	}
    	//Se busca dentro de la tabla de símbolos y en caso de que no esté se agrega para su uso posterior
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
			s=install(sbuf, INDEF,inicioC);//tabla symb
		yylval.sym=s;
                if(s->type == INDEF){
			return VAR;
                } else {
 		//printf("func=(%s) tipo=(%d) \n", s->name, s->type);
                        return s->type;
                }
	}
  	if(c == '\n'){
                //puts("enter");
		lineno++;
        }
        /*if( c== '(')
		puts("(");
        if( c== ')')
		puts(")");*/
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
Complejo *Complejo_add(Complejo *c1, Complejo *c2){
  return creaComplejo(c1->real + c2->real, c1->img + c2->img);
}
Complejo *Complejo_sub(Complejo *c1, Complejo *c2){
  return creaComplejo(c1->real - c2->real, c1->img - c2->img);
}
Complejo *Complejo_mul(Complejo *c1, Complejo *c2){
  return creaComplejo( c1->real*c2->real - c1->img*c2->img,
                       c1->img*c2->real + c1->real*c2->img);
}
Complejo *Complejo_div(Complejo *c1, Complejo *c2){
   double d = c2->real*c2->real + c2->img*c2->img;
   return creaComplejo( (c1->real*c2->real + c1->img*c2->img) / d,
                        (c1->img*c2->real - c1->real*c2->img) / d);
}

Complejo *Complejo_pow(Complejo *c, double n){
  double norma=pow(sqrt((c->real*c->real)+(c->img*c->img)),n);

   return  creaComplejo( (norma*cos(n*atan2(c->img,c->real))),
                          (norma*sin(n*atan2(c->img,c->real)))
                        );
}
void imprimirC(Complejo *c){
   if(c->img != 0)
      printf("%f%+fi\n", c->real, c->img);
   else
      printf("%f\n", c->real);
}
