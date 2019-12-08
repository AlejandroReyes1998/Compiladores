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
}
