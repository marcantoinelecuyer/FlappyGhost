
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *C’est la classe  qui décrit la collision entre le ghost et les différents obstacles
 */
public class Explosion extends Obstacle{
        
        private boolean exploser;
        private Element[] projectiles;
    
    public Explosion(){
        super(645,Math.random()*(150)+100);//y entre 100 et 250
        exploser=false;
        
        projectiles=new Projectile[3];
    }
    
    @Override
    public void draw(GraphicsContext context,boolean debug){
        
        if(exploser){
            for (int i = 0; i < projectiles.length; i++) {
                projectiles[i].draw(context,debug);
            }
        } else {
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
    }
    
    @Override
    public void update(double deltaTime,Ghost ghost){
        //Mise à jour de la position
        if(!exploser)
            this.x-=deltaTime*ghost.getVitesse();
        
        if(!compter&&this.x+this.rayon < ghost.getX()){
            compter=true;
            ghost.compter();
        }
        
        if(!exploser&&this.x<20){
            exploser=true;
            
            for (int i = 0; i < projectiles.length; i++) {
                projectiles[i]=new Projectile(this.x,this.y,(i*10)-10,ghost,url);
            }
        }
        
        if(exploser){
            for (int i = 0; i < projectiles.length; i++) {
                ((Projectile)projectiles[i]).update(deltaTime,ghost);
            }
        }
          
    }
}
