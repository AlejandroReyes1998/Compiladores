//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "figura.y"
	import java.lang.Math;
	import java.io.*;
	import java.util.StringTokenizer;
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import java.util.*;
//#line 25 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NUMBER=257;
public final static short LINEA=258;
public final static short RECTANGULO=259;
public final static short CIRCULO=260;
public final static short NOMBRE=261;
public final static short DIBUJAR=262;
public final static short MOVER=263;
public final static short WHILE=264;
public final static short IF=265;
public final static short ELSE=266;
public final static short FOR=267;
public final static short ESCALAR=268;
public final static short AND=269;
public final static short OR=270;
public final static short NOT=271;
public final static short PARENT_I=272;
public final static short GT=273;
public final static short GE=274;
public final static short LT=275;
public final static short LE=276;
public final static short EQ=277;
public final static short NE=278;
public final static short PARENT_D=279;
public final static short LLAVE_I=280;
public final static short LLAVE_D=281;
public final static short SIMB_SEP=282;
public final static short COMMA=283;
public final static short DECREMENTO=284;
public final static short INCREMENTO=285;
public final static short RETURN=286;
public final static short FUNCION=287;
public final static short SUM=288;
public final static short RES=289;
public final static short DIV=290;
public final static short MUL=291;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    2,    3,    4,    4,    6,    6,    7,
    8,    8,    9,    9,   10,   10,   11,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   14,   14,   14,   14,   14,   16,   18,
   19,   20,   17,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   24,   24,   21,   13,   13,   22,    5,    5,
   26,   26,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   27,   25,
};
final static short yylen[] = {                            2,
    0,    2,    6,    1,    1,    0,    1,    1,    3,    1,
    6,    4,    0,    1,    1,    3,    1,    1,    1,    1,
    1,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    5,    5,    7,    9,    1,    1,
    1,    1,    1,    7,    7,    4,    3,    3,    3,    3,
    3,    3,    1,    1,    1,    2,    2,    1,    1,    3,
    0,    2,    2,    2,    2,    9,    9,    8,    3,    5,
    4,    2,    3,
};
final static short yydefred[] = {                         0,
    4,    0,    0,    0,    2,    5,    0,    0,   10,    0,
    0,    8,    0,    0,   19,    0,    0,    0,    0,   61,
    0,    3,   18,    0,   21,   59,    0,    0,    9,    0,
   56,   57,    0,    0,    0,    0,    0,    0,    0,   65,
    0,    0,    0,    0,   63,   64,    0,    0,   15,    0,
   73,    0,    0,    0,    0,   69,    0,    0,   39,   40,
   42,   60,   62,   34,    0,    0,    0,    0,   23,    0,
   22,   26,    0,   25,   32,    0,   31,   29,    0,   28,
   12,    0,    0,    0,    0,    0,    0,   71,    0,    0,
    0,   16,    0,    0,    0,    0,   70,   53,   54,    0,
    0,    0,   43,    0,    0,    0,    0,   55,    0,    0,
    0,   11,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   35,
   47,   48,   49,   50,   51,   52,    0,    0,    0,    0,
   68,   46,    0,    0,   41,    0,    0,   66,   67,    0,
    0,   37,    0,   58,    0,    0,    0,    0,   44,   45,
   38,
};
final static short yydgoto[] = {                          2,
    3,    4,    7,   10,   22,   11,   12,   23,   47,   48,
   49,   24,   25,   63,   26,   65,  102,   66,  146,   67,
  107,  155,  103,  104,   27,   37,   28,
};
final static short yysindex[] = {                      -272,
    0,    0, -272, -244,    0,    0, -246, -226,    0, -231,
 -233,    0, -217, -226,    0,  -61, -182, -174, -170,    0,
 -225,    0,    0, -151,    0,    0, -215, -190,    0, -225,
    0,    0, -250, -189, -177, -158, -243,  -59, -185,    0,
 -202, -200, -199, -184,    0,    0, -179, -171,    0, -185,
    0, -144, -132, -131, -143,    0, -127, -150,    0,    0,
    0,    0,    0,    0, -130, -129, -128, -116,    0,  -60,
    0,    0,  -60,    0,    0,  -60,    0,    0,  -60,    0,
    0, -225, -111,  -94,  -93, -225, -117,    0, -201, -201,
  -95,    0,  -90,  -89,  -88, -108,    0,    0,    0, -102,
 -201, -107,    0, -122, -106,  113, -105,    0,  -82,  -81,
  -79,    0, -201, -100, -217, -183, -183, -183, -183, -183,
 -183, -217,  -77, -201,  -76,  -75,  -99,  -92, -228,    0,
    0,    0,    0,    0,    0,    0,  -80,  -98,  -91,  -87,
    0,    0,  -84,  -83,    0, -217,  -71,    0,    0, -201,
 -201,    0, -219,    0,  -86,  -78,  -74, -217,    0,    0,
    0,
};
final static short yyrindex[] = {                       185,
    0,    0,  185,    0,    0,    0,    0,  -73,    0,    0,
  -72,    0,    0,    0,    0, -141,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -231,
    0,    0,    0,    0,    0,    0,    0, -207,  -68,    0,
    0,    0,    0,    0,    0,    0,    0,  -70,    0, -193,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -194,
    0,    0, -181,    0,    0, -168,    0,    0, -155,    0,
    0,    0,    0,    0,    0, -231,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -234,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                       189,
    0,    0,    0,    0, -109,    0,  180,   75,  110,    0,
  115,  -18,   51,    0,  162,    0,  -85,    0,    0,    0,
    0,    0,  -97,   41,  109,    0,    0,
};
final static int YYTABLESIZE=226;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   68,   68,   39,  114,  105,  130,   51,   52,   53,   54,
   55,   50,  137,   15,    1,  128,    6,   16,   17,   18,
   59,   60,   36,   61,   19,    8,   36,   36,   36,   36,
   36,   15,   36,   36,    9,   38,  152,   62,  138,   15,
  143,  144,   21,   16,   17,   18,   36,   13,  161,   14,
   19,   36,  156,  157,   69,   98,   72,   75,   70,   99,
   73,   76,   20,   50,   31,   32,   45,   50,   21,  100,
  101,   20,   78,   98,   20,   20,   79,   99,   34,   57,
   20,   20,   20,   20,   24,   17,   35,   24,   24,   17,
   36,   46,   56,   24,   24,   24,   24,   27,   58,   81,
   27,   27,   41,   42,   43,   44,   27,   27,   27,   27,
   33,   82,   83,   33,   33,   71,   74,   77,   80,   33,
   33,   33,   33,   30,   84,   85,   30,   30,   86,   87,
   40,   88,   30,   30,   30,   30,   41,   42,   43,   44,
   20,   89,   90,   91,   55,   93,   20,   20,   20,   20,
  116,  117,  118,  119,  120,  121,  131,  132,  133,  134,
  135,  136,   94,   95,   97,  106,  109,  110,  111,  113,
  112,  115,  122,  123,  125,  126,  124,  127,  129,   51,
  139,  140,  141,  147,    1,  145,  142,  150,  151,  153,
  148,    5,  158,   29,  149,   96,   92,  154,   64,  108,
  159,    0,    0,    0,  160,    6,    7,    0,   14,    0,
   30,   30,   30,   72,    0,    0,    0,    0,    0,    0,
    0,    0,   31,   32,   31,   32,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         61,
   61,   61,   21,  101,   90,  115,  257,  258,  259,  260,
  261,   30,  122,  257,  287,  113,  261,  261,  262,  263,
  264,  265,  257,  267,  268,  272,  261,  262,  263,  264,
  265,  257,  267,  268,  261,  261,  146,  281,  124,  257,
  269,  270,  286,  261,  262,  263,  281,  279,  158,  283,
  268,  286,  150,  151,  257,  257,  257,  257,  261,  261,
  261,  261,  280,   82,  284,  285,  282,   86,  286,  271,
  272,  279,  257,  257,  282,  283,  261,  261,  261,  257,
  288,  289,  290,  291,  279,  279,  261,  282,  283,  283,
  261,  282,  282,  288,  289,  290,  291,  279,  257,  279,
  282,  283,  288,  289,  290,  291,  288,  289,  290,  291,
  279,  283,  257,  282,  283,   41,   42,   43,   44,  288,
  289,  290,  291,  279,  257,  257,  282,  283,  272,  257,
  282,  282,  288,  289,  290,  291,  288,  289,  290,  291,
  282,  272,  272,  272,  261,  257,  288,  289,  290,  291,
  273,  274,  275,  276,  277,  278,  116,  117,  118,  119,
  120,  121,  257,  257,  282,  261,  257,  257,  257,  272,
  279,  279,  279,   61,  257,  257,  282,  257,  279,  257,
  257,  257,  282,  282,    0,  266,  279,  272,  272,  261,
  282,    3,  279,   14,  282,   86,   82,  147,   37,   91,
  279,   -1,   -1,   -1,  279,  279,  279,   -1,  279,   -1,
  272,  272,  272,  282,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  284,  285,  284,  285,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=291;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'='",null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"NUMBER","LINEA","RECTANGULO","CIRCULO","NOMBRE",
