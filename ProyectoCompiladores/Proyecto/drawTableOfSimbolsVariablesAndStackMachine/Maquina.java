import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

class  Maquina {

Stack pila;
Vector prog;
Stack nombres;
static int pc=0;
int progbase=0;
boolean returning=false;

int numArchi=0;
Method metodo;
Method metodos[];
Class c;
Graphics g;
double angulo;
int x=250, y=250;
final int DIST = 10;

Class parames[];
Object parms[];
HashMap<String,Dibujable> figuras;

	Maquina(Graphics g){  this.g=g; 
		figuras = new HashMap<>();
	}
	public Vector getProg(){ return prog; }
	public void initXY()
	{
		x = y=150;
	}
	void initcode(){
        	pila=new Stack();
		prog=new Vector();
		nombres = new Stack<>();
	}
	Object pop(){ return ((Integer)pila.pop()); }

	int code(Object f)
	{
		System.out.println("Gen ("+f+") size="+prog.size());
   		prog.addElement(f);
		return prog.size()-1;
	}
        void execute(int p){
		String inst;
                System.out.println("progsize="+prog.size());
                for(pc=0;pc < prog.size(); pc=pc+1){
					System.out.println("pc="+pc+" inst "+prog.elementAt(pc));
				}
		if(prog.size()==0)
			System.out.println("djdkjl");
		else
		for(pc=p; !(inst=(String)prog.elementAt(pc)).equals("STOP") && !returning;){
		//for(pc=p;pc < prog.size();){
			try {
				//System.out.println ("Tamaño vector: " + prog.size() + "\n Pc = " + pc);
			//System.out.println("111 pc= "+pc);
			inst=(String)prog.elementAt(pc);
			pc=pc+1;
			//System.out.println("222 pc= "+pc+" instr "+inst);
            c=this.getClass();
			//System.out.println("clase "+c.getName());
          	metodo=c.getDeclaredMethod(inst, parames);
			metodo.invoke(this, parms);
			System.out.println ("Tamaño vector: " + prog.size() + "\n Pc = " + pc);
			}
			catch(NoSuchMethodException e){
				System.out.println("No metodo "+e);
                        }

			catch(InvocationTargetException e){
				System.out.println(e);
                        }
			catch(IllegalAccessException e){
				System.out.println(e);
                        }
		}
	}
	void constpush()
	{
		Simbolo s;
		int n = ((Integer)prog.elementAt(pc)).intValue();
		pc +=1;
		nombres.push(prog.elementAt(pc).toString());
		pc+=1;
		for(int i = 1; i<n;++i)
		{
			s=(Simbolo)prog.elementAt(pc);
			pc=pc+1;
			pila.push(new Integer((int)s.val));
		}
	}

	void constpush2()
	{
		Simbolo s;
		int n = ((Integer)prog.elementAt(pc)).intValue();
		pc +=1;
		for(int i = 0 ;i<n;++i)
		{
			s=(Simbolo)prog.elementAt(pc);
			pc=pc+1;
			pila.push(new Integer((int)s.val));
		}
	}

	private void linea()
	{
		int color = ((Integer)pila.pop()).intValue();
		int y2 = ((Integer)pila.pop()).intValue();
 		int x2 = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		figuras.put(nombre,new Linea(x1,y1,x2,y2,color));
	}

	private void rectangulo()
	{
		int color = ((Integer)pila.pop()).intValue();
		int y2 = ((Integer)pila.pop()).intValue();
 		int x2 = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		figuras.put(nombre,new Rectangulo(x1,y1,x2,y2,color));
	}

	private void circulo()
	{
		int color = ((Integer)pila.pop()).intValue();
		int r = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		figuras.put(nombre,new Circulo(x1,y1,r,color));
	}

	private void drawLinea()
	{
		int color = ((Integer)pila.pop()).intValue();
		int y2 = ((Integer)pila.pop()).intValue();
 		int x2 = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		(new Linea(x1,y1,x2,y2,color)).dibuja(g);
	}

	private void drawRectangulo()
	{
		int color = ((Integer)pila.pop()).intValue();
		int y2 = ((Integer)pila.pop()).intValue();
 		int x2 = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		(new Rectangulo(x1,y1,x2,y2,color)).dibuja(g);
	}

	private void drawCirculo()
	{
		int color = ((Integer)pila.pop()).intValue();
		int r = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		(new Circulo(x1,y1,r,color)).dibuja(g);
	}

	private void dibujar()
	{
		System.out.println("Si o no");
		String nombre = (String)nombres.pop();
		figuras.get(nombre).dibuja(g);
	}

	private void mover()
	{
		String nombre = (String)nombres.pop();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		figuras.get(nombre).mover(x1,y1);
	}
}
