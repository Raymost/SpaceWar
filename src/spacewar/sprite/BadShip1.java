package spacewar.sprite;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BadShip1 extends Ships implements Shoot{
    public static final int BAD_SHIP_WIDTH = 76;
    public static final int BAD_SHIP_HEIGHT = 80;
    private static final String IMAGE_PATH = "assets/badship1.png";
    private static final int SHIP_MOVEMENT = 3;
    private final int SHOOT_MOVEMENT=8;



    public BadShip1( int lives) {
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

        if (movement == 0){
            this.y+=(1 + SHIP_MOVEMENT);
        } else if (movement == 1) {
            this.x+=(1 + SHIP_MOVEMENT);
            this.y+=(1 + SHIP_MOVEMENT);
        } else if (movement == 2) {
            this.x+=(1 + SHIP_MOVEMENT);
        }
   }

   public void initialPosition(){
       // The initial position of the ship
       moveTo( 300,
               300);
    }
    @Override
    public void shootingSelect(int shoot) {
        this.y+= (int) (1 + SHOOT_MOVEMENT);
    }

    @Override
    public void bigShoot() {

    }

    @Override
    public void normalShoot() {
        this.y+= (int) (1 + SHOOT_MOVEMENT);
    }
}
