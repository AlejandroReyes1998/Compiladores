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
public final static short VARIABLE=261;
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
public final static short DECREMENTO=283;
public final static short INCREMENTO=284;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    1,    1,    7,    3,    6,
    8,    4,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   12,   12,    5,    5,   13,   13,   10,    9,   14,
   14,   15,    2,    2,    2,    2,    2,    2,    2,    2,
};
final static short yylen[] = {                            2,
    0,    2,    1,    5,    5,    7,    9,    1,    1,    1,
    1,    1,    7,    7,    4,    3,    3,    3,    3,    3,
    3,    1,    1,    1,    3,    0,    2,    1,    1,    2,
    2,    3,    2,    2,    9,    9,    8,    3,    5,    4,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    9,   10,   11,    0,    2,    3,
    0,    0,    0,    0,    0,   30,   31,    0,    0,    0,
    0,    0,    0,    0,   33,   34,   32,    0,    0,    0,
   38,    0,    0,   22,   23,    0,    0,    0,   12,    0,
    0,    0,    0,   29,    0,    0,    0,    0,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,    0,    0,   26,   24,    4,
   16,   17,   18,   19,   20,   21,    0,    0,    0,    0,
    0,   15,    0,    0,    0,    8,    0,    0,    0,    0,
    0,    0,    0,   25,   27,    6,    0,    0,   28,    0,
    0,   37,    0,    0,    0,   35,   36,   13,   14,    7,
};
final static short yydgoto[] = {                          1,
    9,   69,   11,   38,   70,   12,   87,   13,   43,   98,
   39,   40,   85,   14,   15,
};
final static short yysindex[] = {                         0,
 -216,  -61, -247, -239,    0,    0,    0, -234,    0,    0,
 -261, -241, -238, -249, -246,    0,    0, -204, -242, -220,
 -215, -233, -233, -217,    0,    0,    0, -186, -185, -184,
    0, -183, -207,    0,    0, -231, -233, -203,    0, -214,
 -202,   17, -201,    0, -178, -177, -175, -199,    0, -233,
 -195, -245, -236, -236, -236, -236, -236, -236, -245, -172,
 -233, -171, -169, -168,    0, -189, -250,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -174, -191, -164, -163,
 -162,    0, -176, -173, -255,    0, -245, -161, -160, -159,
 -181, -233, -233,    0,    0,    0, -254, -170,    0, -180,
 -179,    0, -167, -166, -245,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   19,    2,    0,  -18,  -55,    0,    0,    0,    0,    0,
  -35,   12,    0,   18,   81,
};
final static int YYTABLESIZE=282;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
    5,   51,   10,   77,   41,    2,    3,    4,    5,    6,
   22,    7,    8,   19,   66,    2,    3,    4,   83,   84,
   34,   20,    8,   34,   35,   94,   21,   35,   16,   17,
   23,   96,   25,   24,   68,   26,   32,   36,   37,   31,
   50,   33,   78,   42,    2,    3,    4,    5,    6,  110,
    7,    8,   27,   28,   29,   30,  103,  104,   53,   54,
   55,   56,   57,   58,   71,   72,   73,   74,   75,   76,
   45,   46,   47,   48,   49,   52,   59,   60,   62,   63,
   61,   64,   65,   67,   27,   79,   10,   80,   81,   82,
   88,   86,   89,   90,   91,   92,  100,  101,   93,   97,
  102,  106,  107,   95,   44,   99,    0,    0,  105,    0,
    0,  108,  109,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   16,   17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    5,    5,    5,    5,    5,    0,    5,    5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    5,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         61,
    0,   37,    1,   59,   23,  261,  262,  263,  264,  265,
  272,  267,  268,  261,   50,  261,  262,  263,  269,  270,
  257,  261,  268,  257,  261,  281,  261,  261,  283,  284,
  272,   87,  282,  272,  280,  282,  257,  271,  272,  282,
  272,  257,   61,  261,  261,  262,  263,  264,  265,  105,
  267,  268,  257,  258,  259,  260,   92,   93,  273,  274,
  275,  276,  277,  278,   53,   54,   55,   56,   57,   58,
  257,  257,  257,  257,  282,  279,  279,   61,  257,  257,
  282,  257,  282,  279,  257,  257,   85,  257,  257,  279,
  282,  266,  257,  257,  257,  272,  257,  257,  272,  261,
  282,  282,  282,   85,   24,   88,   -1,   -1,  279,   -1,
   -1,  279,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  261,  262,  263,  264,  265,   -1,  267,  268,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  281,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=284;
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
null,null,null,null,null,"NUMBER","LINEA","RECTANGULO","CIRCULO","VARIABLE",
"DIBUJAR","MOVER","WHILE","IF","ELSE","FOR","ESCALAR","AND","OR","NOT",
"PARENT_I","GT","GE","LT","LE","EQ","NE","PARENT_D","LLAVE_I","LLAVE_D",
"SIMB_SEP","DECREMENTO","INCREMENTO",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list sentencia",
"sentencia : inst",
"sentencia : while PARENT_I cond PARENT_D stmt",
"sentencia : if PARENT_I cond PARENT_D stmt",
"sentencia : if PARENT_I cond PARENT_D stmt else stmt",
"sentencia : for PARENT_I initfor SIMB_SEP cond SIMB_SEP cambioValFor PARENT_D stmt",
"else : ELSE",
"while : WHILE",
"if : IF",
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
"exp : VARIABLE",
"stmt : inst",
"stmt : LLAVE_I instrucciones LLAVE_D",
"instrucciones :",
"instrucciones : instrucciones sentencia",
"cambioValFor : cambioValor",
"initfor : crearVariableInt",
"cambioValor : VARIABLE DECREMENTO",
"cambioValor : VARIABLE INCREMENTO",
"crearVariableInt : VARIABLE '=' NUMBER",
"inst : cambioValor SIMB_SEP",
"inst : crearVariableInt SIMB_SEP",
"inst : VARIABLE '=' LINEA NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : VARIABLE '=' RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : VARIABLE '=' CIRCULO NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : DIBUJAR VARIABLE SIMB_SEP",
"inst : MOVER VARIABLE NUMBER NUMBER SIMB_SEP",
"inst : ESCALAR VARIABLE NUMBER SIMB_SEP",
};

