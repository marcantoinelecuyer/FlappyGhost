public class Quantique extends Obstacle{
    
    private double lifeTime;
    
    public Quantique(){
      super(645,Math.random()*305);//y entre 0 et 305
      lifeTime=0;
    }
    
    @Override
    public void update(double deltaTime, Ghost ghost){
        this.x-=deltaTime*ghost.getVitesse();
        
        lifeTime+=deltaTime;
        double newX;
        double newY;
        
        if(Math.floor(lifeTime*10)/10%.2==0){//Chaque .2 seconde
            
            do {
                newX = this.x + Math.random() * 60 - 30;
                newY = this.y + Math.random() * 60 - 30;
            } while (newY > (400 - this.rayon) || newY < 0
                    || (this.x + this.rayon < ghost.getX() //obstacle a dépasser ghost
                        ^ newX + this.rayon < ghost.getX()));//l'obstacle recule
            
            this.x = newX;
            this.y = newY;
        }
        
        if(!compter&&this.x+this.rayon < ghost.getX()){
            compter=true;
            ghost.compter();
        }
        
    }
}
