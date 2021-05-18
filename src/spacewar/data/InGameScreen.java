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

    private Image background;
    private GoodShip ship;
    private BadShip badShip;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    private int lives = 5;
    private int points=0;

    public InGameScreen()
    {
        super();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
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
        ship.moveTo(380, 375);
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);

                gc.drawImage(background,0,0);
                ship.draw(gc);
                /*
                if (badShip != null)
                    badShip.draw(gc);

                 */

                updateHUD();

                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.GAME_OVER_SCREEN);
                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.CREDITS_SCREEN);
                } else if (activeKeys.contains(KeyCode.LEFT)){
                    ship.movement(GoodShip.LEFT);
                } else if (activeKeys.contains(KeyCode.RIGHT)){
                    ship.movement(GoodShip.RIGHT);
                } else if (activeKeys.contains(KeyCode.UP)){
                    ship.movement(GoodShip.UP);
                } else if (activeKeys.contains(KeyCode.DOWN)){
                    ship.movement(GoodShip.DOWN);
                }

                // Generate or move fruit
                /*
                if (fruit == null){
                    fruit= new Fruit();
                    fruit.moveTo((int)(Math.random() * (GeneralScreen.GAME_WIDTH - Fruit.FRUIT_WIDTH)),0);
                } else {
                    fruit.move();
                    if (fruit.collidesWith(ship)){ // Collisions
                        if (Fruit.FRUIT_TYPE == 0 || Fruit.FRUIT_TYPE == 1 ||
                                Fruit.FRUIT_TYPE == 2){
                            points +=10;
                        } else if (Fruit.FRUIT_TYPE == 3 ||
                                Fruit.FRUIT_TYPE == 4){
                            points +=20;
                        }
                        fruit.increaseDifficulty();
                        // Launch sound effect when takes a fruit
                        playEffect(SOUND_EFFECT);
                        fruit = null;
                    } else if (fruit.getY() > GeneralScreen.GAME_HEIGHT){
                        lives--;
                        ship.initialPosition();
                        fruit = null;
                    }
                }



                if (lives == 0){
                    this.stop();
                    mediaPlayer.stop();
                    SpaceWar.setScene(
                            SpaceWar.CREDITS_SCREEN);
                }

                 */
            }
        }.start();
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
    }

    private void updateHUD(){
        Font myFont = Font.font("Arial",FontWeight.NORMAL,18);
        gc.setFont(myFont);
        gc.setFill(Color.BLUE);
        gc.setEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("Score: " + points, 20,  GeneralScreen.GAME_HEIGHT - 15);

        gc.setFill(Color.YELLOW);
        gc.setEffect(new DropShadow(6,Color.BLACK));
        gc.fillText("Lives: " + lives,GeneralScreen.GAME_WIDTH - 100, GeneralScreen.GAME_HEIGHT - 15);
    }
}
