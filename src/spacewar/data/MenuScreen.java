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
import spacewar.sprite.GoodShip;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MenuScreen extends GeneralScreen{
    private static final String BACKGROUND_IMAGE="assets/title_menu.png";
    private static final String[] BUTTONS={"assets/start_button_on.png",
            "assets/highscore_button_on.png","assets/exit_button_on.png"};
    private static final String MENU_SONG="";
    private static final String SOUND_EFFECT="";
    private static int count=3;

    private Image background;
    private final Image[] button = new Image[3];
    private MediaPlayer mediaPlayerEffects;
    private Media effect;

    public MenuScreen(){
        super();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            for (int i = 0; i < BUTTONS.length; i++) {
                button[i] = new Image(Files.newInputStream(Paths.get(BUTTONS[i])));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

                // Menu button change
                gc.drawImage(background,0,0);
                if (count == 0){
                    gc.drawImage(button[0],0,0);
                } else if (count >= 1 && count <= 5 ){
                    gc.drawImage(button[1],0,0);
                } else if (count == 6){
                    gc.drawImage(button[2],0,0);
                }

                // Keys for change menu
                if (activeKeys.contains(KeyCode.UP)) {
                    count--;
                    if (count < 0)
                        count = 0;
                }
                if (activeKeys.contains(KeyCode.DOWN)){
                    count++;
                    if (count > 6)
                        count=6;
                }

                // Selected item in menu
                if( count == 0 && activeKeys.contains(KeyCode.ENTER)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.IN_GAME_SCREEN);
                } else if( count >= 1 && count <= 5 && activeKeys.contains(KeyCode.ENTER)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.HIGHSCORES_SHOW_SCREEN);
                } else if( count == 6 && activeKeys.contains(KeyCode.ENTER)) {
                    this.stop();
                    SpaceWar.exit();
                }
                // For emergency exit
                if (activeKeys.contains(KeyCode.ESCAPE)) {
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
