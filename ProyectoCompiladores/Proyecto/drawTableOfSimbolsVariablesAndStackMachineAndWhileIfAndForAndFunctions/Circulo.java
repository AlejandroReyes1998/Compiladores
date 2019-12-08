import java.awt.*;

public class Circulo implements Dibujable {
	private int x=0;
	private int y=0;
	private int r=0;
	private Color color;
	public Circulo(int x, int y, int r, int color)
	{
		this.x=x;
		this.y=y;
		this.r=r;
		this.color = colores[color];
	}

	public void dibuja(Graphics g){
  		 g.setColor(color);
		 g.drawOval(x-(r/2),y-(r/2),r,r);
	}

	public void mover(int x,int y)
	{
		this.x = x;
		this.y = y;
	}

	public void mover(int x1,int y1,int x2,int y2){}
	
	public void escalar(int e)
	{
		this.r *= e;	
	}
}