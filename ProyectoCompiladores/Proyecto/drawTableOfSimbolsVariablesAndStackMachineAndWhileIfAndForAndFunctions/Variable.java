class Variable
{
	public Integer ivalor=0;
	public Dibujable fvalor=null;
	public Variable(Object valor)
	{
		if(valor instanceof Dibujable)
			fvalor = (Dibujable)valor;
		else if(valor instanceof Simbolo)
			ivalor = new Integer((int)((Simbolo)valor).val);
		else if(valor instanceof Integer)
			ivalor = (Integer)valor;
		else if(valor instanceof Variable)
		{
			this.ivalor = ((Variable)valor).ivalor;
			this.fvalor = ((Variable)valor).fvalor;
		}
	}
	public Variable(Dibujable valor)
	{
		this.fvalor = valor;
	}
	public Variable(Integer valor)
	{
		this.ivalor = valor;
	}
}