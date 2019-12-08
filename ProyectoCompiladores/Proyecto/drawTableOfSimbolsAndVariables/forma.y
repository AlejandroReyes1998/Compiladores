%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
%}

%token NUMBER LINEA RECTANGULO CIRCULO VARIABLE DIBUJAR
%start list

%%
list :
     | list ';'
     | list inst ';'	 
     ;
inst:   NUMBER						{ System.out.println(" " + $1.dval + " "); }
	  | VARIABLE '=' LINEA NUMBER NUMBER NUMBER NUMBER NUMBER { v.setTipos(1,$4.dval,$5.dval,$6.dval,$7.dval,$8.dval);}
	  | VARIABLE '=' RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER { v.setTipos(2,$4.dval,$5.dval,$6.dval,$7.dval,$8.dval);}
	  | VARIABLE '=' CIRCULO NUMBER NUMBER NUMBER NUMBER { v.setTipos(3,$4.dval,$5.dval,$6.dval,$7.dval);}
	  | LINEA NUMBER NUMBER NUMBER NUMBER NUMBER	{ li.linea($2.dval,$3.dval,$4.dval,$5.dval,$6.dval); }
      | RECTANGULO NUMBER NUMBER NUMBER NUMBER NUMBER  	{ li.rectangulo($2.dval,$3.dval,$4.dval,$5.dval,$6.dval); } 
      | CIRCULO NUMBER NUMBER NUMBER NUMBER 		{ li.circulo($2.dval,$3.dval,$4.dval,$5.dval); }
	  | DIBUJAR VARIABLE {li.dibujar(v); }
      ;
%%

Lienzo li;
Variable v = new Variable();
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

int yylex(){
	String s;
	int tok;
	Double d;	
	String aux;
 	if (!st.hasMoreTokens())
 		if (!newline){
 			newline=true;
 			return '\n'; }
 	else
 		return 0;
 	s = st.nextToken();
 	System.out.println("tok:"+s);
 	System.out.println(" ");
	
	try {
		d = Double.valueOf(s);
		yylval = new ParserVal(d.doubleValue());
		tabla.install("", NUMBER, d.doubleValue());
		tok = NUMBER;} 
	catch (Exception e){
		simbo = tabla.lookup(s);
		aux = s;
		System.out.println("PASO ANTES DEL NULL " + aux + s);
		if(simbo == null && !s.equals("=") && !s.equals(";") ){
		
		    System.out.print("INSTALAR SIMBOLO " + aux);
			tabla.install(s, VARIABLE,0.0);
			v.setNombre(s);
			tok= VARIABLE;
			
		}
		else if(simbo != null){
			tok= simbo.tipo;
		}
		else{
			tok = s.charAt(0);}}
	tabla.verTodos();
	
	/*
 	try{
		char algo = s.charAt(0);
 		if(!s.equals("=") && !s.equals("dibujar") && !s.equals("linea") && !s.equals("rectangulo") && !s.equals("circulo") && !s.equals(";") && !s.matches("[0-9]+")){
			System.out.println("PASO POR TOKEN VARIABLE" + algo);
 			tok = VARIABLE;}
 		else if(s.equals("dibujar")){
 			tok = DIBUJAR;}
		else if(s.equals("linea")){
 			tok = LINEA;}
		else if(s.equals("rectangulo")){
 			tok = RECTANGULO;}
		else if(s.equals("circulo")){
 			tok = CIRCULO;}
 		else{
 			d = Double.valueOf(s);
 			yylval = new ParserVal(d.doubleValue()); 
			tabla.install("", NUMBER, d.doubleValue())
 			tok = NUMBER;}}
 	catch (Exception e){
 		tok = s.charAt(0);}  */ 
	
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
		 
		 tabla.install("linea", LINEA, 0.0);
		 tabla.install("dibujar", DIBUJAR, 0.0);
		 tabla.install("circulo", CIRCULO, 0.0);
		 tabla.install("rectangulo", RECTANGULO, 0.0);
		 
         li=new Lienzo(g);
		 st = new StringTokenizer(t.getText());
		 newline=false;
		 yyparse();}}}
