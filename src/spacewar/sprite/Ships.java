package spacewar.sprite;

public abstract class Ships extends AnimatedSprite{
    private int lives;

    public Ships(int lives,int width,int height) {
        super(width, height);
    }
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public abstract void movement(int movement,int movement2);

    public abstract void initialPosition();
}
