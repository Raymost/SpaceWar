package spacewar;

import javafx.application.Application;
import javafx.stage.Stage;
import spacewar.data.*;

public class SpaceWar extends Application {
    public static final int MAX_SCENES = 6;
    public static final int INTRO_SCREEN = 0;
    public static final int MENU_SCREEN = 1;
    public static final int IN_GAME_SCREEN = 2;
    public static final int GAME_OVER_SCREEN = 3;
    public static final int HIGHSCORES_SCREEN = 4;
    public static final int CREDITS_SCREEN = 5;

    public static final GeneralScreen[] scenes =
            new GeneralScreen[MAX_SCENES];

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        scenes[0] = new IntroScreen();
        scenes[1] = new MenuScreen();
        scenes[2] = new InGameScreen();
        scenes[3] = new GameOverScreen();
        scenes[4] = new HighScoresScreen(0, "name");
        scenes[5] = new CreditsScreen();


        stage.setTitle("SpaceWar");
        setScene(INTRO_SCREEN);
        stage.show();
    }

    public static void setScene(int numScene){
        stage.setScene(scenes[numScene]);
        scenes[numScene].draw();
    }

    public static void exit(){
        stage.hide();
    }
    public static void main(String[] args) {
        launch(args);}
}
