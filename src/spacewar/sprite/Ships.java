package spacewar.sprite;

/**
 * This class is the parent class of all the ships
 */
public abstract class Ships extends AnimatedSprite{
    private int lives;

    /**
     * Constructor receives the lives, width and height of the ships
     * @param lives
     * @param width
     * @param height
     */
    public Ships(int lives,int width,int height) {
        super(width, height);
        this.setLives(lives);
    }

    /**
     * For consulting the number of lives remaining
     * @return
     */
    public int getLives() {
        return lives;
    }

    /**
     * For set the number of lives of the ships
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Method for if the ship was hit for something rest a life
     */
    public void hit(){this.setLives(this.getLives()-1);}

    /**
     * For the inheritance of the movement for the ships
     * @param movement
     * @param movement2
     */
    public abstract void movement(int movement,int movement2);

    /**
     * For the initial position of the ships
     */
    public abstract void initialPosition();
}
