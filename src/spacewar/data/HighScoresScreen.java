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

public class HighScoresScreen extends InGameScreen {
    private static final String NAMEFILE = "highscores.dat";
    private static final String BACKGROUND_IMAGE="assets/starfield_intro.png";
    private static String writeText;
    private final ArrayList<String> writeKey = loadKeys();
    private final ArrayList<HighScores> highScores = loadScore();
    private static boolean endWrite=true;
    private static boolean onlyOne=true;
    private Image background;

    public HighScoresScreen() throws IOException {
        super();
        showWriteYourName();
        background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
    }

    private void showWriteYourName() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 40);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("HIGHSCORES", 350, 200);

        gc.setFill(Color.RED);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.fillText("Write Your Name:", 275, 275);

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);

        myFont = Font.font("System", FontWeight.NORMAL, 30);
        gc.setFont(myFont);
        gc.setFill(Color.YELLOWGREEN);
        gc.applyEffect(new DropShadow(5,Color.BLACK));
        gc.setEffect(bloom);
        gc.fillText(writeText, 300, 400);
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
                if(endWrite) {
                    showWriteYourName();
                    writeNameKeys();
                }
                savingKeys();

                if(activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
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
        if (onlyOne) {
            if (activeKeys.contains(KeyCode.ENTER)) {
                endWrite = false;
                //loadPoints();
                highScores.add(new HighScores(InGameScreen.points, writeText));
                saveScore(highScores);
                onlyOne = false;
            }
        }
    }

    private void writeNameKeys(){
        // Write the inputKeys on screen
        for (int i = 0; i < writeKey.size(); i++) {
            if(activeKeys.contains(writeKey.get(i))){
                String[] items = writeKey.get(i).split(".");
                writeText+=items[1];
                System.out.println(writeText);
            }
        }
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

    protected ArrayList<String> loadKeys(){
        // Loading the input keys for write a name
        ArrayList<String> inputKeys = new ArrayList<String>();
        if (new File("inputKeys.dat").exists() ) {
            try {
                BufferedReader inputFile = new BufferedReader(
                        new FileReader(new File("inputKeys.dat")));

                String line = inputFile.readLine();
                while (line != null) {
                    inputKeys.add(line);
                    line = inputFile.readLine();
                }
                inputFile.close();

            } catch (IOException fileError){
                System.out.println("We have a problem: " +
                        fileError.getMessage());
            }
        }
        return inputKeys;
    }
/*
    protected void loadPoints(){
        // Loading the points
        if (new File("points.dat").exists() ) {
            try {
                BufferedReader inputFile = new BufferedReader(
                        new FileReader(new File("points.dat")));
                String line = inputFile.readLine();
                points = Integer.parseInt(line);
                inputFile.close();
            } catch (IOException fileError){
                System.out.println("We have a problem: " +
                        fileError.getMessage());
            }
        }
    }

 */

    protected void saveScore(List<HighScores> scores){
        // Sorted by Score
        scores.sort(new Comparator<HighScores>() {
            @Override
            public int compare(HighScores h1, HighScores h2) {
                return Integer.toString( h1.getScore()).compareTo(Integer.toString(h2.getScore()));
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
