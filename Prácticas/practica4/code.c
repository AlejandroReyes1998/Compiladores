#include "complejo_cal.h" 
#include "y.tab.h"
#include "math.h"
#define NSTACK  256
static  Datum  stack[NSTACK];  /* Declaramos la pila */
static  Datum   *stackp;       /*Apuntador a PIla*/
#define NPROG   2000
Inst    prog[NPROG];    /*Máquina que utilizará la pila*/
Inst    *progp;         /*Apuntador a máquina para generación de código*/
Inst    *pc;	/*Contador de programa que se ejecutará durante el funcionamiento del programa*/


initcode()      /*Generación de código*/ {
stackp = stack;
progp = prog;
}

push(d)	/*  meter d en la pila  */
Datum d; 
{
if (stackp >= &stack[NSTACK])
execerror("stack overflow", (char *) 0);
*stackp++ = d;
}

Datum pop( )     /*Sacamos el elemento del tope de la pila y lo retnornamos*/
{
if (stackp <= stack)
execerror("stack underflow", (char *) 0);
return  *--stackp;
}


constpush( )	/*Se ingresa un número complejo a la pila*/
{
Datum d;
d.val  =  ((Symbol  *)*pc++)->u.val;
push(d);
}

varpush()	/* meter una variable a la pila   */
{
Datum d;
d.sym  =  (Symbol   *)(*pc++);
push(d);
}

eval( )	/*  evaluar una variable en la pila   */
{
Datum  d;
d   =  pop();
if   (d.sym->type   ==   INDEF)
execerror("undefined variable",   
d.sym->name); 
d.val   =  d.sym->u.val; push(d);
}

add( )	/*   sumar los dos elementos superiores de la pila   */
{
Datum d1,   d2,d3; 
d2  =  pop(); 
d1   =  pop(); 
d3 = sumaC(d1,d2);
push(d3); 
}
sumaC(d1,d2){
  d1.val->real= d1.val->real+  d2.val->real;
d1.val->img= d1.val->img+  d2.val->img;
return d1;
}

sub()
{
Datum d1,  d2,d3; 
d2  = pop(); 
d1  = pop(); 
d3 = restaC(d1,d2);
push(d3);
}
restaC(d1,d2){
d1.val->real= d1.val->real -  d2.val->real;
d1.val->img= d1.val->img - d2.val->img;
return d1;
}

mul()
{
Datum d1, d2;
ComplejoAP comp;
d2 = pop(); 
d1 = pop(); 
comp=mulC(d1,d2);
push(comp);
}
mulC(d1,d2){
ComplejoAP comp;
comp=creaComplejo(d1.val->real*d2.val->real-d1.val->img*d2.val->img,d1.val->img*d2.val->real+d1.val->real*d2.val->img);
return comp;
}
divC( )
{
Datum d1, d2;
ComplejoAP comp;
d2 = pop();
//if (d2.val == 0.0)
//execerror("division by zero", (char *)0);
double d = d2.val->real*d2.val->real + d2.val->img*d2.val->img;
d1 = pop(); 
comp=dC(d1,d2);
push(comp);
}
dC(d1,d2){
ComplejoAP comp;
comp=creaComplejo( (d1.val->real*d2.val->real + d1.val->img*d2.val->img) / d,(d1.val->img*d2.val->real - d1.val->real*d2.val->img) / d);
return comp;
}


negate()
{
Datum d; 
d = pop(); 
d.val->real  =  -d.val->real;
d.val->img  =  -d.val->img; 
push(d);
}

power()
{
Datum d1;

double n=3;
 double norma=pow(sqrt((d1.val->real*d1.val->real)+(d1.val->img*d1.val->img)),n);

d1 = pop();
d1.val->real =  (norma*cos(n*atan2(d1.val->img,d1.val->real)));
d1.val->img = (norma*sin(n*atan2(d1.val->img,d1.val->real)));
push(d1);
}

assign( )        /* asignar el valor superior al siguientevalor */ 
{
Datum d1, d2;
d1 = pop();
d2 = pop();
if (d1.sym->type != VAR && d1.sym->type != INDEF) 
execerror("assignment to non-variable", d1.sym->name);
d1.sym->u.val = d2.val;
d1.sym->type = VAR;
push(d2); 
} 

print( )  /* sacar el valor superior de la pila e imprimirlo */ 
{
Datum d;
d = pop();
printf("\t%f%+fi\n", d.val->real, d.val->img);
}

bltin( )/*  evaluar un predefinido en el tope de la pila  */
{
Datum d;
d  =  pop();
d.val  =   (*(ComplejoAP   (*)())(*pc++))(d.val);
push(d);
}
 

Inst   *code(Inst f) /*   instalar una instrucción u operando   */
{
Inst *oprogp = progp;
	if (progp >= &prog [ NPROG ])
		execerror("program too big", (char *) 0);
	*progp++ = f;
	return oprogp;
}

execute(Inst p)	/*   ejecución con la máquina   */
{
for  (pc  =  p;   *pc != STOP; ) 
	(*(*pc++))();
}

