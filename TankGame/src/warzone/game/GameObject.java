package warzone.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected BufferedImage img;
    protected Rectangle hitBox;
    public GameObject(float x, float y,BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());

    }
/*
    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException{
        return switch (type){
            case "9" -> new UnbreakableWall(x,y,ResourcesManager.getSprite("unbreak"));
            case "1" -> new BreakableWall(x,y, ResourcesManager.getSprite("break1"));
            case "2" -> new BreakableWall(x,y, ResourcesManager.getSprite("break2"));
            case "3" -> new BreakableWall(x,y, ResourcesManager.getSprite("break3"));
            case "4" -> new Health(x,y,ResourcesManager.getSprite("break4"));
            case "5" -> new Speed(x,y,ResourcesManager.getSprite("break4"));
            case "6" -> new Shield(x,y,ResourcesManager.getSprite("shield"));
            case "7" -> new BreakableWall(x,y, ResourcesManager.getSprite("health"));
            case "8" -> new BreakableWall(x,y, ResourcesManager.getSprite("speed"));
            default -> throw new UnsupportedOperationException();
        };
    }*/


    public abstract void drawImage(Graphics g);
    public abstract void drawHitbox(Graphics g);
    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public abstract void update();

}
