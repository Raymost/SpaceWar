package spacewar.data;


import javafx.animation.AnimationTimer;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spacewar.SpaceWar;

import java.util.ArrayList;
import java.util.List;

public class GameOverScreen extends GeneralScreen{
    public static final String SOUND_EFFECT="";


    public GameOverScreen(){
        super();
        showGameOverMessage();
    }

    private void showGameOverMessage() {
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.2);

        Font myFont = Font.font("System", FontWeight.NORMAL, 50);
        gc.setFont(myFont);
        gc.setFill(Color.GREENYELLOW);
        gc.setEffect(bloom);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("GAME OVER", 275, 200);

        myFont = Font.font("Arial", FontWeight.NORMAL, 30);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("Press Spacebar to next", 300, 300);
    }
    private void playEffect(String sound){}

    private void putYourName(List<HighScores> name)
    {
        // TO DO
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

                showGameOverMessage();

                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.HIGHSCORES_SCREEN);
                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.exit();
                }
            }
        }.start();
    }
}
