package spacewar.data;


import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spacewar.SpaceWar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IntroScreen extends GeneralScreen{
    public static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    public static final String INTRO_SONG="";

    private Image background;

    public IntroScreen() {
        super();
        showIntro();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showIntro() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 32);
        gc.setFont(myFont);
        gc.setFill(Color.RED);
        gc.fillText("Space WAR", 275, 200);

        myFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(myFont);
        gc.setFill(Color.RED);
        gc.fillText("Press Spacebar to play", 325, 275);
    }

    @Override
    public void draw() {
        activeKeys.clear();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);

                gc.drawImage(background,0,0);
                showIntro();


                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.MENU_SCREEN);
                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.exit();
                }
            }
        }.start();
    }
}
