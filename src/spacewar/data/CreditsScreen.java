package spacewar.data;


import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import spacewar.SpaceWar;

public class CreditsScreen extends GeneralScreen{
    private static int move=825;

    public CreditsScreen(){
        super();
        showCredits(0);
    }

    private void showCredits(int move) {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 32);
        gc.setFont(myFont);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText("Developed by", GAME_WIDTH / 2 - 75,move);
        gc.fillText("Joaquin Ferreras Torralba", GAME_WIDTH / 2 - 125,move+40);
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

                showCredits(move);
                move--;
                if(activeKeys.contains(KeyCode.ENTER)) {
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
