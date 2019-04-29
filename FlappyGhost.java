/**
 * Fait par Marc-Antoine Lecuyer et Sanon Dougece
 */ 
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * La classe principale du programme
 */
public class FlappyGhost extends Application {
    private ControlleurGUI controlleur;
    
    GraphicsContext context;
    Text scoreText;
    Pane fenetre;//Fenetre de jeu
    boolean pause;

    
    @Override
    public void start(Stage primaryStage) {
        controlleur = new ControlleurGUI(this);
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 640, 440);

        Canvas canvas = new Canvas(640, 440);
        root.getChildren().add(canvas);

        context = canvas.getGraphicsContext2D();
        
        fenetre=new Pane();
        
        ImageView background1=new ImageView(new Image("file:images/bg.png"));
        background1.setFitHeight(400);
        ImageView background2=new ImageView(new Image("file:images/bg.png"));
        background2.setFitHeight(400);
        
        fenetre.getChildren().add(background1);
        fenetre.getChildren().add(background2);
        background2.setX(640);

        Button pauseButton= new Button("Pause");
        pauseButton.setMinWidth(70);

        CheckBox modeDebug= new CheckBox("Mode debug");
        modeDebug.setOnAction((event -> {

            if(modeDebug.isSelected()){
                controlleur.setDebugging(true);
            } else{
                controlleur.setDebugging(false);
            }
            root.requestFocus();
        }));
        
        scoreText = new Text("Score: 0");
        
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);
        
        HBox menu=new HBox(pauseButton,separator1,modeDebug,separator2,scoreText);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(7));
        menu.setStyle("-fx-background-color : #F4F4F4;");
        
        fenetre.getChildren().add(canvas);
        root.setCenter(fenetre);
        root.setBottom(menu);

        primaryStage.getIcons().add(new Image("file:images/ghost.png"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Flappy Ghost");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        /* Après l’exécution de la fonction, le
            focus va automatiquement au canvas */
        Platform.runLater(() -> {
            root.requestFocus();
        });
        /* Lorsqu’on clique ailleurs sur la scène,
            le focus retourne sur le canvas */
        scene.setOnMouseClicked((event) -> {
            root.requestFocus();
        });
        
        AnimationTimer timer = new AnimationTimer() {
            public long lastTime = System.nanoTime();
            private double troisSec=0;


            @Override
            public void handle(long now) {
                double deltaTime= (now - lastTime) * 1e-9;

                if(!pause){
                troisSec+=deltaTime;
                }
                context.clearRect(0, 0, 640, 440);

                if(!pause){
                    animerBackground(background1, background2,
                            deltaTime * controlleur.getVitesse());
                }
                //Ajouter un obstacle aux 3 secondes
                if(Math.floor(troisSec)%4==0){
                    controlleur.ajouterObstacles();
                    troisSec=1;
                }
                      
                //Animer les obstacles
                controlleur.animer(deltaTime, pause);
                controlleur.collision();
                controlleur.updateScore();
               
                lastTime = now;
                resetPause();
            }

        };

        timer.start();
        pauseButton.setOnAction(event -> {

            if (pauseButton.getText().equals("Pause")){
                pauseButton.setText("Resume");
                pauserJeu();
                timer.stop();


            } else
            {
                pauseButton.setText("Pause");
                timer.start();
            }

        });

        scene.setOnKeyPressed((value) -> {
            if (value.getCode() == KeyCode.SPACE) {
                controlleur.sauter();
            }else{
            //Code secret
                controlleur.codeSecret(value.getCode().toString());
            }
            
        });
    }

    /**
     * 
     * @param bg1 une premiere photo du background
     * @param bg2 une deuxieme photo du background
     * @param vitesse 
     * 
     * Cette procédure anime les backgrounds pour qu'ils défilent un après 
     * l'autre. Lorsqu'un background est au bout de la fenêtre, il se 
     * repositionne au début de celle-ci.
     */
    public void animerBackground(ImageView bg1,ImageView bg2,double vitesse){
        
                bg1.setX(bg1.getX()-vitesse);
                bg2.setX(bg2.getX()-vitesse);
                
                if((int)bg1.getX()<=0)
                    bg2.setX(bg1.getX()+640);
                
                if((int)bg2.getX()<=0)
                    bg1.setX(bg2.getX()+640);
    }
    
    public void draw(Element elem,boolean debug){
        elem.draw(context,debug);
    }
    
    public void changerScore(int score){
        scoreText.setText("Score: "+score);
    }

    public void pauserJeu(){
        this.pause=true;
        controlleur.posPause=controlleur.player.getY();
    }

    public void resetPause() {this.pause=false;}

    public void recommencer(){
        controlleur = new ControlleurGUI(this);
    }
    /**
     * Fait un effet miroir en Y sur la fenetre (background et elements du jeu)
     */
    public void codeSecret(){
        fenetre.setScaleY(fenetre.getScaleY()*-1);
    }


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
