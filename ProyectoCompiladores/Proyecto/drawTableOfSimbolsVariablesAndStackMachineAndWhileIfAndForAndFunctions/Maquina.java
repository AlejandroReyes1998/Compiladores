import java.awt.*;
import java.util.*;
import java.lang.reflect.*;
import javax.swing.*;

class  Maquina {
Stack<String> funcActivas;
boolean forActivo = false;
boolean retornoFunc = false;
Stack pila;
Vector prog;
Stack nombres;
Stack<Object> condicion;
static int pc=0;
int progbase=0;
boolean returning=false;
Stack<Boolean> varCond;
Stack<String> sent;
Stack<HashMap<String,Variable>> variables;
Stack<Variable> regreso; 
int pcCond = 0;
boolean ciclo = false;
int r=0;
int numArchi=0;
Method metodo;
Method metodos[];
Class c;
Graphics g;
JTextArea errs;
double angulo;
boolean errores = false;
int x=250, y=250;
final int DIST = 10;

Class parames[];
Object parms[];
HashMap<String, Funcion> frame;
public int inicio = 0;

	Maquina(Graphics g , JTextArea errs){  
		this.errs = errs;
		this.g=g; 
		variables = new Stack<>();
		frame = new HashMap<>();
	}
	public Vector getProg(){ return prog; }
	public void initXY()
	{
		x = y=150;
	}
	void initcode(){
		errores = false;
        pila=new Stack();
		prog=new Vector();
		funcActivas = new Stack<>();
		regreso = new Stack<>();
		nombres = new Stack<>();
		condicion = new Stack<>();
		varCond = new Stack<>();
		sent = new Stack<>();
		variables.push(new HashMap<>());
	}
	Object pop(){ return ((Integer)pila.pop()); }

	void change(int p, Object f)
	{
		prog.set(p,f);
	}

