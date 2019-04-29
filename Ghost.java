import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Ghost extends Element{
    
    private double vitesseX;
    private double vitesseY;
    private double gravite;
    private double accY; //Accélération en Y
    private int score;
    private boolean collide;
    
    public Ghost(){
        super(290,200,30);
        vitesseX= 120;
        vitesseY=0;
        gravite=500;
        accY=0;
        score=0;
        collide=false;
    }
    
    public void accelerer(){
        vitesseX+=15;
        if(vitesseX>300)
            vitesseX=300;
        
        gravite+=15;
    }

    /**
     * La méthode fait sauter ghost
     */
    
    public void sauter(){
        vitesseY=-300;
    }
    
    public boolean collision(Obstacle obstacle){
        double deltaX = (this.x+this.rayon) - (obstacle.getX()+obstacle.getRayon());
        double deltaY = (this.y+this.rayon) - (obstacle.getY()+obstacle.getRayon());
        double dCarre = deltaX*deltaX + deltaY*deltaY;
        
        boolean isColliding = dCarre<(this.rayon + obstacle.rayon)*(this.rayon + obstacle.rayon);
        if(isColliding)
            collide=true;
        
        return isColliding;
    }
    public boolean getCollide(){
        return collide;
    }
    public void setCollide(boolean collide){
        this.collide=collide;
    }
     
    public double getVitesse(){
        return vitesseX;
    }
    
    public void compter(){
        score+=5;
        if(score%2==0)
            accelerer();
    }
    public int getScore(){
        return score;
    }
    
    @Override
    public void draw(GraphicsContext context,boolean debug){
        if(debug){
            context.setFill(Color.BLACK);
            context.fillOval(this.x, this.y, 60,60);
        }else{
            context.drawImage(new Image("file:images/ghost.png"), this.x, this.y);
        }
    }

    /**
     *La méthode update permet la mise à jour de la position du ghost
     * @param deltaTime
     * @param ghost
     */
    @Override
    public void update(double deltaTime,Ghost ghost){
        //Mise à jour de la vitesse


            vitesseY += deltaTime * (gravite - accY);

            if (vitesseY > 300)
                vitesseY = 300;
            if (vitesseY < -300)
                vitesseY = -300;

            this.y += deltaTime * vitesseY;

            if (this.y >= 340)
                vitesseY = -200;
            if (this.y <= 0)
                vitesseY = 100;
        }

    }

