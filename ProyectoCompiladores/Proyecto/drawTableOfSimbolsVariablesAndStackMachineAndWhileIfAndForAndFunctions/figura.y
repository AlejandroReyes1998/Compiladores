%{
	import java.lang.Math;
	import java.io.*;
	import java.util.StringTokenizer;
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import java.util.*;
%}

%token NUMBER LINEA RECTANGULO CIRCULO NOMBRE DIBUJAR MOVER WHILE IF ELSE FOR ESCALAR AND OR NOT PARENT_I
%token GT GE LT LE EQ NE PARENT_D LLAVE_I LLAVE_D SIMB_SEP  COMMA DECREMENTO INCREMENTO RETURN FUNCION SUM RES DIV MUL
%nonassoc ELSE
%start list

%%
list : 
	| funcion list
	;

//creacion de funciones

funcion: func nomFunc PARENT_I argumentos PARENT_D stmt
		{
			maq.code2($1.ival,$2.sval,$4.ival);
		}
		;
func : FUNCION 
		{
			$$ = new ParserVal(maq.code("FUNCION"));
		}
		;
nomFunc : NOMBRE
		{
			maq.code($1.sval);
			$$ = new ParserVal($1.sval);
		}

argumentos : 
			{
				$$ = new ParserVal(0);
			}
			| args
			{
				$$ = $1;
			}
			;

args: 	arg
		{
			$$ = new ParserVal(1);
		}
		| args COMMA arg
		{
			$$ = new ParserVal($1.ival + 1);
		}
		;

arg:NOMBRE
	{
		maq.code($1.sval);
	}
	;

// llamar funciones
llamarFuncion: NOMBRE '=' NOMBRE PARENT_I parametros PARENT_D 
	  {
	  		maq.code("call");
	  		maq.code($3.sval);
	  		maq.code("asignar");
	  		maq.code($1.sval);
	  }
	 |NOMBRE PARENT_I parametros PARENT_D
	  {
	  		maq.code("call");
	  		maq.code($1.sval);
	  		//maq.code("VOID");
	  }
	  ;

parametros: 
			| params;

params:		param;
		  | params COMMA param
		  ;

param: operacion ;
		
operacion: llamarFuncion
		| NUMBER
		{
			maq.code("constpush4");
			maq.code(((Algo)$1.obj).simb);
		}
		| NOMBRE
		{
			maq.code("constpush4");
			maq.code($1.sval);
		}
		| cambioValor
		| operacion SUM llamarFuncion
		{
			maq.code("SUM");
		}
		| operacion SUM NUMBER
		{
			maq.code("constpush4");
			maq.code(((Algo)$3.obj).simb);
			maq.code("SUM");
		}
		| operacion SUM NOMBRE
		{
			maq.code("constpush4");
			maq.code($3.sval);
			maq.code("SUM");
		}
		| operacion RES llamarFuncion
		{
			maq.code("RES");
		}
		| operacion RES NUMBER
		{
			maq.code("constpush4");
			maq.code(((Algo)$3.obj).simb);
			maq.code("RES");
		}
		| operacion RES NOMBRE
		{
			maq.code("constpush4");
			maq.code($3.sval);
			maq.code("RES");
		}
		| operacion MUL llamarFuncion
		{
			maq.code("MUL");
		}
		| operacion MUL NUMBER
		{
			maq.code("constpush4");
			maq.code(((Algo)$3.obj).simb);
			maq.code("MUL");
		}
		| operacion MUL NOMBRE
		{
			maq.code("constpush4");
			maq.code($3.sval);
			maq.code("MUL");
		}
		| operacion DIV llamarFuncion
		{
			maq.code("DIV");
		}
		| operacion DIV NUMBER
		{
			maq.code("constpush4");
			maq.code(((Algo)$3.obj).simb);
			maq.code("DIV");
		}
		| operacion DIV NOMBRE
		{
			maq.code("constpush4");
			maq.code($3.sval);
			maq.code("DIV");
		}
		;

sentencia : inst
		| while PARENT_I cond PARENT_D stmt
		  {
			 	maq.change($3.ival+1,new Integer($5.ival+1)); //stmt
			 	$$ = $5;

		  }
		 | if  PARENT_I cond PARENT_D stmt
		 {
		  		maq.change($3.ival+1,new Integer($5.ival+1));
		  		maq.change($3.ival+2,new Integer($5.ival+1));
			 	$$ = $5;
		 }
		| if PARENT_I cond PARENT_D stmt else stmt
		  {
		  		maq.change($3.ival+1,new Integer($6.ival));
		  		maq.change($3.ival+2,new Integer($7.ival+1));
		  		$$ = $7;
		  }
		| for PARENT_I initfor SIMB_SEP cond SIMB_SEP cambioValFor PARENT_D stmt
			{
				maq.change($1.ival+1,new Integer($3.ival+1));
				maq.change($5.ival+1,new Integer($7.ival+1));
				maq.change($5.ival+2,new Integer($9.ival+2));
				$$ = new ParserVal(maq.code(new Integer($5.ival+3)));
			}
		;

while: WHILE
	{
		$$ = new ParserVal(maq.code("WHILE"));
	}
	;
