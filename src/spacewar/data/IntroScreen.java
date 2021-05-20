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
    private static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    private static final String SHIP_IMAGE="assets/goodship_intro.png";
    public static final String INTRO_SONG="";
    public double move=-1800;
    public double ship_move=-100;

    private Image background;
    private Image goodship;

    public IntroScreen() {
        super();
        showIntro();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            goodship = new Image(Files.newInputStream(Paths.get(SHIP_IMAGE)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showIntro() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 25);
        gc.setFont(myFont);
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,700,150);
        gc.fillRect(0,650,700,150);
        gc.setFill(Color.RED);
        gc.fillText("Deep space, a explorer ship travels to a galaxy", 50, 700);
        gc.fillText("searching for new planets for life, until now...", 50, 730);
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

                gc.drawImage(background,move,0);
                gc.drawImage(goodship,ship_move,100);
                showIntro();
                move=move+0.5;
                ship_move=ship_move+0.2;

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
