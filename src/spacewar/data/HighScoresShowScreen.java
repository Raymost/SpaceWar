package spacewar.data;


import javafx.animation.AnimationTimer;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spacewar.SpaceWar;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoresShowScreen extends GeneralScreen {
    private static final String NAMEFILE = "highscores.dat";
    private static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    private final ArrayList<HighScores> highScores = loadScore();
    private Image background;

    public HighScoresShowScreen() throws IOException {
        super();
        showHighScore();
        background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
    }

    private void showHighScore() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 40);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("HIGHSCORES", 350, 100);

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);

        myFont = Font.font("System", FontWeight.NORMAL, 20);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.setEffect(bloom);
        for (int i = 0; i < 10; i++) {
            String line = highScores.get(i).getScore() + " - " + highScores.get(i).getName();
            gc.fillText(line, 300, 400);
        }
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
                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    SpaceWar.setScene(SpaceWar.CREDITS_SCREEN);
                }

                if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.exit();
                }
            }
        }.start();
    }

    private ArrayList<HighScores> loadScore(){
        ArrayList<HighScores> scores = new ArrayList<HighScores>();
        if (new File(NAMEFILE).exists() ) {
            try {

                BufferedReader inputFile = new BufferedReader(
                        new FileReader(new File(NAMEFILE)));

                String line = inputFile.readLine();
                while (line != null) {
                    String[] items = line.split(";");
                    scores.add(new HighScores(Integer.parseInt(items[0]),
                            items[1]));
                    line = inputFile.readLine();
                }
                inputFile.close();

            } catch (IOException fileError){
                System.out.println("We have a problem: " +
                                fileError.getMessage());
            }
        }
        return scores;
    }
}