if: IF
	{
		$$ = new ParserVal(maq.code("IF"));
	}
	;
else: ELSE
	{
		$$ = new ParserVal(maq.code("ELSE"));
	}
	;
for : FOR
	{
		$$ = new ParserVal(maq.code("FOR"));
		maq.code(new String(""));
	}
	;

cond : condicion
		{
			$$ = new ParserVal(maq.code("STOP"));
			maq.code(new String("r"));
			maq.code(new String("r"));
		}
		;
condicion: 	
			PARENT_I condicion PARENT_D AND  PARENT_I condicion PARENT_D	
			{
				maq.code("AND");	
			}
			| PARENT_I condicion PARENT_D OR PARENT_I condicion PARENT_D
			{
				maq.code("OR");
			}
			| NOT PARENT_I condicion PARENT_D				
			{
				maq.code("NOT");
			}
			| 
			exp GT exp 
			{
				maq.code("mayor");
			}
			| exp GE exp
			{
				maq.code("mayorIgual");
			} 				
			| exp LT exp
			{
				maq.code("menor");
			}			
			| exp LE exp 
			{
				maq.code("menorIgual");
			}				
			| exp EQ exp
			{
				maq.code("igual");
			} 				
			| exp NE exp
			{
				maq.code("diferente");
			}			
			;

exp : NUMBER 
			{
				$$ = new ParserVal(maq.code("constpush3"));
				maq.code(((Algo)$1.obj).simb);
			}
	| NOMBRE 
			{
				$$ = new ParserVal(maq.code("constpush3"));
		  		maq.code($1.sval);
		  	}
		  	;

initfor :
		crearNOMBREInt
		{
			$$ = new ParserVal(maq.code("STOP"));
		}
		;

cambioValor: NOMBRE DECREMENTO 
			{
				maq.code("constpush");
		  		maq.code(new Integer(1));
		  		maq.code($2.sval);
				maq.code("decrementa");
			}
		  | NOMBRE INCREMENTO 
		  {
		  		maq.code("constpush");
		  		maq.code(new Integer(1));
		  		maq.code($2.sval);
				maq.code("incrementa");
		  }
		  ;

cambioValFor: cambioValor
				{
					$$ = new ParserVal(maq.code("STOP"));
				}
		;

stmt: 	 inst 
		{
			$$ = new ParserVal(maq.code("STOP"));
		}
		| LLAVE_I instrucciones LLAVE_D
		{
			$$ = new ParserVal(maq.code("STOP"));
		}
		;

instrucciones: 	
			| instrucciones sentencia 
			;	

inst: crearNOMBREInt SIMB_SEP
	  | retorno SIMB_SEP
	  | operacion SIMB_SEP
	  | NOMBRE '=' LINEA NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP
	  { 
	  		maq.code("constpush");
	  		maq.code(new Integer(6));
	  		maq.code($1.sval);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code(((Algo)$5.obj).simb);
	  		maq.code(((Algo)$6.obj).simb);
	  		maq.code(((Algo)$7.obj).simb);
	  		maq.code(((Algo)$8.obj).simb);
	  		maq.code("linea");
	  }
	  | NOMBRE '=' RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP
	  { 
	  		maq.code("constpush");
	  		maq.code(new Integer(6));
	  		maq.code($1.sval);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code(((Algo)$5.obj).simb);
	  		maq.code(((Algo)$6.obj).simb);
	  		maq.code(((Algo)$7.obj).simb);
	  		maq.code(((Algo)$8.obj).simb);
	  		maq.code("rectangulo");
	  }
	  | NOMBRE '=' CIRCULO NUMBER NUMBER NUMBER NUMBER SIMB_SEP
	  { 
	  		maq.code("constpush");
	  		maq.code(new Integer(5));
	  		maq.code($1.sval);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code(((Algo)$5.obj).simb);
	  		maq.code(((Algo)$6.obj).simb);
	  		maq.code(((Algo)$7.obj).simb);
	  		maq.code("circulo");
	  }
	  | DIBUJAR NOMBRE SIMB_SEP
	  {
	  		maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code($2.sval);
	  		maq.code("dibujar");
	  		
	  }
	  | MOVER NOMBRE NUMBER NUMBER SIMB_SEP
	  {
	  		maq.code("constpush");
	  		maq.code(new Integer(3));
	  		maq.code($2.sval);
	  		maq.code(((Algo)$3.obj).simb);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code("mover");
	  }
	  | ESCALAR NOMBRE NUMBER SIMB_SEP
	  {
	  		maq.code("constpush");
	  		maq.code(new Integer(2));
	  		maq.code($2.sval);
	  		maq.code(((Algo)$3.obj).simb);
	  		maq.code("escala");
	  }
      ; 

retorno: RETURN operacion
		{
			maq.code("retorno");
		}
		;
crearNOMBREInt : NOMBRE '=' NUMBER 
			  {
				  	maq.code("constpush");
				  	maq.code(new Integer(2));
				  	maq.code($1.sval);
				  	maq.code(((Algo)$3.obj).simb);
				  	maq.code("varEntera");
			  }
			  ;
