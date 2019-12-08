%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
%}

%token NUMBER LINEA RECTANGULO CIRCULO VARIABLE DIBUJAR MOVER DELETE
%start list

%%
list :
	 | list ';'
     | list inst ';'
     {
     	maq.code("STOP");
     	return 1;
     }	 
     ;
inst:   NUMBER	
	  | VARIABLE '=' LINEA NUMBER NUMBER NUMBER NUMBER NUMBER 
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
	  | VARIABLE '=' RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER 
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
	  | VARIABLE '=' CIRCULO NUMBER NUMBER NUMBER NUMBER 
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
	  | LINEA NUMBER NUMBER NUMBER NUMBER NUMBER	
	  { 
	  		maq.code("constpush2");
	  		maq.code(new Integer(5));
	  		maq.code(((Algo)$2.obj).simb);
	  		maq.code(((Algo)$3.obj).simb);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code(((Algo)$5.obj).simb);
	  		maq.code(((Algo)$6.obj).simb);
	  		maq.code("drawLinea");
	  }
      | RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER  	
      { 
      		maq.code("constpush2");
	  		maq.code(new Integer(5));
	  		maq.code(((Algo)$2.obj).simb);
	  		maq.code(((Algo)$3.obj).simb);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code(((Algo)$5.obj).simb);
	  		maq.code(((Algo)$6.obj).simb);
	  		maq.code("drawRectangulo");
      } 
      | CIRCULO NUMBER NUMBER NUMBER NUMBER 		
      { 
      		maq.code("constpush2");
	  		maq.code(new Integer(4));
	  		maq.code(((Algo)$2.obj).simb);
	  		maq.code(((Algo)$3.obj).simb);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code(((Algo)$5.obj).simb);
	  		maq.code("drawCirculo");
      }
	  | DIBUJAR VARIABLE 
	  {
	  		maq.code("constpush");
	  		maq.code(new Integer(1));
	  		maq.code($2.sval);
	  		maq.code("dibujar");
	  }
	  | MOVER VARIABLE NUMBER NUMBER
	  {
	  		maq.code("constpush");
	  		maq.code(new Integer(3));
	  		maq.code($2.sval);
	  		maq.code(((Algo)$3.obj).simb);
	  		maq.code(((Algo)$4.obj).simb);
	  		maq.code("mover");
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

Tabla tabla=new Tabla();
Simbolo simbo;
StringTokenizer st;
boolean newline;
static Parser par = new Parser(0);
static JFrame f;
static JTextField t=new JTextField(20);
static JButton bcalc;
static JLabel lmuestra=new JLabel("                                 ");
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
 	System.out.println("tok:"+s);
 	System.out.println(" ");
	
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
			tok = s.charAt(0);
	}
	return tok;
}
void yyerror(String s)
{
   //System.out.println("parser error: "+s); 
}

Parser(int foo)
{
	f=new JFrame("Calcula");
	bcalc=new JButton("Ejecuta");
	bcalc.addActionListener(new ManejaBoton());
	canv=new Canvas();
	canv.setSize(900,600);
	f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
}
public static void main(String args[])
{
	f.add("North", t);
	f.add("Center", canv);
	f.add("South", bcalc);
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
			tabla.install("linea", LINEA, 0.0);
			tabla.install("dibuja", DIBUJAR, 0.0);
			tabla.install("circulo", CIRCULO, 0.0);
			tabla.install("rectangulo", RECTANGULO, 0.0);
			tabla.install("mover",MOVER,0.0);
			st = new StringTokenizer(t.getText());
			newline=false;
			for(maq.initcode(); par.yyparse ()!=0; maq.initcode())
            	maq.execute(maq.progbase);
		}
	}
}