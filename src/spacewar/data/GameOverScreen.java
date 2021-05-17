package spacewar.data;


import java.util.ArrayList;
import java.util.List;

public class GameOverScreen extends GeneralScreen{
    public static final String SOUND_EFFECT="";
    public static final List<HighScoresScreen> score =
            new ArrayList<HighScoresScreen>();

    public GameOverScreen(){
        super();
        showGameOverMessage();
    }

    private void showGameOverMessage() {
        // TO DO
    }
    private void playEffect(String sound){}

    private void putYourName(List<HighScoresScreen> name)
    {
        // TO DO
    }

    @Override
    public void draw() {
       /* TO DO
        public void handle(long currentNanoTime)
        {

        }

        */
    }
}
