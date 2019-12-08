import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

class  Maquina {

Stack pila;
Vector prog;
Method metodo;
Method metodos[];
Class parames[];
Class c;
Graphics g;
Color[] ColorArray = {Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
	

static int pc=0;
int progbase=0;
int numArchi=0;
int x=0, y=0;
double angulo;
boolean returning=false;

Maquina(Graphics g){ 
	this.g=g; }
	
public Vector getProg(){ 
	return prog; }
	
void initcode(){
	pila=new Stack();
	prog=new Vector(); }
	
Object pop(){ 
	return pila.pop(); }
	
int code(Object f){
	System.out.println("Gen ("+f+") size="+prog.size());
	prog.addElement(f);
	return prog.size()-1;
}

int code(Object a, Object b,Object c,Object d, Object e){
	System.out.println("Gen ("+a+") size="+prog.size());
	System.out.println("Gen ("+b+") size="+prog.size());
	System.out.println("Gen ("+c+") size="+prog.size());
	System.out.println("Gen ("+d+") size="+prog.size());
	System.out.println("Gen ("+e+") size="+prog.size());
	System.out.println("WOLOLOLOL "+d.toString());
	prog.addElement(a);
	prog.addElement(b);
	prog.addElement(c);
	prog.addElement(d);
	prog.addElement(e);
	return prog.size()-5;
}

int code(Object a, Object b,Object c,Object d){
	System.out.println("Gen ("+a+") size="+prog.size());
	System.out.println("Gen ("+b+") size="+prog.size());
	System.out.println("Gen ("+c+") size="+prog.size());
	System.out.println("Gen ("+d+") size="+prog.size());
	System.out.println("WOLOLOLOL "+d.toString());
	prog.addElement(a);
	prog.addElement(b);
	prog.addElement(c);
	prog.addElement(d);
	return prog.size()-4;
}

void execute(int p){
	String inst;
	System.out.println("progsize="+prog.size());
	for(pc=0;pc < prog.size(); pc=pc+1){
		System.out.println("pc="+pc+" inst "+prog.elementAt(pc)); }
	for(pc=p; !(inst=(String)prog.elementAt(pc)).equals("STOP") && !returning;){
		//for(pc=p;pc < prog.size();){
		try {
			//System.out.println("111 pc= "+pc);
			inst=(String)prog.elementAt(pc);
			pc=pc+1;
			System.out.println("222 pc= "+pc+" instr "+inst);
			c=this.getClass();
			//System.out.println("clase "+c.getName());
			metodo=c.getDeclaredMethod(inst, null);
			metodo.invoke(this, null); }
		catch(NoSuchMethodException e){
			System.out.println("No metodo "+e);}
		catch(InvocationTargetException e){
			System.out.println("Esto es algo "+e); }
		catch(IllegalAccessException e){
			System.out.println("Estp es algo 2"+e); }}}

void constpush(){
	Simbolo s;
	Double d;
	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	pila.push(new Double(s.val)); }

void constpush2(){
	Simbolo s;
	Simbolo s1;
	Simbolo s2;
	Simbolo s3;
	Simbolo s4;
	Double d;
	Double d1;
	Double d2;
	Double d3;
	Double d4;
	Double d5;

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d1 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d1);
	pila.push(d1); 

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d2 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d2);
	pila.push(d2); 

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d3 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d3);
	pila.push(d3);

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d4 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d4);
	pila.push(d4);

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d5 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d5);
	pila.push(d5);

System.out.println("CONST PUSH 1 VALOR PERRO"+ d1);
System.out.println("CONST PUSH 2 VALOR PERRO"+ d2);
System.out.println("CONST PUSH 3 VALOR PERRO"+ d3);
System.out.println("CONST PUSH 4 VALOR PERRO"+ d4);
System.out.println("CONST PUSH 4 VALOR PERRO"+ d5); }

void constpush3(){
	Simbolo s;
	Double d;
	Double d1;
	Double d2;
	Double d3;
	Double d4;
	Double d5;

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d1 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d1);
	pila.push(d1); 

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d2 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d2);
	pila.push(d2); 

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d3 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d3);
	pila.push(d3);

	s=(Simbolo)prog.elementAt(pc);
	pc=pc+1;
	d4 = new Double(s.val);
	System.out.println("QUE CHINGADOS "+ d4);
	pila.push(d4);


System.out.println("CONST PUSH 1 VALOR PERRO"+ d1);
System.out.println("CONST PUSH 2 VALOR PERRO"+ d2);
System.out.println("CONST PUSH 3 VALOR PERRO"+ d3);
System.out.println("CONST PUSH 4 VALOR PERRO"+ d4); }
		
void linea(){
	double d1;
	double d2;
	double d3;
	double d4;
	double d5;
	d1=((Double)pila.pop()).doubleValue();
	d2=((Double)pila.pop()).doubleValue();
	d3=((Double)pila.pop()).doubleValue();
	d4=((Double)pila.pop()).doubleValue();
	d5=((Double)pila.pop()).doubleValue();

	int a = (int) d1;
	int b = (int) d2;
	int c = (int) d3;
	int d = (int) d4;
	int e = (int) d5;

	System.out.println("PASO d1 "+d1+" d2 "+d2+" d3 "+ d3+" d4 "+ d4+" d5 "+ d5);
	if(g!=null){
		System.out.println("PASO NO NULO");
		g.setColor(ColorArray[a]);
		(new Linea(b,c,d,e)).dibuja(g);}

	System.out.println("x="+x+" y="+y+" d1="+d1);}
	
void circulo(){
		double d1;
		double d2;
		double d3;
		double d4;
		d1=((Double)pila.pop()).doubleValue();
		d2=((Double)pila.pop()).doubleValue();
		d3=((Double)pila.pop()).doubleValue();
		d4=((Double)pila.pop()).doubleValue();

	int a = (int) d1;
	int b = (int) d2;
	int c = (int) d3;
	int d = (int) d4;

		if(g!=null){
			g.setColor(ColorArray[a]);
			(new Circulo(b,c,d)).dibuja(g);}}

void rectangulo(){

		double d1;
	double d2;
	double d3;
	double d4;
	double d5;
	d1=((Double)pila.pop()).doubleValue();
	d2=((Double)pila.pop()).doubleValue();
	d3=((Double)pila.pop()).doubleValue();
	d4=((Double)pila.pop()).doubleValue();
	d5=((Double)pila.pop()).doubleValue();

	int a = (int) d1;
	int b = (int) d2;
	int c = (int) d3;
	int d = (int) d4;
	int e = (int) d5;

		if(g!=null){
			g.setColor(ColorArray[a]);
			(new Rectangulo(b,c,d,e)).dibuja(g);}}
			
void print(){
	Double d;
	d=(Double)pila.pop();
	System.out.println(""+d.doubleValue());}
	
void prexpr(){
	Double d;
	d=(Double)pila.pop();
	System.out.print("["+d.doubleValue()+"]");}
	
}
