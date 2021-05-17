package spacewar.sprite;

public class BadShip extends Ships implements Shoot{
    public static final int[] BAD_SHIP_WIDTH = {0,0,0};
    public static final int[] BAD_SHIP_HEIGHT = {0,0,0};
    private static final String[] IMAGE_PATH = new String[5];
    private static final int SHIP_MOVEMENT = 6;
    private int shootMovement=8;
    private int shipNumber;

    public BadShip(int shipNumber, int lives, int width,int height) {
        super(lives, width, height);
        // TO DO
    }

   public void movement(int movement){
       this.y+= (int) (1 + SHIP_MOVEMENT);
   }

   public void initialPosition(){
        // TO DO
    }
    public int getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(int shipNumber) {
        this.shipNumber = shipNumber;
    }

    @Override
    public void shootingSelect(int shoot) {

    }

    @Override
    public void bigShoot() {

    }

    @Override
    public void normalShoot() {
        this.y+= (int) (1 + shootMovement);
    }
}
