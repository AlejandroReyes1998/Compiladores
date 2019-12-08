%{
	import java.lang.Math;
	import java.io.*;
	import java.util.StringTokenizer;
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import java.util.*;
%}

%token NUMBER END
%start A
%%
	A : condicion END condicion{System.out.println("END");}
	condicion: NUMBER '<' variable {System.out.println("CONDICION");}
	variable: NUMBER {System.out.println("NUMERO");}
%%


int pos=0;
StringTokenizer st;
boolean newline;
static Parser par = new Parser(0);
static JFrame f;
static JTextField t=new JTextField(20);
static JButton bcalc;
static JLabel lmuestra=new JLabel("                                 ");
static Canvas canv;
static Graphics g;
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
 	
	
	try 
	{
		d = Integer.parseInt(s);
		yylval = new ParserVal(d);
		tok = NUMBER;
	} 
	catch (Exception e)
	{
		if(s.equals("END"))
			tok = END;
		else
			tok = s.charAt(0);
	}
	return tok;
}
void yyerror(String s)
{
   System.out.println("parser error: "+s); 
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
}
  
class ManejaBoton implements ActionListener 
{
   public void actionPerformed(ActionEvent e)
   {
      	JButton jb=(JButton)e.getSource();
     	if(jb == bcalc)
     	{ 
     		st = new StringTokenizer(t.getText());
			newline=false;
			par.yyparse();
		}
	}
}