import java.util.ArrayList;

public class ControlleurGUI {
    // La vue est le point d'entrée de l'Application JavaFX
    private FlappyGhost vue;

    public Ghost player;
    private ArrayList<Obstacle> obstacles;
    private boolean debugging;
    private boolean[] twado;
    public double posPause;
    
    ControlleurGUI(FlappyGhost vue) {
        this.vue = vue;
        this.player = new Ghost();
        obstacles=new ArrayList<>();
        debugging=false;//Activation du mode debug
        twado=new boolean[5];//Activation du code secret
    }
    
    public void sauter(){
        ((Ghost) player).sauter();
    }
    public void setDebugging(boolean debug){
        debugging=debug;
    }
    public void collision() {
        
        for (Obstacle obstacle : obstacles) {
            if (((Ghost) player).collision(obstacle)) {
                obstacle.setCollision(true);
            }else{
                obstacle.setCollision(false);
            }
        }
        
        if (!debugging&&player.getCollide()) {//Si le joueur meurt
            vue.recommencer();
        }
        player.setCollide(false);
    }
    /**
     * Changer le score affiché sur la fenetre de jeu
     */
    public void updateScore(){
        vue.changerScore(player.getScore());
    }
    
    public double getVitesse(){
        return player.getVitesse();
    }
    /**
     * Ajoute un type d'obstacle aléatoire
     */
    public void ajouterObstacles() {
        switch ((int) (Math.random() * 5)) {
            case 0:
                obstacles.add(new Simple());
                break;
            case 1:
                obstacles.add(new Sinus());
                break;
            case 2:
                obstacles.add(new Quantique());
                break;
            case 3:
                obstacles.add(new Explosion());
                break;
            case 4:
                obstacles.add(new Oscillation());
                break;
        }
    }

    /**
     * Anime les éléments
     * @param deltaTime 
     */
    public void animer(double deltaTime, boolean pause) {
        //Animer les obstacles

        if (!pause) {
            for (Obstacle obstacle : obstacles) {

                if (obstacle.getX() < -200) {
                    obstacles.remove(obstacle);//Enlever si hors fenetre
                    break;
                } else {
                    vue.draw(obstacle, debugging);
                    obstacle.update(deltaTime, (Ghost) player);
                }
            }

            //Animer le joueur (ghost)
            vue.draw(player, debugging);
            player.update(deltaTime, null);
        }
        else {
            player.setY(posPause);
        }
    }
    
    
    public void codeSecret(String value){
        switch(value){
            case "T":
                twado[0]=true;
                break;
            case "W":
                if(twado[0])
                    twado[1]=true;
                break;
            case "A":
                if(twado[1])
                    twado[2]=true;
                break;
            case "D":
                if(twado[2])
                    twado[3]=true;
                break;
            case "O":
                if(twado[3])
                    twado[4]=true;
                break;
            default://Reset twado si autre caractère
                twado = new boolean[5];
                return;
        }
        //Return si tous les elements de twado ne sont pas true
        for (int i = 0; i < twado.length; i++) {
            if(!twado[i]){
                return;
            }
        }
        //Exécuter le code secret
        twado = new boolean[5];
        vue.codeSecret();
        
    }
}