//#line 245 "figura.y"

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
static JButton bcalc;
static JLabel lmuestra=new JLabel("                                 ");
static JPanel editor = new JPanel();
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
			tabla.install(s, VARIABLE,0.0);
			yylval = new ParserVal(s);
			tok= VARIABLE;
		}
		else if(simbo != null)
		{
			tok= simbo.tipo;
			if (tok == VARIABLE)
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
	editor.add(t,BorderLayout.CENTER);
	editor.add(bcalc,BorderLayout.SOUTH);
	f.getContentPane().add(editor);
	f.getContentPane().add(canv);
	f.setSize( 900, 600);
	f.setVisible(true);
	g=canv.getGraphics(); 
	maq = new Maquina(g);
}
  
class ManejaBoton implements ActionListener 
{
   public void actionPerformed(ActionEvent e)
   {
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
			maq.execute(0);
		}
	}
}
//#line 477 "Parser.java"
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
case 4:
//#line 24 "figura.y"
{
			 	maq.change(val_peek(2).ival+1,new Integer(val_peek(0).ival+1)); /*stmt*/
			 	yyval = val_peek(0);

		  }
break;
case 5:
//#line 30 "figura.y"
{
		  		maq.change(val_peek(2).ival+1,new Integer(val_peek(0).ival+1));
		  		maq.change(val_peek(2).ival+2,new Integer(val_peek(0).ival+1));
			 	yyval = val_peek(0);
		 }
break;
case 6:
//#line 36 "figura.y"
{
		  		maq.change(val_peek(4).ival+1,new Integer(val_peek(1).ival));
		  		maq.change(val_peek(4).ival+2,new Integer(val_peek(0).ival+1));
		  		yyval = val_peek(0);
		  }
