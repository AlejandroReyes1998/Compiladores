import java.awt.*;
import java.util.*;

class  Lienzo {

Graphics g;
Color[] ColorArray = {Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};

Lienzo(Graphics g){ 
	this.g=g; }

void linea(double d1,double d2,double d3,double d4,double d5){
	int a = (int) d1;
	int b = (int) d2;
	int c = (int) d3;
	int d = (int) d4;
	int e = (int) d5;

	if(g!=null){
		System.out.println("PASO NO NULO");
		g.setColor(ColorArray[e]);
		(new Linea(a,b,c,d)).dibuja(g);}
}

void circulo(double d1,double d2,double d3,double d4){


	int a = (int) d1;
	int b = (int) d2;
	int c = (int) d3;
	int d = (int) d4;

		if(g!=null){
			g.setColor(ColorArray[d]);
			(new Circulo(a,b,c)).dibuja(g);}}
			
void dibujar(Variable v){


	if(v.getTipo()==1)
		linea(v.getX1(),v.getX2(),v.getY1(),v.getY2(),v.getColor());
	else if (v.getTipo()==2)
		rectangulo(v.getX1(),v.getX2(),v.getY1(),v.getY2(),v.getColor());
	else
		circulo(v.getX1(),v.getX2(),v.getY1(),v.getColor());

}

void algo(){
	System.out.print("GUUUUU");
}

void rectangulo(double d1,double d2,double d3,double d4, double d5){


	int a = (int) d1;
	int b = (int) d2;
	int c = (int) d3;
	int d = (int) d4;
	int e = (int) d5;

		if(g!=null){
			g.setColor(ColorArray[e]);
			(new Rectangulo(a,b,c,d)).dibuja(g);}}
}
	
