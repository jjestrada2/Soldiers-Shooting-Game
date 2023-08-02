package warzone.game.moveableObjects;

import warzone.game.Collidable;
import warzone.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MoveableObject extends GameObject implements Collidable {

    protected float vx;
    protected float vy;
    protected float angle;
    protected float R;



    public MoveableObject(float x, float y, float vx, float vy, float angle, float R, BufferedImage img){
        super(x,y,img);
        this.vx = vx;
        this.vy = vy;
        this.angle=angle;
        this.R = R;
    }
    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public void drawHitbox(Graphics g){
        if(true/*GameState.hitboxState==GameState.HitboxState.ON*/) {
            g.setColor(Color.yellow);
            g.drawRect((int) (x), (int) (y), this.img.getWidth(), this.img.getHeight());
        }
    }
}
