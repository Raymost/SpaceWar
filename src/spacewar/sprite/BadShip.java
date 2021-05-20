package spacewar.sprite;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BadShip extends Ships implements Shoot{
    public static final int[] BAD_SHIP_WIDTH = {76,94,123,121,195};
    public static final int[] BAD_SHIP_HEIGHT = {80,86,90,153,208};
    private static final String[] IMAGE_PATH = {"assets/badship1.png",
            "assets/badship2.png","assets/badship3.png","assets/badship4.png","assets/boss.png",};
    private static final int SHIP_MOVEMENT = 6;
    private final int SHOOT_MOVEMENT=8;
    private int shipNumber;

    public BadShip(int shipNumber, int lives) {
        super(lives, BAD_SHIP_WIDTH[shipNumber], BAD_SHIP_HEIGHT[shipNumber]);
        // Create the sprite
        try{
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH[shipNumber])));
        }catch (Exception e){
            e.printStackTrace();
        }
        this.spriteX=0;
        this.spriteY=0;
    }

   public void movement(int movement, int movement2){
       this.y+= (int) (1 + SHIP_MOVEMENT);
   }

   public void initialPosition(){
       // The initial position of the ship
       moveTo( 300,
               300);
    }
    public int getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(int shipNumber) {
        this.shipNumber = shipNumber;
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
