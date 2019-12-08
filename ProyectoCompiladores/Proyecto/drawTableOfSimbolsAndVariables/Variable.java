import java.awt.*;


public class Variable {
	private int tipo;
	private double x1;
	private double x2;
	private double y1;
	private double y2;
	private double color;
	private String nombre;


	public Variable(){
	}
	
	public Variable(String nombre){
		this.nombre=nombre;
	}
	
	public Variable(int tipo, double x1, double x2,double y1,double y2,double color){
	System.out.println("VARIABLE CREADA" + x1 + x2+ y1+ y2 + color);
		this.tipo=tipo;
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		this.color=color;}
	
	public Variable(int tipo, double x1, double x2,double y1,double color){
		this.tipo=tipo;
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.color=color;}
	
	
	public void setTipos(int tipo, double x1, double x2,double y1,double y2,double color){
	System.out.println("VARIABLE SETEADA " + x1 + x2+ y1+ y2 + color);
		this.tipo=tipo;
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		this.color=color;}	
		
	public void setTipos(int tipo, double x1, double x2,double y1,double color){
		this.tipo=tipo;
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.color=color;}
	
	public String getNombre() {
        return nombre;
    }
	
	public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	
	
	public double getTipo() {
        return tipo;
    }
	
	public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getColor() {
        return color;
    }

    public void setColor(double color) {
        this.color = color;
    }
	
}
