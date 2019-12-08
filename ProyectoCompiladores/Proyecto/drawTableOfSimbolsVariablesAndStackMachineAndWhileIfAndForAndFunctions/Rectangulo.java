import java.awt.*;


public class Rectangulo implements Dibujable {
	private int x1=0;
	private int y1=0;
	private int x2=0;
	private int y2=0;
	private Color color;
	public Rectangulo(int x1, int y1, int x2, int y2,int color)
	{
		this.x1=x1;
        this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.color = colores[color];
	}
	public void dibuja(Graphics g)
	{
		g.setColor(color);
		g.drawRect(x1,y1,x2,y2);
	}

	public void mover(int x,int y)
	{
		this.x1 = x;
		this.y1 = y;
	}

	public void mover(int x1,int y1,int x2,int y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public void escalar(int e)
	{
		int escalaAlto = (y2/2)*(e-1);
		int escalaAncho = (x2/2)*(e-1);
		this.x1 -= escalaAncho;
		this.y1 -= escalaAlto;
		this.x2 += 2*escalaAncho;
		this.y2 += 2*escalaAlto;
	}
}