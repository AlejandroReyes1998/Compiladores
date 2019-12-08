public class Tabla {
	Simbolo listaSimbolo;
	Tabla(){
		listaSimbolo=null;
	}

Simbolo install(String s, short t, double d){
		Simbolo simb=new Simbolo(s,t,d);
		simb.ponSig(listaSimbolo);
		listaSimbolo=simb;
		return simb;
	}

Simbolo install(String s, short t, double a, double b, double c, double d,double e){
		System.out.print("0000000000000000000000000000000000 Paso por largo 0000000000000000000000000 ");
		Simbolo simb=new Simbolo(s,t,a,b,c,d,e);
		simb.ponSig(listaSimbolo);
		listaSimbolo=simb;
		return simb;
	}

Simbolo lookup(String s){
	for(Simbolo sp=listaSimbolo; sp!=null; sp=sp.obtenSig())
		if((sp.obtenNombre()).equals(s))
			return sp;
	return null;
	}
	
public void verTodos(){
	System.out.println("ENTRO A VER TODOS");
	for(Simbolo sp=listaSimbolo; sp!=null; sp=sp.obtenSig()){
			System.out.println("->" + sp.obtenNombre() + sp.getA());
	  }
	}
}
