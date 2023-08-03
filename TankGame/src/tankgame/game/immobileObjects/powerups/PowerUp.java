package tankgame.game.immobileObjects.powerups;

import tankgame.game.Collidable;
import tankgame.game.immobileObjects.ImmobileObject;
import tankgame.game.dynamicObjects.tanks.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends ImmobileObject implements Collidable {

    public PowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public abstract void empower(Tank tank);

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(super.img, (int) x, (int) y, null);
        super.drawHitbox(g);
    }

    @Override
    public void update() {}

    @Override
    public boolean isCollidable() {
        return true;
    }


    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {}

    public abstract void playSound();

}
