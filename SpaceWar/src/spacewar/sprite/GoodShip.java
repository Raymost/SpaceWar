package spacewar.sprite;

import javafx.scene.image.Image;
import spacewar.data.GeneralScreen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GoodShip extends Ships implements Shoot{
    public static final int GOOD_SHIP_WIDTH = 91;
    public static final int GOOD_SHIP_HEIGHT = 37;
    private static final String IMAGE_PATH = "assets/goodship.png";
    private static final int SHIP_MOVEMENT = 6;

    public GoodShip(int lives) {
        super(lives, GOOD_SHIP_WIDTH, GOOD_SHIP_HEIGHT);
        try{
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e){
            e.printStackTrace();
        }
        spriteXCoordinates[RIGHT] = new int[] {480,576,672,576};
        spriteYCoordinates[RIGHT] = new int[] {0,0,0,0};
        spriteXCoordinates[LEFT] = new int[] {1248,1344,1440,1344};
        spriteYCoordinates[LEFT] = new int[] {0,0,0,0};

        updateSpriteCoordinates();
    }

   public void movement(int movement){
       int newX = x;
       if (movement == LEFT)
           newX -= Math.min(SHIP_MOVEMENT, x);
       else if (movement == RIGHT)
           newX += Math.min(SHIP_MOVEMENT,
                   GeneralScreen.GAME_WIDTH - GOOD_SHIP_WIDTH - x);
       else if (movement == UP)
           newX -= Math.min(SHIP_MOVEMENT, y);
       else if (movement == DOWN)
           newX += Math.min(SHIP_MOVEMENT,
                   GeneralScreen.GAME_HEIGHT - GOOD_SHIP_HEIGHT - y);
       moveTo(newX, y);
       animate(movement);
   }

   public void initialPosition(){
       moveTo( GeneralScreen.GAME_WIDTH / 2 - GOOD_SHIP_WIDTH / 2,
               GeneralScreen.GAME_HEIGHT - 38 - GOOD_SHIP_HEIGHT);
    }

    @Override
    public void shootingSelect(int shoot) {

    }

    @Override
    public void bigShoot() {

    }

    @Override
    public void normalShoot() {

    }
}
