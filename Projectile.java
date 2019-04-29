public class Projectile extends Obstacle{
        
    private double angle;
    private double initX,initY;
    private Ghost player;
    
    public Projectile(double x,double y,double angle,Ghost player,String url){
        super(x,y);
        this.initX=x;
        this.initY=y;
        this.angle=angle;
        this.rayon=10;//Rayon au plus petit
        this.player=player;
        this.url=url;
    }
    
    @Override
    public void update(double deltaTime,Ghost ghost){
        //Mise à jour de la position
        this.x+=deltaTime*ghost.getVitesse()/1.5;
        this.y=initY+(this.x-initX)*Math.tan((angle))*.85;
        
        //Regarder s'il y a une collision avec les projectiles
        this.collide=player.collision(this);
    }
    
}
