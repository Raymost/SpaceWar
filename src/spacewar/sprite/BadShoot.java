package spacewar.sprite;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class of the enemy's shoots
 */
public class BadShoot extends AnimatedSprite{
    public static final int BAD_SHOOT_WIDTH = 26;
    public static final int BAD_SHOOT_HEIGHT = 42;
    private static final String IMAGE_PATH = "assets/shoot_bad_ship.png";
    private static final int SHOOT_MOVEMENT = 6;

    /**
     * Constructor of the shoot, defines the sprites
     */
    public BadShoot() {
        super(BAD_SHOOT_WIDTH, BAD_SHOOT_HEIGHT);
        // Create the sprite
        try{
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        }catch (Exception e){
            e.printStackTrace();
        }
        spriteXCoordinates[C] = new int[] {0,39,77,117};
        spriteYCoordinates[C] = new int[] {0,0,0,0};
    }

    /**
     * Movement of the shoot on screen
     * @param select when have more options of shooting movement
     */
    public void movement(int select){
        this.y += SHOOT_MOVEMENT;
    }

    /**
     * Method of the initial position of the shoot
     * @param posX position on X
     * @param posY position on Y
     */
    public void initialPosition(int posX, int posY){
        // The initial position of the shoot
        moveTo( posX,
                posY);
    }
}