%%

class Algo 
{
    Simbolo simb;
    int inst;
    public Algo(int i){ inst=i; }
    public Algo(Simbolo s, int i)
    {
        simb=s;
        inst=i;
    }
}
int pos=0;
Tabla tabla=new Tabla();
Simbolo simbo;
StringTokenizer st;
boolean newline;
static Parser par = new Parser(0);
static JFrame f;
static JTextArea t=new JTextArea();
static JTextArea errs = new JTextArea();
static JButton bcalc;
static JLabel lmuestra=new JLabel("                                 ");
static JPanel editor = new JPanel();
static JPanel bottomPart = new JPanel();
static Canvas canv;
static Graphics g;
static Maquina maq;

int yylex()
{
	String s;
	int tok;
	int d;	
	String aux;
 	if (!st.hasMoreTokens())
 	{
 		if (!newline)
 		{
 			newline=true;
 			return '\n'; 
 		}
		else
			return 0;
	}
 	s = st.nextToken();
 	//System.out.println("Token:\t"+s);
 	//System.out.println(" ");
	
	try 
	{
		d = Integer.parseInt(s);
		yylval = new ParserVal(new Algo(tabla.install("", NUMBER, (double)d),0) );
		tabla.install("", NUMBER, d);
		tok = NUMBER;
	} 
	catch (Exception e)
	{
		simbo = tabla.lookup(s);
		aux = s;
		if(simbo == null && !s.equals("=") && !s.equals(";") )
		{
			tabla.install(s, NOMBRE,0.0);
			yylval = new ParserVal(s);
			tok= NOMBRE;
		}
		else if(simbo != null)
		{
			tok= simbo.tipo;
			if (tok == NOMBRE)
				yylval = new ParserVal(s);
		}
		else
		{
			tok = s.charAt(0);
		}
	}
	return tok;
}

void yyerror(String s)
{
   System.out.println("parser error: "+s + " en la linea "); 
}

Parser(int foo)
{
	f=new JFrame("Calcula");

	f.setLayout(new GridLayout(1,2));
	bcalc=new JButton("Ejecuta");
	bcalc.addActionListener(new ManejaBoton());
	canv=new Canvas();
	canv.setSize(900,600);
	f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
}
public static void main(String args[])
{
	editor.setLayout(new BorderLayout());
	bottomPart.setLayout(new GridLayout(2,1));

	editor.add(t,BorderLayout.CENTER);
	bottomPart.add(errs);
	bottomPart.add(bcalc);
	editor.add(bottomPart,BorderLayout.SOUTH);
	f.getContentPane().add(editor);
	f.getContentPane().add(canv);
	f.setSize( 900, 600);
	f.setVisible(true);
	g=canv.getGraphics(); 
}
  
class ManejaBoton implements ActionListener 
{
   public void actionPerformed(ActionEvent e)
   {
   	    maq = new Maquina(g,errs);
      	JButton jb=(JButton)e.getSource();
     	if(jb == bcalc)
     	{ 
			tabla.install("LINEA", LINEA, 0.0);
			tabla.install("DRAW", DIBUJAR, 0.0);
			tabla.install("CIRCULO", CIRCULO, 0.0);
			tabla.install("RECTANGULO", RECTANGULO, 0.0);
			tabla.install("MOV",MOVER,0.0);

			tabla.install("SCALE", ESCALAR, 0.0);
			tabla.install("while", WHILE, 0.0);
			tabla.install("if", IF, 0.0);
			tabla.install("else", ELSE, 0.0);
			tabla.install("for",FOR,0.0);
			tabla.install(">", GT, 0.0);
			tabla.install("<", LT, 0.0);
			tabla.install("==", EQ, 0.0);
			tabla.install("!=", NE, 0.0);
			tabla.install(">=", GE, 0.0);
			tabla.install("<=", LE, 0.0);
			tabla.install("&&", AND, 0.0);
			tabla.install("||", OR, 0.0);
			tabla.install("!", NOT, 0.0);
			tabla.install("(",PARENT_I,0.0);
			tabla.install(")",PARENT_D,0.0);
			tabla.install("{",LLAVE_I,0.0);
			tabla.install("}",LLAVE_D,0.0);
			tabla.install(";",SIMB_SEP,0.0);
			tabla.install("--",DECREMENTO,0.0);
			tabla.install("++",INCREMENTO,0.0);
			tabla.install("function",FUNCION,0.0);
			tabla.install("return",RETURN,0.0);
			tabla.install(",",COMMA,0.0);
			tabla.install("+",SUM,0.0);
			tabla.install("-",RES,0.0);
			tabla.install("*",MUL,0.0);
			tabla.install("/",DIV,0.0);
			System.out.println(t.getText());
			System.out.println(t.getText().replace("\n"," ").replace("\t"," "));
			st = new StringTokenizer(t.getText().replace("\n"," ").replace("\t"," "));
			newline=false;
			maq.initcode();
			int z;
			while(par.yyparse()!=0)
				z=1;
			maq.code("STOP");
			maq.contenido();
			
			if(maq.buscaMain())
				maq.execute(maq.inicio);
		}
	}
}