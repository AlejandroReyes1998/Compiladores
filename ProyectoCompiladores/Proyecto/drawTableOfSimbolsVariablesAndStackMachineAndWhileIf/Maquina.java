import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

class  Maquina {

Stack pila;
Vector prog;
Stack nombres;
Stack<Object> condicion;
static int pc=0;
int progbase=0;
boolean returning=false;
Stack<Boolean> varCond;
Stack<String> sent;
int pcCond = 0;
boolean ciclo = false;

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
HashMap<String,Integer> variables;

	Maquina(Graphics g){  
		this.g=g; 
		figuras = new HashMap<>();
		variables = new HashMap<>();
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
		condicion = new Stack<>();
		varCond = new Stack<>();
		sent = new Stack<>();
	}
	Object pop(){ return ((Integer)pila.pop()); }

	void change(int p, Object f)
	{
		prog.set(p,f);
	}

	int code(Object f)
	{
		//System.out.println("Gen ("+f+") size="+prog.size());

   		prog.addElement(f);
		return prog.size()-1;
	}
	void contenido()
	{
		System.out.println(prog.size());
		for(int i=0;i<prog.size();++i)
			System.out.println(prog.elementAt(i));
	}
    void execute(int p){
    	//System.out.println(prog.size());
		String inst;
		/*System.out.println("progsize="+prog.size());
		for(pc=0;pc < prog.size(); pc=pc+1){
			System.out.println("pc="+pc+" inst "+prog.elementAt(pc));
		}*/
		if(prog.size()==pc)
			System.out.println("djdkjl");
		else
		{
			for(pc=p; !(inst=(String)prog.elementAt(pc)).equals("STOP");)
			{
				//for(pc=p;pc < prog.size();){
				try 
				{
					inst=(String)prog.elementAt(pc);
					pc=pc+1;
					c=this.getClass();
					metodo=c.getDeclaredMethod(inst, parames);
					metodo.invoke(this, parms);
					//System.out.println(pc+"---"+prog.elementAt(pc));
				}
				catch(NoSuchMethodException e)
				{
					System.out.println("No metodo "+e);
				}

				catch(InvocationTargetException e)
				{
					System.out.println(e);
				}
				catch(IllegalAccessException e)
				{
					System.out.println(e);
				}
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

	void constpush3()
	{
		condicion.push(prog.elementAt(pc));
		pc+=1;
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

	private void escala()
	{
		String nombre = (String)nombres.pop();
		int e = ((Integer)pila.pop()).intValue();
		figuras.get(nombre).escalar(e);
	}

	private int[] getDatToComp()
	{
		Object obj2 = condicion.pop();
		Object obj1 = condicion.pop();
		int[] array = new int[2];
		if(obj1 instanceof Simbolo)
			array[0] = (int)(((Simbolo)obj1).val);
		else
			array[0] = variables.get((String)(obj1.toString()));
		if(obj2 instanceof Simbolo)
			array[1] = (int)((Simbolo)obj2).val;
		else
			array[1] = variables.get((String)(obj2.toString())).intValue();
		return array;
	}

	private void diferente()
	{
		int[] datos = getDatToComp();
		varCond.push(new Boolean(datos[0]!=datos[1]));
	}

	private void igual()
	{
		int[] datos = getDatToComp();
		varCond.push(new Boolean(datos[0]==datos[1]));
	}

	private void menorIgual()
	{
		int[] datos = getDatToComp();
		varCond.push(new Boolean(datos[0]<=datos[1]));
	}

	private void mayorIgual()
	{
		int[] datos = getDatToComp();
		varCond.push(new Boolean(datos[0]>=datos[1]));
	}

	private void menor()
	{
		int[] datos = getDatToComp();
		varCond.push(new Boolean(datos[0]<datos[1]));
	}

	private void mayor()
	{
		int[] datos = getDatToComp();
		//System.out.println(new Boolean(datos[0]>datos[1]));
		varCond.push(new Boolean(datos[0]>datos[1]));
	}

	private void AND()
	{
		varCond.push(new Boolean(varCond.pop().booleanValue() && varCond.pop().booleanValue()));
	}

	private void OR()
	{
		varCond.push(new Boolean (varCond.pop().booleanValue() || varCond.pop().booleanValue()));
	}

	private void NOT()
	{
		varCond.push(new Boolean(!varCond.pop().booleanValue()));
	}

	private void verificaCondicion()
	{
		if(!varCond.pop().booleanValue())
		{
			System.out.println(((Integer)prog.elementAt(pc)).intValue());
			pc = ((Integer)prog.elementAt(pc)).intValue();
		}
		else
		{
			pc+=1;
		}	
	}

	private void parteStmt()
	{
		if(prog.elementAt(pc) instanceof String)
			pc+=1;
		else
			pc = ((Integer)prog.elementAt(pc)).intValue();
	}
	private void WHILE()
	{
		System.out.println("Al while si entro");
		int aux = pc;
		execute(pc);
		if(varCond.pop().booleanValue())
		{
			execute(pc+3);
			pc = aux;
			WHILE();
		}
		else
		{
			int salto = ((Integer)prog.elementAt(pc+1)).intValue();
			pc = salto;
			execute(pc);
		}

	}
	private void IF()
	{
		execute(pc);
		if(varCond.pop().booleanValue())
		{
			int salto = ((Integer)prog.elementAt(pc+2)).intValue();
			execute(pc+3);
			pc=salto;
			execute(pc);
		}
		else
		{
			int salto = ((Integer)prog.elementAt(pc+1)).intValue();
			pc=salto;
			execute(pc);
		}
	}
	private void ELSE()
	{
		execute(pc);
		execute(pc+1);
	}
	private void varEntera()
	{
		int valor = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		variables.put(nombre,new Integer(valor));
	}

	private void decrementa()
	{
		//System.out.println("Si o no");
		String nombre = (String)nombres.pop();
		int nuevoValor = variables.get(nombre).intValue() - 1;
		variables.put(nombre,new Integer(nuevoValor));
	}

	private void incrementa()
	{
		//System.out.println("Si o no");
		String nombre = (String)nombres.pop();
		int nuevoValor = variables.get(nombre).intValue() + 1;
		variables.put(nombre,new Integer(nuevoValor));
	}
}
