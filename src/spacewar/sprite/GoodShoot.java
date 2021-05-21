package spacewar.sprite;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class of the players shoot
 */
public class GoodShoot extends AnimatedSprite{
    public static final int GOOD_SHOOT_WIDTH = 21;
    public static final int GOOD_SHOOT_HEIGHT = 45;
    private static final String IMAGE_PATH = "assets/shoot_good_ship.png";
    private static final int SHOOT_MOVEMENT = 6;

    /**
     * Define the sprite of the shoot
     */
    public GoodShoot() {
        super(GOOD_SHOOT_WIDTH, GOOD_SHOOT_HEIGHT);
        // Create the sprite
        try{
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        }catch (Exception e){
            e.printStackTrace();
        }
        spriteXCoordinates[C] = new int[] {0,35,70,105};
        spriteYCoordinates[C] = new int[] {0,0,0,0};
    }

    /**
     * Method of the movement of the shoot
     * @param select
     */
    public void movement(int select){
        this.y -= SHOOT_MOVEMENT;
    }

    /**
     * Initial position of the shoot
     * @param posX X position of the ship
     * @param posY Y position of the ship
     */
    public void initialPosition(int posX, int posY){
        // The initial position of the shoot
        moveTo( posX,
                posY);
    }
}
