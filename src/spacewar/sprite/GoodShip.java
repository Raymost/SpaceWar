package spacewar.sprite;

import javafx.scene.image.Image;
import spacewar.data.GeneralScreen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GoodShip extends Ships implements Shoot{
    public static final int GOOD_SHIP_WIDTH = 30;
    public static final int GOOD_SHIP_HEIGHT = 33;
    private static final String IMAGE_PATH = "assets/goodship.png";
    private static final int SHIP_MOVEMENT = 6;

    public GoodShip(int lives) {
        super(lives, GOOD_SHIP_WIDTH, GOOD_SHIP_HEIGHT);
        try{
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e){
            e.printStackTrace();
        }
        spriteXCoordinates[RIGHT] = new int[] {63};
        spriteYCoordinates[RIGHT] = new int[] {0};
        spriteXCoordinates[LEFT] = new int[] {0};
        spriteYCoordinates[LEFT] = new int[] {0};
        spriteXCoordinates[UP] = new int[] {31};
        spriteYCoordinates[UP] = new int[] {0};
        spriteXCoordinates[DOWN] = new int[] {31};
        spriteYCoordinates[DOWN] = new int[] {0};

        updateSpriteCoordinates();
    }
    @Override
    public void movement(int movement){
       int newX = x;
       int newY = y;
       if (movement == LEFT)
           newX -= Math.min(SHIP_MOVEMENT, x);
       else if (movement == RIGHT)
           newX += Math.min(SHIP_MOVEMENT,
                   GeneralScreen.GAME_WIDTH - GOOD_SHIP_WIDTH - x);
       else if (movement == UP)
           newY -= Math.min(SHIP_MOVEMENT, y);
       else if (movement == DOWN)
           newY += Math.min(SHIP_MOVEMENT,
                   GeneralScreen.GAME_HEIGHT - GOOD_SHIP_HEIGHT - y);
       moveTo(newX, newY);
       animate(movement);
    }

    public void initialPosition(){
       moveTo( GeneralScreen.GAME_WIDTH / 2 - GOOD_SHIP_WIDTH / 2,
               GeneralScreen.GAME_HEIGHT / 2 - GOOD_SHIP_HEIGHT / 2);
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
