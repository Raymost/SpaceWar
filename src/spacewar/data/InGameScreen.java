package spacewar.data;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spacewar.SpaceWar;
import spacewar.sprite.BadShip;
import spacewar.sprite.GoodShip;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InGameScreen extends GeneralScreen{
    private static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    public static final String BACKGROUND_SONG= "";
    public static final String SOUND_EFFECT= "";
    private static final int MAX_ENEMYS=5;

    private Image background;
    private GoodShip ship;
    private BadShip[] enemy;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    private int lives = 5;
    private int points = 0;

    public InGameScreen()
    {
        super();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            enemy = new BadShip[MAX_ENEMYS];
            for (int i=0; i < enemy.length; i++){
                enemy[i] = new BadShip(i,2);
            }

            ship= new GoodShip(lives);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw() {
        reset();

        /*
        // Launches music
        sound= new Media(new File(BACKGROUND_SONG).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        */
        activeKeys.clear();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);

                gc.drawImage(background,0,0);
                ship.draw(gc);
                updateHUD();

                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.GAME_OVER_SCREEN);
                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.CREDITS_SCREEN);
                }

                // Player's movement
                keyPress();
                for (int i=0; i < enemy.length; i++){
                    enemy[i].movement(1,1);
                }

                // Bad ships render loop.
                for (int i=0; i < enemy.length; i++){
                    enemy[i].draw(gc);
                }
            }
        }.start();
    }

    private void keyPress(){
        if (activeKeys.contains(KeyCode.LEFT) && activeKeys.contains(KeyCode.DOWN) ){
            ship.movement(GoodShip.LEFT,GoodShip.DOWN);
        }else if (activeKeys.contains(KeyCode.RIGHT) && activeKeys.contains(KeyCode.DOWN)){
            ship.movement(GoodShip.RIGHT,GoodShip.DOWN);
        }else if (activeKeys.contains(KeyCode.LEFT) && activeKeys.contains(KeyCode.UP)){
            ship.movement(GoodShip.LEFT,GoodShip.UP);
        }else if (activeKeys.contains(KeyCode.RIGHT) && activeKeys.contains(KeyCode.UP)){
            ship.movement(GoodShip.RIGHT,GoodShip.UP);
        }else if (activeKeys.contains(KeyCode.LEFT)){
            ship.movement(GoodShip.LEFT,7);
        } else if (activeKeys.contains(KeyCode.RIGHT)){
            ship.movement(GoodShip.RIGHT, 7);
        } else if (activeKeys.contains(KeyCode.UP)){
            ship.movement(GoodShip.UP,7);
        } else if (activeKeys.contains(KeyCode.DOWN)){
            ship.movement(GoodShip.DOWN,7);
        }
    }

    private void playEffect(String path){
        effect = new Media(new File(path).toURI().toString());
        mediaPlayerEffects = new MediaPlayer(effect);
        mediaPlayerEffects.play();
    }

    private void reset(){
        ship.initialPosition();
        lives = 3;
        points = 0;
        for (int i=0; i < enemy.length; i++){
            enemy[i].moveTo(i*50, 100);
        }
    }

    private void updateHUD(){
        Font myFont = Font.font("Arial",FontWeight.NORMAL,18);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.setEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("Score: " + points, 20,  GeneralScreen.GAME_HEIGHT - 15);

        gc.setFill(Color.YELLOWGREEN);
        gc.setEffect(new DropShadow(6,Color.BLACK));
        gc.fillText("Lives: " + lives,GeneralScreen.GAME_WIDTH - 100, GeneralScreen.GAME_HEIGHT - 15);
    }
}
