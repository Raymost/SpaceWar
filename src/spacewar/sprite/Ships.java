package spacewar.sprite;

import javafx.scene.image.Image;


import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Ships extends AnimatedSprite{
    private int lives;

    public Ships(int lives,int width,int height) {
        super(width, height);
        // TO DO
    }
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void movement(int movement){
        // TO DO
    }

    public void initialPosition(){
        // TO DO
    }
}
