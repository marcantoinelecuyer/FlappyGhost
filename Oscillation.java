
public class Oscillation extends Obstacle{
    
    private double initY;
    
    public Oscillation(){
        super(645,Math.random()*(290)+65);//y entre 65 et 355
        initY=this.y;
    }
    
    @Override
    public void update(double deltaTime,Ghost ghost){
        //Mise à jour de la position
        this.x=(this.x+rayon)-deltaTime*ghost.getVitesse();
        //Oscillation
        this.rayon=(int)(35+30*Math.sin(this.x*.02));
        //Recentrer
        this.y=initY-rayon;
        this.x-=rayon;
        
        if(!compter&&this.x+this.rayon < ghost.getX()){
            compter=true;
            ghost.compter();
        }
    }
}
