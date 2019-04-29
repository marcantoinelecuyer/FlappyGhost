import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Obstacle extends Element{
    
    protected String url;
    protected boolean collide;
    protected boolean compter;//Le point a-t-il été donné au joueur?
    
    public Obstacle(double x,double y){
        super(x,y,(int)(Math.random()*35)+10); //rayon entre 10 et 45
        
        url = "images/obstacles/"+(int)(Math.random()*27)+".png";
        compter=false;
        collide=false;
    }
    
    public void setCollision(boolean collision){
        collide=collision;
    }

    /**
     * Cette methode dessine les differents obstables
     * @param context
     * @param debug
     */
    @Override
    public void draw(GraphicsContext context, boolean debug) {
        if (debug) {
            if (this.collide) {
                context.setFill(Color.RED);
                context.fillOval(this.x, this.y, rayon * 2, rayon * 2);
            } else {
                context.setFill(Color.YELLOW);
                context.fillOval(this.x, this.y, rayon * 2, rayon * 2);
            }
        } else {
            context.drawImage(new Image("file:" + url), this.x, this.y, rayon * 2, rayon * 2);
        }
    }

    @Override
    public abstract void update(double deltaTime,Ghost ghost);
}