"DIBUJAR","MOVER","WHILE","IF","ELSE","FOR","ESCALAR","AND","OR","NOT",
"PARENT_I","GT","GE","LT","LE","EQ","NE","PARENT_D","LLAVE_I","LLAVE_D",
"SIMB_SEP","COMMA","DECREMENTO","INCREMENTO","RETURN","FUNCION","SUM","RES",
"DIV","MUL",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : funcion list",
"funcion : func nomFunc PARENT_I argumentos PARENT_D stmt",
"func : FUNCION",
"nomFunc : NOMBRE",
"argumentos :",
"argumentos : args",
"args : arg",
"args : args COMMA arg",
"arg : NOMBRE",
"llamarFuncion : NOMBRE '=' NOMBRE PARENT_I parametros PARENT_D",
"llamarFuncion : NOMBRE PARENT_I parametros PARENT_D",
"parametros :",
"parametros : params",
"params : param",
"params : params COMMA param",
"param : operacion",
"operacion : llamarFuncion",
"operacion : NUMBER",
"operacion : NOMBRE",
"operacion : cambioValor",
"operacion : operacion SUM llamarFuncion",
"operacion : operacion SUM NUMBER",
"operacion : operacion SUM NOMBRE",
"operacion : operacion RES llamarFuncion",
"operacion : operacion RES NUMBER",
"operacion : operacion RES NOMBRE",
"operacion : operacion MUL llamarFuncion",
"operacion : operacion MUL NUMBER",
"operacion : operacion MUL NOMBRE",
"operacion : operacion DIV llamarFuncion",
"operacion : operacion DIV NUMBER",
"operacion : operacion DIV NOMBRE",
"sentencia : inst",
"sentencia : while PARENT_I cond PARENT_D stmt",
"sentencia : if PARENT_I cond PARENT_D stmt",
"sentencia : if PARENT_I cond PARENT_D stmt else stmt",
"sentencia : for PARENT_I initfor SIMB_SEP cond SIMB_SEP cambioValFor PARENT_D stmt",
"while : WHILE",
"if : IF",
"else : ELSE",
"for : FOR",
"cond : condicion",
"condicion : PARENT_I condicion PARENT_D AND PARENT_I condicion PARENT_D",
"condicion : PARENT_I condicion PARENT_D OR PARENT_I condicion PARENT_D",
"condicion : NOT PARENT_I condicion PARENT_D",
"condicion : exp GT exp",
"condicion : exp GE exp",
"condicion : exp LT exp",
"condicion : exp LE exp",
"condicion : exp EQ exp",
"condicion : exp NE exp",
"exp : NUMBER",
"exp : NOMBRE",
"initfor : crearNOMBREInt",
"cambioValor : NOMBRE DECREMENTO",
"cambioValor : NOMBRE INCREMENTO",
"cambioValFor : cambioValor",
"stmt : inst",
"stmt : LLAVE_I instrucciones LLAVE_D",
"instrucciones :",
"instrucciones : instrucciones sentencia",
"inst : crearNOMBREInt SIMB_SEP",
"inst : retorno SIMB_SEP",
"inst : operacion SIMB_SEP",
"inst : NOMBRE '=' LINEA NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : NOMBRE '=' RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : NOMBRE '=' CIRCULO NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : DIBUJAR NOMBRE SIMB_SEP",
"inst : MOVER NOMBRE NUMBER NUMBER SIMB_SEP",
"inst : ESCALAR NOMBRE NUMBER SIMB_SEP",
"retorno : RETURN operacion",
"crearNOMBREInt : NOMBRE '=' NUMBER",
};

