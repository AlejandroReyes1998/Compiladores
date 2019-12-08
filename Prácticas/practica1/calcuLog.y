%{
 #define YYSTYPE int
%}
//Operadores
%left 'v' //OR
%left '^' //AND
%left UNARYMINUS //NOT


%%
list:	
			| list '\n'
			| list exp '\n' {printf("Resultado: %d\n", $2);}
			;
exp:	  		't'						{$$ = $1;}
			| 'f'						{$$ = $1;}
			| exp '^' exp 	{$$ = funcionAND($1, $3);printf("AND\n");}
			| exp 'v' exp   {$$ = funcionOR($1, $3);printf("OR\n");}
			| '(' exp ')'		{$$ = $2;} //Parentesis
			| '-' exp %prec UNARYMINUS {$$ = funcionNOT($2);printf("NOT\n");}
			;
%%

#include <ctype.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void main() { yyparse(); }

int yylex() {
	int c;
//Asignamos los valores para t y f
	while ( (c = getchar()) == ' ' || c == '\t')
		;
	
	if (c == EOF)
		return 0;
	//t
	if (c == 't') {
		yylval = 1;
	}
	//f
	if (c == 'f') {
		yylval = 0;
	}

	return c;
}
//El usuario introduce un operando no contemplado anteriormente
void yyerror(char *s)   /* called for yacc syntax error */
{
    warning(s, (char *)0);
}

void warning(char *s, char *t)  /* print warning message */
{
    printf("Operación Inválida u Operando inválido\n");
}
//AND
int funcionAND(int op1, int op2) {
	return op1 && op2;
}
//OR
int funcionOR(int op1, int op2) {
	return op1 || op2;
}
//NOT.
int funcionNOT(int op) {
	return !op;
}
