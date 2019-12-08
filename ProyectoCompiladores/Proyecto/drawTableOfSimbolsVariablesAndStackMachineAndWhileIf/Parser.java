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
public final static short ESCALAR=267;
public final static short AND=268;
public final static short OR=269;
public final static short NOT=270;
public final static short PARENT_I=271;
public final static short GT=272;
public final static short GE=273;
public final static short LT=274;
public final static short LE=275;
public final static short EQ=276;
public final static short NE=277;
public final static short PARENT_D=278;
public final static short LLAVE_I=279;
public final static short LLAVE_D=280;
public final static short SIMB_SEP=281;
public final static short DECREMENTO=282;
public final static short INCREMENTO=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    1,    7,    3,    6,    4,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    9,
    9,    5,    5,   10,   10,    2,    2,    2,    2,    2,
    2,    2,    2,    2,
};
final static short yylen[] = {                            2,
    0,    2,    1,    5,    5,    7,    1,    1,    1,    1,
    7,    7,    4,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    3,    0,    2,    3,    3,    4,    9,    9,
    8,    3,    5,    4,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    8,    9,    0,    2,    3,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   26,
   27,    0,    0,    0,    0,   32,    0,    0,   20,   21,
    0,    0,    0,   10,    0,    0,   28,    0,    0,    0,
    0,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0,   24,   22,
    4,   14,   15,   16,   17,   18,   19,    0,    0,    0,
    0,   13,    0,    0,    0,    7,    0,    0,    0,    0,
    0,    0,   23,   25,    6,    0,    0,   31,    0,    0,
   29,   30,   11,   12,
};
final static short yydgoto[] = {                          1,
    8,   60,   10,   33,   61,   11,   77,   34,   35,   75,
};
final static short yysindex[] = {                         0,
 -228,  -61, -251, -249,    0,    0, -242,    0,    0, -248,
 -244, -253, -231, -211, -222, -219, -197, -240, -240,    0,
    0, -220, -195, -194, -193,    0, -192, -215,    0,    0,
 -204, -240, -210,    0, -232, -209,    0, -187, -186, -185,
 -208,    0, -240, -203, -247, -239, -239, -239, -239, -239,
 -239, -247, -183, -181, -179,    0, -199, -243,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -184, -177, -176,
 -174,    0, -182, -180, -256,    0, -247, -173, -172, -191,
 -240, -240,    0,    0,    0, -189, -188,    0, -190, -178,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   11,    2,    0,   68,  -48,    0,    0,  -30,    7,    0,
};
final static int YYTABLESIZE=281;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         14,
    5,   44,    9,   68,    2,    3,    4,    5,    6,   15,
    7,   16,   57,    2,    3,    4,   29,   29,   17,    7,
   30,   30,   18,   83,   73,   74,   19,   20,   85,   31,
   32,   59,    2,    3,    4,    5,    6,   27,    7,   46,
   47,   48,   49,   50,   51,   22,   23,   24,   25,   21,
   89,   90,   62,   63,   64,   65,   66,   67,   26,   28,
   37,   38,   39,   40,   41,   42,   43,   45,   52,   53,
   54,   55,   56,   69,   58,   70,    9,   71,   72,   78,
   79,   76,   80,   86,   87,   84,   36,   93,   81,   88,
   82,   91,   92,    0,    0,    0,    0,    0,    0,   94,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   12,   13,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    5,    5,    5,    5,    5,    0,    5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    5,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         61,
    0,   32,    1,   52,  261,  262,  263,  264,  265,  261,
  267,  261,   43,  261,  262,  263,  257,  257,  261,  267,
  261,  261,  271,  280,  268,  269,  271,  281,   77,  270,
  271,  279,  261,  262,  263,  264,  265,  257,  267,  272,
  273,  274,  275,  276,  277,  257,  258,  259,  260,  281,
   81,   82,   46,   47,   48,   49,   50,   51,  281,  257,
  281,  257,  257,  257,  257,  281,  271,  278,  278,  257,
  257,  257,  281,  257,  278,  257,   75,  257,  278,  257,
  257,  266,  257,  257,  257,   75,   19,  278,  271,  281,
  271,  281,  281,   -1,   -1,   -1,   -1,   -1,   -1,  278,
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  282,  283,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  261,  262,  263,  264,  265,   -1,  267,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  280,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=283;
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
"DIBUJAR","MOVER","WHILE","IF","ELSE","ESCALAR","AND","OR","NOT","PARENT_I",
"GT","GE","LT","LE","EQ","NE","PARENT_D","LLAVE_I","LLAVE_D","SIMB_SEP",
"DECREMENTO","INCREMENTO",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list sentencia",
"sentencia : inst",
"sentencia : while PARENT_I cond PARENT_D stmt",
"sentencia : if PARENT_I cond PARENT_D stmt",
"sentencia : if PARENT_I cond PARENT_D stmt else stmt",
"else : ELSE",
"while : WHILE",
"if : IF",
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
"inst : VARIABLE DECREMENTO SIMB_SEP",
"inst : VARIABLE INCREMENTO SIMB_SEP",
"inst : VARIABLE '=' NUMBER SIMB_SEP",
"inst : VARIABLE '=' LINEA NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : VARIABLE '=' RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : VARIABLE '=' CIRCULO NUMBER NUMBER NUMBER NUMBER SIMB_SEP",
"inst : DIBUJAR VARIABLE SIMB_SEP",
"inst : MOVER VARIABLE NUMBER NUMBER SIMB_SEP",
"inst : ESCALAR VARIABLE NUMBER SIMB_SEP",
};