//#line 400 "figura.y"

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
//#line 549 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 24 "figura.y"
{
			maq.code2(val_peek(5).ival,val_peek(4).sval,val_peek(2).ival);
		}
break;
case 4:
//#line 29 "figura.y"
{
			yyval = new ParserVal(maq.code("FUNCION"));
		}
break;
case 5:
//#line 34 "figura.y"
{
			maq.code(val_peek(0).sval);
			yyval = new ParserVal(val_peek(0).sval);
		}
break;
case 6:
//#line 40 "figura.y"
{
				yyval = new ParserVal(0);
			}
break;
case 7:
//#line 44 "figura.y"
{
				yyval = val_peek(0);
			}
break;
case 8:
//#line 50 "figura.y"
{
			yyval = new ParserVal(1);
		}
break;
case 9:
//#line 54 "figura.y"
{
			yyval = new ParserVal(val_peek(2).ival + 1);
		}
break;
case 10:
//#line 60 "figura.y"
{
		maq.code(val_peek(0).sval);
	}
break;
case 11:
//#line 67 "figura.y"
{
	  		maq.code("call");
	  		maq.code(val_peek(3).sval);
	  		maq.code("asignar");
	  		maq.code(val_peek(5).sval);
	  }
break;
case 12:
//#line 74 "figura.y"
{
	  		maq.code("call");
	  		maq.code(val_peek(3).sval);
	  		/*maq.code("VOID");*/
	  }
