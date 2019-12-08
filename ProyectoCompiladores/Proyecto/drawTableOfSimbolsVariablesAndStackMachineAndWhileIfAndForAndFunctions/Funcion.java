import java.awt.*;
import java.util.*;
import java.lang.reflect.*;
public class Funcion
{
	private int pos;
	private int nargs;
	private Stack<Integer> retpc;
	public Funcion(int pos,int nargs)
	{
		this.pos = pos;
		this.nargs = nargs;
		retpc = new Stack<>();
	}

	public int getRetpc()
	{
		return retpc.pop().intValue();
	}

	public void setRetpc(int retpc)
	{
		this.retpc.push(new Integer(retpc));
	}

	public int get_nargs()
	{
		return nargs;
	}

	public void set_nargs(int nargs)
	{
		this.nargs = nargs;
	}

	public int getPos()
	{
		return pos;
	}

	public void setPos(int pos)
	{
		this.pos = pos;
	}
}