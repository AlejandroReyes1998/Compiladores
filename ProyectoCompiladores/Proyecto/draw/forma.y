%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
%}

%token NUMBER LINEA RECTANGULO CIRCULO 
%start list

%%
list :
     | list ';'
     | list inst ';'	 
     ;
inst:   NUMBER						{ System.out.println(" " + $1.dval + " "); }
      | LINEA NUMBER NUMBER NUMBER NUMBER NUMBER	{ li.linea($2.dval,$3.dval,$4.dval,$5.dval,$6.dval); }
      | RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER  	{ li.rectangulo($2.dval,$3.dval,$4.dval,$5.dval,$6.dval); } 
      | CIRCULO NUMBER NUMBER NUMBER NUMBER 		{ li.circulo($2.dval,$3.dval,$4.dval,$5.dval); }
      ;
%%

Lienzo li;

StringTokenizer st;
boolean newline;
static Parser par = new Parser(0);
static JFrame f;
static JTextField t=new JTextField(20);
static JButton bcalc;
static JLabel lmuestra=new JLabel("                                 ");
static Canvas canv;
static Graphics g;

int yylex(){
	String s;
	int tok;
	Double d;
 	if (!st.hasMoreTokens())
 		if (!newline){
 			newline=true;
 			return '\n'; }
 	else
 		return 0;
 	s = st.nextToken();
 	System.out.println("tok:"+s);
 	try{
 		if(s.equals("linea")){
 			tok = LINEA; }
 		else if(s.equals("rectangulo")){
 			tok = RECTANGULO;}
		else if(s.equals("circulo")){
 			tok = CIRCULO;}
 		else{
 			d = Double.valueOf(s);
 			yylval = new ParserVal(d.doubleValue()); 
 			tok = NUMBER;}}
 	catch (Exception e){
 		tok = s.charAt(0);}
	return tok;}

void yyerror(String s){
   System.out.println("parser error: "+s); }

Parser(int foo){
   f=new JFrame("Calcula");
   bcalc=new JButton("Ejecuta");
   bcalc.addActionListener(new ManejaBoton());
   canv=new Canvas();
   canv.setSize(300,300);
   f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
}

public static void main(String args[]){
  f.add("North", t);
  f.add("Center", canv);
  f.add("South", bcalc);
  f.setSize( 300, 400);
  f.setVisible(true);
  g=canv.getGraphics(); }
  
class ManejaBoton implements ActionListener {
   public void actionPerformed(ActionEvent e){
      JButton jb=(JButton)e.getSource();
      if(jb == bcalc){
         li=new Lienzo(g);
		 st = new StringTokenizer(t.getText());
		 newline=false;
		 yyparse();}}}
