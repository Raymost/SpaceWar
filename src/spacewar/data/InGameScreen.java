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
import spacewar.sprite.*;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InGameScreen extends GeneralScreen{
    private static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    private static final String BACKGROUND_SONG= "";
    private static final String SOUND_EFFECT= "";
    private static final int MAX_ENEMYS=6;
    private static int positionCount=0;
    private static boolean playerDeath=false;

    private Image background;
    private GoodShip ship;
    private static List<BadShip1> enemy1 = new ArrayList<>();
    private static List<BadShip2> enemy2 = new ArrayList<>();
    private static List<BadShip3> enemy3 = new ArrayList<>();
    private static List<BadShip4> enemy4 = new ArrayList<>();
    private Boss boss;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    private int lives = 5;
    private int points = 0;

    public InGameScreen()
    {
        super();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            shipsCharge();
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

                // Enemy movement
                enemy1Movement();

                // Enemy render loop.
                for (int i=0; i < enemy1.size(); i++){
                    enemy1.get(i).draw(gc);
                }
                // Collisions between ships
                collisions();
                if (playerDeath){
                    this.stop();
                    SpaceWar.exit();
                }
            }
        }.start();
    }

    private void collisions(){
        // Collisions with enemy1
        for (int i = 0; i < enemy1.size(); i++) {
            if (enemy1.get(i).collidesWith(ship)){
                if (enemy1.get(i).getLives() > 0){
                    enemy1.get(i).setLives(-1);
                    playEffect(SOUND_EFFECT);
                    if (enemy1.get(i).getLives() == 0){
                        enemy1.remove(i);
                        points +=20;
                    }
                }
                if (ship.getLives() > 0){
                    ship.setLives(lives--);
                    if (ship.getLives() == 0)
                        playerDeath = true;
                ship.initialPosition();
                }
            }
        }
    }

    private void enemy1Movement(){
        int posX;
        int posY;

        // enemyListRefill(positionCount);

        if (positionCount==0){
            posX=50;
            posY=-90;
            for (int i=0; i < enemy1.size(); i++){
                enemy1.get(i).moveTo(posX,posY);
                posX += 100;
                posY -= 90;
            }
            positionCount++;
        } else if (positionCount==1){
            for (int i=0; i < enemy1.size(); i++){
                enemy1.get(i).movement(0,1);
            }
            if (enemy1.get(5).getY() >= GAME_HEIGHT + 81){
                positionCount++;
            }
        }
        if (positionCount==2){
            posX=-90;
            posY=0;
            for (int i=0; i < enemy1.size(); i++){
                enemy1.get(i).moveTo(posX,posY);
                posX -= 100;
                posY -= 90;
            }
            positionCount++;
        } else if (positionCount==3){
            for (int i=0; i < enemy1.size(); i++){
                enemy1.get(i).movement(1,1);
            }
            if (enemy1.get(5).getY() >= GAME_HEIGHT + 81){
                positionCount++;
            }
        }
        if (positionCount==4){
            posX=-90;
            posY=200;
            for (int i=0; i < enemy1.size(); i++){
                enemy1.get(i).moveTo(posX,posY);
                posX -= 100;
            }
            positionCount++;
        } else if (positionCount==5){
            for (int i=0; i < enemy1.size(); i++){
                enemy1.get(i).movement(2,1);
            }
            if (enemy1.get(5).getY() >= GAME_HEIGHT + 81){
                positionCount=0;
            }
        }
    }

    private void enemyListRefill(int when) {
        if (when == 0 || when == 2 || when == 4) {
            if (enemy1.size() < MAX_ENEMYS) {
                int count = MAX_ENEMYS - enemy1.size();
                for (int i = 0; i < count; i++) {
                    enemy1.add(new BadShip1(2));
                }
            }
        }
    }

    private void shipsCharge(){
        // Declare all ships
        for (int i=0; i < MAX_ENEMYS; i++){
            enemy1.add(new BadShip1(2));
        }

        for (int i=0; i < MAX_ENEMYS; i++){
            enemy2.add(new BadShip2(2));
        }

        for (int i=0; i < MAX_ENEMYS; i++){
            enemy3.add(new BadShip3(2));
        }

        for (int i=0; i < MAX_ENEMYS; i++){
            enemy4.add(new BadShip4(2));
        }

        boss = new Boss(1000);
        ship= new GoodShip(lives);
    }

    private void keyPress(){
        // What happen when press a key
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
        }else if (activeKeys.contains(KeyCode.RIGHT)){
            ship.movement(GoodShip.RIGHT, 7);
        }else if (activeKeys.contains(KeyCode.UP)){
            ship.movement(GoodShip.UP,7);
        }else if (activeKeys.contains(KeyCode.DOWN)){
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