//#line 218 "figura.y"

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
//#line 464 "Parser.java"
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
//#line 43 "figura.y"
{
		yyval = new ParserVal(maq.code("ELSE"));
	}
break;
case 8:
//#line 49 "figura.y"
{
		yyval = new ParserVal(maq.code("WHILE"));
	}
break;
case 9:
//#line 54 "figura.y"
{
		yyval = new ParserVal(maq.code("IF"));
	}
break;
case 10:
//#line 59 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
			maq.code(new String("r"));
			maq.code(new String("r"));
		}
break;
case 11:
//#line 67 "figura.y"
{
				maq.code("AND");	
			}
break;
case 12:
//#line 71 "figura.y"
{
				maq.code("OR");
			}
break;
case 13:
//#line 75 "figura.y"
{
				maq.code("NOT");
			}
break;
case 14:
//#line 80 "figura.y"
{
				maq.code("mayor");
			}
break;
case 15:
//#line 84 "figura.y"
{
				maq.code("mayorIgual");
			}
break;
case 16:
//#line 88 "figura.y"
{
				maq.code("menor");
			}
break;
case 17:
//#line 92 "figura.y"
{
				maq.code("menorIgual");
			}
break;
case 18:
//#line 96 "figura.y"
{
				maq.code("igual");
			}
break;
case 19:
//#line 100 "figura.y"
{
				maq.code("diferente");
			}
break;
case 20:
//#line 106 "figura.y"
{
				yyval = new ParserVal(maq.code("constpush3"));
				maq.code(((Algo)val_peek(0).obj).simb);
			}
break;
case 21:
//#line 111 "figura.y"
{
				yyval = new ParserVal(maq.code("constpush3"));
		  		maq.code(val_peek(0).sval);
		  	}
break;
case 22:
//#line 119 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 23:
//#line 123 "figura.y"
{
			yyval = new ParserVal(maq.code("STOP"));
		}
break;
case 26:
//#line 135 "figura.y"
{
			maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code(val_peek(1).sval);
			maq.code("decrementa");
		}
break;
case 27:
//#line 142 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code(val_peek(1).sval);
			maq.code("incrementa");
	  }
break;
case 28:
//#line 149 "figura.y"
{
		  	maq.code("constpush");
		  	maq.code(new Integer(2));
		  	maq.code(val_peek(3).sval);
		  	maq.code(((Algo)val_peek(1).obj).simb);
		  	maq.code("varEntera");
	  }
break;
case 29:
//#line 157 "figura.y"
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
case 30:
//#line 169 "figura.y"
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
case 31:
//#line 181 "figura.y"
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
case 32:
//#line 192 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code(val_peek(1).sval);
	  		maq.code("dibujar");
	  		
	  }
break;
case 33:
//#line 200 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(3));
	  		maq.code(val_peek(3).sval);
	  		maq.code(((Algo)val_peek(2).obj).simb);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("mover");
	  }
break;
case 34:
//#line 209 "figura.y"
{
	  		maq.code("constpush");
	  		maq.code(new Integer(2));
	  		maq.code(val_peek(2).sval);
	  		maq.code(((Algo)val_peek(1).obj).simb);
	  		maq.code("escala");
	  }
break;
//#line 843 "Parser.java"
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
