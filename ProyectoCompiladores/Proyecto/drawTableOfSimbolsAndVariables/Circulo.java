import java.awt.*;

public class Circulo implements Dibujable {
	private int x=0;
	private int y=0;
	private int r=0;

	public Circulo(int x, int y, int r)
	{
		this.x=x;
		this.y=y;
		this.r=r;
	}

	public void dibuja(Graphics g){
		 x = x-(r/2);
		 y = y-(r/2);
  		 //g.fillOval(x,y,r,r);
		 g.drawOval(x,y,r,r);
	}
}


