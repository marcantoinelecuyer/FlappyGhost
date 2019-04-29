import javafx.scene.canvas.GraphicsContext;

/**
 * C’est la classe abstraite qui est à la base des différents personnages du jeu
 */
public abstract class Element {
    protected double x,y;
    protected int rayon;
    
    public Element(double x,double y,int rayon){
        this.y=y;
        this.x=x;
        this.rayon=rayon;
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
    
    public int getRayon(){
        return rayon;
    }
    public abstract void draw(GraphicsContext context,boolean debug);
    public abstract void update(double deltaTime,Ghost ghost);
}