	int code(Object f)
	{
   		prog.addElement(f);
		return prog.size()-1;
	}
	void code2(int pos,String nombre,int nargs)
	{
		frame.put(nombre,new Funcion(pos,nargs));
	}
	boolean  buscaMain()
	{
		if(frame.containsKey("main"))
		{
			inicio = frame.get("main").getPos();
			return true;
		}
		else
		{
			errs.setText("No hay metodo main");
			errores = true;
			return false;
		}
	}
	void contenido()
	{
		System.out.println(prog.size());
		for(int i=0;i<prog.size();++i)
			System.out.println(prog.elementAt(i));
		System.out.println("------------------------------------------");
	}
    void execute(int p){
    	errs.setText("");
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
			for(pc=p; !(inst=(String)prog.elementAt(pc)).equals("STOP") && !returning && !errores;)
			{
				//for(pc=p;pc < prog.size();){
				try 
				{
					inst=(String)prog.elementAt(pc);
					System.out.println(inst);
					r++;
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
					e.printStackTrace();
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
	void constpush4()
	{
		if(prog.elementAt(pc) instanceof String)
			pila.push(variables.peek().get(prog.elementAt(pc).toString()));
		else
			pila.push(prog.elementAt(pc));
		pc++;
	}
	private void linea()
	{
		int color = ((Integer)pila.pop()).intValue();
		int y2 = ((Integer)pila.pop()).intValue();
 		int x2 = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		variables.peek().put(nombre,new Variable(new Linea(x1,y1,x2,y2,color)));
	}

	private void rectangulo()
	{
		int color = ((Integer)pila.pop()).intValue();
		int y2 = ((Integer)pila.pop()).intValue();
 		int x2 = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		variables.peek().put(nombre,new Variable(new Rectangulo(x1,y1,x2,y2,color)));
	}

	private void circulo()
	{
		int color = ((Integer)pila.pop()).intValue();
		int r = ((Integer)pila.pop()).intValue();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		variables.peek().put(nombre,new Variable(new Circulo(x1,y1,r,color)));
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
		variables.peek().get(nombre).fvalor.dibuja(g);
	}

	private void mover()
	{
		String nombre = (String)nombres.pop();
		int y1 = ((Integer)pila.pop()).intValue();
		int x1 = ((Integer)pila.pop()).intValue();
		variables.peek().get(nombre).fvalor.mover(x1,y1);
	}

	private void escala()
	{
		String nombre = (String)nombres.pop();
		int e = ((Integer)pila.pop()).intValue();
		variables.peek().get(nombre).fvalor.escalar(e);
	}

	private int[] getDatToComp()
	{
		Object obj2 = condicion.pop();
		Object obj1 = condicion.pop();
		int[] array = new int[2];
		if(obj1 instanceof Simbolo)
			array[0] = (int)(((Simbolo)obj1).val);
		else if(obj1 instanceof Integer)
			array[0]= ((Integer)obj1).intValue();
		else if(obj1 instanceof Variable)
			array[0] = ((Variable)obj1).ivalor;
		else
			array[0] = variables.peek().get((String)(obj1.toString())).ivalor.intValue();
		
		if(obj2 instanceof Simbolo)
			array[1] = (int)((Simbolo)obj2).val;
		else if(obj2 instanceof Integer)
			array[1]= ((Integer)obj2).intValue();
		else if(obj2 instanceof Variable)
			array[1] = ((Variable)obj2).ivalor;
		else
			array[1] = variables.peek().get((String)(obj2.toString())).ivalor.intValue();
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

	private int[] getValores()
	{
		Object a = pila.pop();
		Object b = pila.pop();
		int a1,b1;
		if(a instanceof Simbolo)
			a1=(int)((Simbolo)a).val;
		else if(a instanceof Integer)
			a1= ((Integer)a).intValue();
		else if(a instanceof Variable)
			a1 = ((Variable)a).ivalor;
		else
			a1 = variables.peek().get(a.toString()).ivalor.intValue();

		if(b instanceof Simbolo)
			b1=(int)((Simbolo)b).val;
		else if(a instanceof Integer)
			b1= ((Integer)b).intValue();
		else if(b instanceof Variable)
			b1 = ((Variable)b).ivalor;
		else
			b1 = variables.peek().get(b.toString()).ivalor.intValue();
		int res[] = {a1,b1};
		return res;
	}

	private void SUM()
	{
		int val[]=  getValores();
		System.out.println(val[1] + "  " + val[0]);
		pila.push(new Integer(val[1]+val[0]));	
	}
	private void RES()
	{
		int val[]=  getValores();
		pila.push(new Integer(val[1]-val[0]));	
	}
	private void MUL()
	{
		int val[]=  getValores();
		pila.push(new Integer(val[1]*val[0]));	
	}

	private void DIV()
	{
		int val[]=  getValores();
		pila.push(new Integer(val[1]/val[0]));	
	}

	private void verificaCondicion()
	{
		if(!varCond.pop().booleanValue())
		{
			//System.out.println(((Integer)prog.elementAt(pc)).intValue());
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

	private void FOR()
	{
		int aux = pc;
		if(!forActivo)
		{
			execute(pc+1);
			forActivo = true;
		}
		pc = aux;
		int salto = ((Integer)prog.elementAt(pc)).intValue();
		pc = salto;
		execute(pc);
		if(varCond.pop().booleanValue())
		{
			salto = ((Integer)prog.elementAt(pc+1)).intValue();
			pc = salto;
			execute(pc);
			salto = ((Integer)prog.elementAt(pc+1)).intValue();
			pc=salto;
			execute(pc);
			pc = aux;
			FOR();
		}
		else
		{
			salto = ((Integer)prog.elementAt(pc+2)).intValue();
			pc=salto;
			execute(pc);
			forActivo = false;
		}
		
	}

	private void varEntera()
	{
		int valor = ((Integer)pila.pop()).intValue();
		String nombre = (String)nombres.pop();
		variables.peek().put(nombre,new Variable(new Integer(valor)));
	}

	private void decrementa()
	{
		//System.out.println("Si o no");
		String nombre = (String)nombres.pop();
		int nuevoValor = variables.peek().get(nombre).ivalor.intValue() - 1;
		variables.peek().put(nombre,new Variable(new Integer(nuevoValor)));
	}

	private void incrementa()
	{
		//System.out.println("Si o no");
		String nombre = (String)nombres.pop();
		int nuevoValor = variables.peek().get(nombre).ivalor.intValue() + 1;
		variables.peek().put(nombre,new Variable(new Integer(nuevoValor)));
	}


	private void call()
	{
		variables.push(new HashMap<>());
		String funcion = prog.elementAt(pc++).toString();//obtenemos el nombre de la funcion
		if(frame.containsKey(funcion))
		{
			System.out.println("call " + funcion);
			frame.get(funcion).setRetpc(pc);
			pc = frame.get(funcion).getPos();
			execute(pc);//llama a la funcion
			returning = false;
			variables.pop();
			pc = frame.get(funcion).getRetpc();
			System.out.println("Finaliza call. pc = " + pc);
		}
		else
		{
			errs.setText("La funcion " + funcion + " no existe");	
			errores = true;
		}
	}

	private void VOID()
	{
		pc = frame.get(funcActivas.pop()).getRetpc();
		variables.pop();
	}
	private void FUNCION()
	{
		returning = false;
		String nombre = prog.elementAt(pc++).toString(); // obtenemos el nombre de la funcion
		System.out.println("Funcion " + nombre + "pc = " + (pc-1));
		funcActivas.push(nombre);
		Funcion func = frame.get(nombre);// obtenemos la informacion de la funcion
		for(int i=0;i<func.get_nargs();++i)
			nombres.push(prog.elementAt(pc++));
		//System.out.println(pila.size());
		for(int i=0;i<func.get_nargs();++i)
		{
			String nom = nombres.pop().toString();
			variables.peek().put(nom,new Variable(pila.pop())); // obtenemos los argumentos 
			System.out.println("argumento " + nom + " = " + variables.peek().get(nom).ivalor.intValue());
		}
		System.out.println("Finaliza funcion " + nombre + " pc = "+pc);
	}

	private void retorno()
	{
		System.out.println("jkdfkjdf");
		Object ret = pila.peek();
		if(ret instanceof String)
		{
			pila.pop();
			pila.push(variables.peek().get(ret.toString()));
		}
		System.out.println("Retorno activado pc = " + pc);
		returning = true;
	}

	//The peek() method is used to look at the object at the top of this stack without removing it from the stack.
	private void asignar()
	{
		System.out.println("Asignar pc =" +pc);
		String nom = prog.elementAt(pc++).toString();
		variables.peek().put(nom,new Variable(pila.pop()));
	}
}
