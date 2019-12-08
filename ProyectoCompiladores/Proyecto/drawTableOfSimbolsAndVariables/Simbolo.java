class Simbolo {
	String nombre;
	public short tipo;
	double val;
	double val1;
	double val2;
	double val3;
	double val4;
	public String metodo;
	int defn;
	Simbolo sig;

	Simbolo(String s, short t, double d)
	{
		nombre=s;
		tipo=t;
		val=d;
	}
	

	Simbolo(String s, short t, double a, double b, double c,double d, double e)
	{
		System.out.print("PASO POR EL LARGO");
		val=a;
		val1=b;
		val2=c;
		val3=d;
		val4=e;
		nombre=s;
		tipo=t;
	}
	
	
	public double getA() {
        return val;
    }
	
	public double getB() {
        return val1;
    }
	
	public double getC() {
        return val2;
    }
	
	public double getD() {
        return val3;
    }
	
	public double getE() {
        return val4;
    }
	
	
	
	
	
	

        public Simbolo obtenSig()
        {
		return sig;
	}
        public void ponSig(Simbolo s)
	{
		sig=s;
	}
        public String obtenNombre()
	{
		return nombre;
	}
	
	public short obtenTipo()
	{
		return tipo;
	}
}