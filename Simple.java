public class Simple extends Obstacle {
    
    public Simple(){
        super(645,Math.random()*305);//y entre 0 et 305
    }
    
    @Override
    public void update(double deltaTime,Ghost ghost){
        //Mise à jour de la position
        this.x-=deltaTime*ghost.getVitesse();
        
        if(!compter&&this.x+this.rayon < ghost.getX()){
            compter=true;
            ghost.compter();
        }
    }
    
}