break;
case 7:
//#line 42 "figura.y"
{
			maq.change(val_peek(8).ival+1,new Integer(val_peek(6).ival+1));
			maq.change(val_peek(4).ival+1,new Integer(val_peek(2).ival+1));
			maq.change(val_peek(4).ival+2,new Integer(val_peek(0).ival+2));
			yyval = new ParserVal(maq.code(new Integer(val_peek(4).ival+3)));
		}
break;
case 8:
//#line 50 "figura.y"
{
		yyval = new ParserVal(maq.code("ELSE"));
	}
break;
case 9:
//#line 56 "figura.y"
{
		yyval = new ParserVal(maq.code("WHILE"));
	}
break;
case 10:
//#line 61 "figura.y"
{
		yyval = new ParserVal(maq.code("IF"));
	}
break;
case 11:
//#line 66 "figura.y"
{
		yyval = new ParserVal(maq.code("FOR"));
		maq.code(new String(""));
	}
break;
case 12:
//#line 72 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
			maq.code(new String("r"));
			maq.code(new String("r"));
		}
break;
case 13:
//#line 80 "figura.y"
{
				maq.code("AND");	
			}
break;
case 14:
//#line 84 "figura.y"
{
				maq.code("OR");
			}
break;
case 15:
//#line 88 "figura.y"
{
				maq.code("NOT");
			}
break;
case 16:
//#line 93 "figura.y"
{
				maq.code("mayor");
			}
break;
case 17:
//#line 97 "figura.y"
{
				maq.code("mayorIgual");
			}
break;
case 18:
//#line 101 "figura.y"
{
				maq.code("menor");
			}
break;
case 19:
//#line 105 "figura.y"
{
				maq.code("menorIgual");
			}
break;
case 20:
//#line 109 "figura.y"
{
				maq.code("igual");
			}
break;
case 21:
//#line 113 "figura.y"
{
				maq.code("diferente");
			}
break;
case 22:
//#line 119 "figura.y"
{
				yyval = new ParserVal(maq.code("constpush3"));
				maq.code(((Algo)val_peek(0).obj).simb);
			}
break;
case 23:
//#line 124 "figura.y"
{
				yyval = new ParserVal(maq.code("constpush3"));
		  		maq.code(val_peek(0).sval);
		  	}
break;
case 24:
//#line 132 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 25:
//#line 136 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 28:
//#line 146 "figura.y"
{
					yyval = new ParserVal(maq.code("STOP"));
				}
break;
case 29:
//#line 152 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 30:
//#line 157 "figura.y"
{
				maq.code("constpush");
		  		maq.code(new Integer(1));
		  		maq.code(val_peek(0).sval);
				maq.code("decrementa");
			}
break;
case 31:
//#line 164 "figura.y"
{
		  		maq.code("constpush");
		  		maq.code(new Integer(1));
		  		maq.code(val_peek(0).sval);
				maq.code("incrementa");
		  }
break;
case 32:
//#line 173 "figura.y"
{
				  	maq.code("constpush");
				  	maq.code(new Integer(2));
				  	maq.code(val_peek(2).sval);
				  	maq.code(((Algo)val_peek(0).obj).simb);
				  	maq.code("varEntera");
			  }
break;
case 35:
//#line 184 "figura.y"
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
case 36:
//#line 196 "figura.y"
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
case 37:
//#line 208 "figura.y"
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
case 38:
//#line 219 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code(val_peek(1).sval);
	  		maq.code("dibujar");
	  		
	  }
break;
case 39:
//#line 227 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(3));
	  		maq.code(val_peek(3).sval);
	  		maq.code(((Algo)val_peek(2).obj).simb);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("mover");
	  }
break;
case 40:
//#line 236 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(2));
	  		maq.code(val_peek(2).sval);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("escala");
	  }
break;
//#line 884 "Parser.java"
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
