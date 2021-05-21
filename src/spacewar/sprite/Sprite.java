package spacewar.sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class defines all parameter for using sprites and movements
 * on the canvas
 */
public class Sprite {
    protected int width,height;
    protected int x, y;
    protected int spriteX, spriteY;
    protected Image spriteImage;

    /**
     * This constructor only receives width and height of the sprite
     * @param width
     * @param height
     */
    public Sprite(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * This define the all the movement on screen
     * @param x
     * @param y
     */
    public void moveTo(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * This method draw the sprite
     * @param gc
     */
    public void draw(GraphicsContext gc){
        gc.drawImage(spriteImage, spriteX,spriteY,
                width,height,x,y,width,height);
    }

    /**
     * This method creates a rectangle of the sprites
     * for using it later
     * @return position and sprite
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * This method calculates when a sprite collides with
     * another sprite
     * @param sp
     * @return true if the sprites collides
     */
    public boolean collidesWith(Sprite sp){
        return sp.getBoundary().intersects(this.getBoundary());
    }
}
