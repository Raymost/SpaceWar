package spacewar.sprite;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BadShip4 extends Ships{
    public static final int BAD_SHIP_WIDTH = 121;
    public static final int BAD_SHIP_HEIGHT = 153;
    private static final String IMAGE_PATH = "assets/badship4.png";
    private static final int SHIP_MOVEMENT = 5;
    private final int SHOOT_MOVEMENT=8;

    public BadShip4( int lives) {
        super(lives, BAD_SHIP_WIDTH, BAD_SHIP_HEIGHT);
        // Create the sprite
        try{
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        }catch (Exception e){
            e.printStackTrace();
        }
        this.spriteX=0;
        this.spriteY=0;
    }

   public void movement(int movement, int movement2){
        moveTo(movement, movement2);
   }

   public void initialPosition(){
       // The initial position of the ship
       moveTo( 300,
               300);
    }
}
