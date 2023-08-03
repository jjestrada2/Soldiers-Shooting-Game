package tankgame.game.dynamicObjects.projectiles;

import tankgame.constants.GameScreenConstants;
import tankgame.constants.ResourceConstants;
import tankgame.util.ResourceManager;
import tankgame.game.Collidable;
import tankgame.game.dynamicObjects.DynamicObject;
import tankgame.game.dynamicObjects.tanks.Tank;
import tankgame.game.immobileObjects.BreakableWall;
import tankgame.game.immobileObjects.Wall;

import java.awt.image.BufferedImage;

public abstract class Projectile extends DynamicObject {

    protected final Tank ownership;
    protected boolean isDestroyed = false;

    public Projectile(float x, float y, float vx, float vy, float angle, float R, BufferedImage img, Tank ownership) {
        super(x, y, vx, vy, angle, R, img);
        this.ownership = ownership;
    }

    @Override
    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void checkBorder() {
        int limitX = ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth();
        int limitY = ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight();
        if(x >= GameScreenConstants.WORLD_WIDTH - limitX || x < limitX || y < limitY || y >= GameScreenConstants.WORLD_HEIGHT - limitY) {
            this.isDestroyed = true;
            this.playSound();
        }
    }

    public Tank getOwnership() {
        return this.ownership;
    }

    public void setDestroyed(boolean status) {
        this.isDestroyed = status;
    }

    public abstract void playSound();

    public boolean getIsDestroyed() {
        return this.isDestroyed;
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof Wall) {
            this.isDestroyed = true;
            if(obj instanceof BreakableWall) {
                ((BreakableWall) obj).setDestroyed(true);
                ((BreakableWall) obj).playSound();
            }
            this.playSound();
        }
        if(obj instanceof Projectile && ((Projectile) obj).getOwnership() != this.ownership) {
            this.setDestroyed(true);
            this.playSound();
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

}