break;
case 19:
//#line 92 "figura.y"
{
			maq.code("constpush4");
			maq.code(((Algo)val_peek(0).obj).simb);
		}
break;
case 20:
//#line 97 "figura.y"
{
			maq.code("constpush4");
			maq.code(val_peek(0).sval);
		}
break;
case 22:
//#line 103 "figura.y"
{
			maq.code("SUM");
		}
break;
case 23:
//#line 107 "figura.y"
{
			maq.code("constpush4");
			maq.code(((Algo)val_peek(0).obj).simb);
			maq.code("SUM");
		}
break;
case 24:
//#line 113 "figura.y"
{
			maq.code("constpush4");
			maq.code(val_peek(0).sval);
			maq.code("SUM");
		}
break;
case 25:
//#line 119 "figura.y"
{
			maq.code("RES");
		}
break;
case 26:
//#line 123 "figura.y"
{
			maq.code("constpush4");
			maq.code(((Algo)val_peek(0).obj).simb);
			maq.code("RES");
		}
break;
case 27:
//#line 129 "figura.y"
{
			maq.code("constpush4");
			maq.code(val_peek(0).sval);
			maq.code("RES");
		}
break;
case 28:
//#line 135 "figura.y"
{
			maq.code("MUL");
		}
break;
case 29:
//#line 139 "figura.y"
{
			maq.code("constpush4");
			maq.code(((Algo)val_peek(0).obj).simb);
			maq.code("MUL");
		}
break;
case 30:
//#line 145 "figura.y"
{
			maq.code("constpush4");
			maq.code(val_peek(0).sval);
			maq.code("MUL");
		}
break;
case 31:
//#line 151 "figura.y"
{
			maq.code("DIV");
		}
break;
case 32:
//#line 155 "figura.y"
{
			maq.code("constpush4");
			maq.code(((Algo)val_peek(0).obj).simb);
			maq.code("DIV");
		}
break;
case 33:
//#line 161 "figura.y"
{
			maq.code("constpush4");
			maq.code(val_peek(0).sval);
			maq.code("DIV");
		}
break;
case 35:
//#line 170 "figura.y"
{
			 	maq.change(val_peek(2).ival+1,new Integer(val_peek(0).ival+1)); /*stmt*/
			 	yyval = val_peek(0);

		  }
break;
case 36:
//#line 176 "figura.y"
{
		  		maq.change(val_peek(2).ival+1,new Integer(val_peek(0).ival+1));
		  		maq.change(val_peek(2).ival+2,new Integer(val_peek(0).ival+1));
			 	yyval = val_peek(0);
		 }
break;
case 37:
//#line 182 "figura.y"
{
		  		maq.change(val_peek(4).ival+1,new Integer(val_peek(1).ival));
		  		maq.change(val_peek(4).ival+2,new Integer(val_peek(0).ival+1));
		  		yyval = val_peek(0);
		  }
break;
case 38:
//#line 188 "figura.y"
{
				maq.change(val_peek(8).ival+1,new Integer(val_peek(6).ival+1));
				maq.change(val_peek(4).ival+1,new Integer(val_peek(2).ival+1));
				maq.change(val_peek(4).ival+2,new Integer(val_peek(0).ival+2));
				yyval = new ParserVal(maq.code(new Integer(val_peek(4).ival+3)));
			}
break;
case 39:
//#line 197 "figura.y"
{
		yyval = new ParserVal(maq.code("WHILE"));
	}
break;
case 40:
//#line 202 "figura.y"
{
		yyval = new ParserVal(maq.code("IF"));
	}
