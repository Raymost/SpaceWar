package spacewar.data;


import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spacewar.SpaceWar;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MenuScreen extends GeneralScreen{
    public static final String BACKGROUND_IMAGE="assets/title_menu.png";
    public static final String MENU_SONG="";
    public static final String SOUND_EFFECT="";

    private Image background;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;

    public MenuScreen(){
        super();
        showMenu();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showMenu() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 32);
        gc.setFont(myFont);
        gc.setFill(Color.RED);
        gc.fillText("MENUUUUUUUUU", 275, 200);

        myFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(myFont);
        gc.setFill(Color.RED);
        gc.fillText("Press Spacebar to play", 325, 275);
    }

    @Override
    public void draw() {
      /*  sound= new Media(new File(MENU_SONG).toURI().toString());
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
                showMenu();

                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.IN_GAME_SCREEN);
                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.exit();
                }
            }
        }.start();
    }

    private void playEffect(String path){
        effect = new Media(new File(path).toURI().toString());
        mediaPlayerEffects = new MediaPlayer(effect);
        mediaPlayerEffects.play();
    }
}
