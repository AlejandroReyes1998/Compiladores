import java.awt.*;

interface Dibujable {
	Color[] colores = {Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
	public void dibuja(Graphics g);
	public void mover(int x,int y);
	public void mover(int x1,int y1, int x2,int y2);
}