break;
case 41:
//#line 207 "figura.y"
{
		yyval = new ParserVal(maq.code("ELSE"));
	}
break;
case 42:
//#line 212 "figura.y"
{
		yyval = new ParserVal(maq.code("FOR"));
		maq.code(new String(""));
	}
break;
case 43:
//#line 219 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
			maq.code(new String("r"));
			maq.code(new String("r"));
		}
break;
case 44:
//#line 227 "figura.y"
{
				maq.code("AND");	
			}
break;
case 45:
//#line 231 "figura.y"
{
				maq.code("OR");
			}
break;
case 46:
//#line 235 "figura.y"
{
				maq.code("NOT");
			}
break;
case 47:
//#line 240 "figura.y"
{
				maq.code("mayor");
			}
break;
case 48:
//#line 244 "figura.y"
{
				maq.code("mayorIgual");
			}
break;
case 49:
//#line 248 "figura.y"
{
				maq.code("menor");
			}
break;
case 50:
//#line 252 "figura.y"
{
				maq.code("menorIgual");
			}
break;
case 51:
//#line 256 "figura.y"
{
				maq.code("igual");
			}
break;
case 52:
//#line 260 "figura.y"
{
				maq.code("diferente");
			}
break;
case 53:
//#line 266 "figura.y"
{
				yyval = new ParserVal(maq.code("constpush3"));
				maq.code(((Algo)val_peek(0).obj).simb);
			}
break;
case 54:
//#line 271 "figura.y"
{
				yyval = new ParserVal(maq.code("constpush3"));
		  		maq.code(val_peek(0).sval);
		  	}
break;
case 55:
//#line 279 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 56:
//#line 285 "figura.y"
{
				maq.code("constpush");
		  		maq.code(new Integer(1));
		  		maq.code(val_peek(0).sval);
				maq.code("decrementa");
			}
break;
case 57:
//#line 292 "figura.y"
{
		  		maq.code("constpush");
		  		maq.code(new Integer(1));
		  		maq.code(val_peek(0).sval);
				maq.code("incrementa");
		  }
break;
case 58:
//#line 301 "figura.y"
{
					yyval = new ParserVal(maq.code("STOP"));
				}
break;
case 59:
//#line 307 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 60:
//#line 311 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 66:
//#line 324 "figura.y"
{ 
	  		maq.code("constpush");
	  		maq.code(new Integer(6));
	  		maq.code(val_peek(8).sval);
	  		maq.code(((Algo)val_peek(5).obj).simb);
	  		maq.code(((Algo)val_peek(4).obj).simb);
	  		maq.code(((Algo)val_peek(3).obj).simb);
	  		maq.code(((Algo)val_peek(2).obj).simb);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("linea");
	  }
break;
case 67:
//#line 336 "figura.y"
{ 
	  		maq.code("constpush");
	  		maq.code(new Integer(6));
	  		maq.code(val_peek(8).sval);
	  		maq.code(((Algo)val_peek(5).obj).simb);
	  		maq.code(((Algo)val_peek(4).obj).simb);
	  		maq.code(((Algo)val_peek(3).obj).simb);
	  		maq.code(((Algo)val_peek(2).obj).simb);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("rectangulo");
	  }
break;
case 68:
//#line 348 "figura.y"
{ 
	  		maq.code("constpush");
	  		maq.code(new Integer(5));
	  		maq.code(val_peek(7).sval);
	  		maq.code(((Algo)val_peek(4).obj).simb);
	  		maq.code(((Algo)val_peek(3).obj).simb);
	  		maq.code(((Algo)val_peek(2).obj).simb);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("circulo");
	  }
break;
case 69:
//#line 359 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code(val_peek(1).sval);
	  		maq.code("dibujar");
	  		
	  }
break;
case 70:
//#line 367 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(3));
	  		maq.code(val_peek(3).sval);
	  		maq.code(((Algo)val_peek(2).obj).simb);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("mover");
	  }
break;
case 71:
//#line 376 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(2));
	  		maq.code(val_peek(2).sval);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("escala");
	  }
break;
case 72:
//#line 386 "figura.y"
{
			maq.code("retorno");
		}
break;
case 73:
//#line 391 "figura.y"
{
				  	maq.code("constpush");
				  	maq.code(new Integer(2));
				  	maq.code(val_peek(2).sval);
				  	maq.code(((Algo)val_peek(0).obj).simb);
				  	maq.code("varEntera");
			  }
break;
//#line 1130 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
