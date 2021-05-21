package spacewar.sprite;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class of the Boss enemy
 */
public class Boss extends Ships{
    public static final int BAD_SHIP_WIDTH = 195;
    public static final int BAD_SHIP_HEIGHT = 208;
    private static final String IMAGE_PATH = "assets/boss.png";
    private static final int SHIP_MOVEMENT = 4;

    /**
     * Constructor of teh boss, define sprites
     * @param lives
     */
    public Boss(int lives) {
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

    /**
     * Method for moving the enemy boss
     * @param movement
     * @param movement2
     */
   public void movement(int movement, int movement2){
       if (movement == 0){
           this.y+=(1 + SHIP_MOVEMENT);
       } else if (movement == 1) {
          if (this.x < 580 )
               this.x+=(1 + SHIP_MOVEMENT);
          else
               this.x-=(1 + SHIP_MOVEMENT);
       }
   }

    /**
     * Method of initial position of the enemy boss
     */
   public void initialPosition(){
       // The initial position of the ship
       moveTo( 300,
               -215);
    }
}
