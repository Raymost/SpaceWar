package spacewar.data;


import java.util.ArrayList;
import java.util.List;

public class HighScoresScreen extends GeneralScreen{
    public static int score=0;
    private String name;

    public HighScoresScreen(int score, String name){
        super();
        showHighScores();
    }

    private void showHighScores() {
        // TO DO
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void resetHighScores(){}

    @Override
    public void draw() {
       /* TO DO
        public void handle(long currentNanoTime)
        {

        }

        */
    }
}
