package spacewar.data;


import javafx.animation.AnimationTimer;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spacewar.SpaceWar;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class HighScoresScreen extends InGameScreen {
    private static final String NAMEFILE = "highscores.dat";
    private static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    private static final String HIGHSCORES_SONG="assets/music/highscore_music.wav";
    private static String writeText="";
    private ArrayList<HighScores> highScores = loadScore();
    private static boolean endWrite=true;
    private static boolean onlyOne=true;
    private Image background;

    public HighScoresScreen() throws IOException {
        super();
        showWriteYourName();
        background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
    }

    private void showWriteYourName() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 50);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("HIGHSCORES", 200, 100);

        gc.setFill(Color.RED);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("Write Your Name:", 100, 275);

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);

        myFont = Font.font("System", FontWeight.NORMAL, 50);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.setEffect(bloom);
        gc.fillText(writeText, 100, 400);
    }

    @Override
    public void draw() {
        sound= new Media(new File(HIGHSCORES_SONG).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        activeKeys.clear();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
                gc.drawImage(background,0,0);
                if(endWrite) {
                    writeNameKeys();
                    showWriteYourName();
                }

                if(activeKeys.contains(KeyCode.ENTER)) {
                    this.stop();
                    mediaPlayer.stop();
                    SpaceWar.setScene(SpaceWar.HIGHSCORES_SHOW_SCREEN);
                }

                if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    SpaceWar.exit();
                }
            }
        }.start();
    }

    private void savingKeys(){
        endWrite = false;
        highScores.add(new HighScores(InGameScreen.points, writeText));
        saveScore(highScores);
        highScores = loadScore();
        mediaPlayer.stop();
        SpaceWar.setScene(SpaceWar.HIGHSCORES_SHOW_SCREEN);
    }

    private void writeNameKeys(){
        // Write the inputKeys on screen
        for (KeyCode k : activeKeys) {
            if (k == KeyCode.ENTER) {
                savingKeys();
                return;
            } else if (k == KeyCode.BACK_SPACE) {
                writeText= writeText.substring(0,
                        writeText.length() - 1);
            } else {
                writeText += k;
            }
        }
        activeKeys.clear();
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

    protected void saveScore(List<HighScores> scores){
        // Sorted by Score
        scores.sort(new Comparator<HighScores>() {
            @Override
            public int compare(HighScores h1, HighScores h2) {
                return  h1.getScore() > h2.getScore() ? -1 : 1;
            }
        });
        // Saving the array in a file
        try{
            BufferedWriter outputFile = new BufferedWriter(
                    new FileWriter(new File(NAMEFILE)));

            for (HighScores s:scores ) {
                outputFile.write(s.getScore() + ";" + s.getName());
                outputFile.newLine();
            }
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
