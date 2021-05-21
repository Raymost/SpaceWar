package spacewar.data;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
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
    private static boolean shootMore=true;

    private Image background;
    private static List<GoodShoot> goodShoot = new ArrayList<>();
    private GoodShip ship;
    private static List<BadShip1> enemy1 = new ArrayList<>();
    private static List<BadShip2> enemy2 = new ArrayList<>();
    private static List<BadShip3> enemy3 = new ArrayList<>();
    private static List<BadShip4> enemy4 = new ArrayList<>();
    private Boss boss;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    private int lives = 6;
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

                // Collisions between ships
                collisions();

                // Exit to Game Over when player is dead
                if (playerDeath){
                    this.stop();
                    SpaceWar.setScene(SpaceWar.GAME_OVER_SCREEN);
                }

                // Shoots of the Good ship
                goodShootMovement();
            }
        }.start();

    } private void goodShootInitialize(){
        // Set initial position of the shoot
        goodShoot.add(new GoodShoot());
        int lastShoot = goodShoot.size() - 1;
        goodShoot.get(lastShoot).
                initialPosition(ship.getX(),ship.getY());
    }

    private void goodShootMovement(){
        // Movement of the shoot
        for (int i = 0; i < goodShoot.size(); i++) {
            if (goodShoot.get(i) !=null ){
                goodShoot.get(i).draw(gc);
                goodShoot.get(i).movement(1);
            }
            // Removing a shoot
            if (goodShoot.get(i).getX() == 0  ||
                    goodShoot.get(i).getY() == 0){
                goodShoot.remove(i);
            }
        }
    }

    private void collisions() {
        // Collisions with enemy1 and shoot
        for (BadShip1 ship1 : enemy1) {
            if (ship1.collidesWith(ship)) {
                ship1.setLives(-1);
                ship.setLives(lives--);
                ship.initialPosition();
                points += 20;
                //playEffect(SOUND_EFFECT);
                if (ship.getLives() == 0)
                    playerDeath = true;
            }
            for (GoodShoot shoot : goodShoot) {
                if (ship1.collidesWith(shoot)) {
                    ship1.setLives(-1);
                    points += 20;
                    //playEffect(SOUND_EFFECT);
                }
            }
            if (ship1.getLives() > 0) {
                ship1.draw(gc);
            }
        }
    }

    private void enemyNoLives(int enemy,int i, int posX, int posY){
        if (enemy == 1) {
            if (enemy1.get(i).getLives() > 0)
                enemy1.get(i).moveTo(posX, posY);
            else {
                enemy1.get(i).moveTo(-150, 900);
            }
        } else if (enemy == 2) {
            if (enemy2.get(i).getLives() > 0)
                enemy2.get(i).moveTo(posX, posY);
            else {
                enemy2.get(i).moveTo(-150, 900);
            }
        } else if (enemy == 3) {
            if (enemy3.get(i).getLives() > 0)
                enemy3.get(i).moveTo(posX, posY);
            else {
                enemy3.get(i).moveTo(-150, 900);
            }
        } else if (enemy == 4) {
            if (enemy3.get(i).getLives() > 0)
                enemy3.get(i).moveTo(posX, posY);
            else {
                enemy3.get(i).moveTo(-150, 900);
            }
        }
    }

    private void enemy1Movement(){
        int posX;
        int posY;
        // Set initial position of the wave
        if (positionCount==0){
            enemyListRefill();
            posX=50;
            posY=-90;
            for (int i=0; i < enemy1.size(); i++){
                enemyNoLives(1,i,posX,posY);
                posX += 100;
                posY -= 90;
            }
            positionCount++;
        // Doing the movement
        } else if (positionCount==1){
            for (BadShip1 badShip1 : enemy1) {
                badShip1.movement(0, 1);
            }
            if (enemy1.get(5).getY() >= GAME_HEIGHT + 81){
                positionCount++;
            }
        }
        if (positionCount==2){
            enemyListRefill();
            posX=-90;
            posY=0;
            for (int i=0; i < enemy1.size(); i++){
                enemyNoLives(1,i,posX,posY);
                posX -= 100;
                posY -= 90;
            }
            positionCount++;
        } else if (positionCount==3){
            for (BadShip1 badShip1 : enemy1) {
                badShip1.movement(1, 1);
            }
            if (enemy1.get(5).getY() >= GAME_HEIGHT + 81){
                positionCount++;
            }
        }
        if (positionCount==4){
            enemyListRefill();
            posX=-90;
            posY=200;
            for (int i=0; i < enemy1.size(); i++){
                enemyNoLives(1,i,posX,posY);
                posX -= 100;
            }
            positionCount++;
        } else if (positionCount==5){
            for (BadShip1 badShip1 : enemy1) {
                badShip1.movement(2, 1);
            }
            if (enemy1.get(5).getX() >= GAME_WIDTH +
                    BadShip1.BAD_SHIP_WIDTH){
                positionCount=0;
            }
        }
    }

    private void enemyListRefill() {
        // Refill ships after a wave
        enemy1.clear();
        for (int i = 0; i < MAX_ENEMYS; i++) {
            enemy1.add(new BadShip1(1));
            enemy1.get(i).setLives(1);
        }
    }

    private void shipsCharge(){
        // Declare all ships
        for (int i=0; i < MAX_ENEMYS; i++){
            enemy1.add(new BadShip1(1));
            enemy1.get(i).setLives(1);
        }

        for (int i=0; i < MAX_ENEMYS; i++){
            enemy2.add(new BadShip2(1));
            enemy2.get(i).setLives(4);
        }

        for (int i=0; i < MAX_ENEMYS; i++){
            enemy3.add(new BadShip3(1));
            enemy3.get(i).setLives(8);
        }

        for (int i=0; i < MAX_ENEMYS; i++){
            enemy4.add(new BadShip4(1));
            enemy4.get(i).setLives(16);
        }

        boss = new Boss(1000);
        ship= new GoodShip(lives);
    }

    private void keyPress(){
        // Player Movement keys
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
        // Shooting key
        if(activeKeys.contains(KeyCode.C)) {
           if(shootMore)
            goodShootInitialize();
            shootMore=false;
        }
        if(releasedKeys.contains(KeyCode.C))
            shootMore=true;
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
        Font myFont = Font.font("System",FontWeight.NORMAL,18);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.setEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("Score: " + points, 20,  GeneralScreen.GAME_HEIGHT - 15);

        gc.setFill(Color.YELLOWGREEN);
        gc.setEffect(new DropShadow(6,Color.BLACK));
        gc.fillText("Lives: " + lives,GeneralScreen.GAME_WIDTH - 100, GeneralScreen.GAME_HEIGHT - 15);
    }
}
