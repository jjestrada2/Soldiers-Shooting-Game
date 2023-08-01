package warzone.game;

import warzone.loaders.ResourcesManager;

import java.awt.*;

public abstract class GameObject {

    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException{
        return switch (type){
            case "9" -> new Wall(x,y,ResourcesManager.getSprite("unbreak"));
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
    }


    public abstract void drawImage(Graphics g);
    public abstract Rectangle getHitBox();

    public abstract void collides(GameObject obj2);
}
