public class Sinus extends Obstacle{
    
    private double origine;
    
    public Sinus(){
        super(645,Math.random()*(300-45)+5);//y entre 50 et 305 moins le rayonMax
        origine=this.y;
        this.rayon=45;
    }
    
    @Override
    public void update(double deltaTime,Ghost ghost){
        this.y=origine+Math.sin(this.x*.02)*50;
        this.x-=deltaTime*ghost.getVitesse();
        
        if(!compter&&this.x+this.rayon < ghost.getX()){
            compter=true;
            ghost.compter();
        }
    }
}
