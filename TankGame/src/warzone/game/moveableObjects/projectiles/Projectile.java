package warzone.game.moveableObjects.projectiles;

import warzone.constants.GameConstants;
import warzone.constants.ResourcesConstants;
import warzone.game.stationaryObjects.walls.BreakableWall;
import warzone.game.Collidable;
import warzone.game.stationaryObjects.walls.UnbreakableWall;
import warzone.game.moveableObjects.MoveableObject;
import warzone.game.moveableObjects.tanks.Tank;
import warzone.loaders.ResourcesManager;

import java.awt.image.BufferedImage;

public abstract class Projectile extends MoveableObject {

    protected final Tank ownership;
    protected boolean isDestroyed = false;
    public Projectile(float x, float y, float vx, float vy, float angle, float R, BufferedImage img, Tank ownership){
        super(x,y,vx,vy,angle,R,img);
        this.ownership = ownership;
    }
    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof UnbreakableWall){
            this.isDestroyed = true;

            if(obj instanceof BreakableWall) {
                ((BreakableWall) obj).setDestroyed(true);
            }
        }
        if(obj instanceof Projectile && ((Projectile)obj).getOwnership() != this.ownership){
            this.setDestroyed(true);
           // this.playSound();
        }

    }

    @Override
    public boolean isCollidable() {
        return true;
    }


    @Override
    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int)x,(int)y);

    }

    private void checkBorder(){
        int limitX = ResourcesManager.getSprite(ResourcesConstants.UNBREAK).getWidth();
        int limitY = ResourcesManager.getSprite(ResourcesConstants.UNBREAK).getHeight();
        if( x >= GameConstants.GAME_WORLD_WIDTH - limitX || x<limitX || y<limitY|| y>= GameConstants.GAME_WORLD_HEIGHT-limitY){
            this.isDestroyed =true;

        }
    }

    public Tank getOwnership(){
        return this.ownership;
    }
    public void setDestroyed(boolean status){
        this.isDestroyed = status;
    }

    public boolean getIsDestroyed(){
        return this.isDestroyed;
    }
    public abstract void playSound();



}
