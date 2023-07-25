package tankrotationexample.game;

import tankrotationexample.Resources.ResourcesManager;

import java.awt.image.BufferedImage;

public class GameObject {

    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException{
        return switch (type){
            case "9","3" -> new Wall(x,y,ResourcesManager.getSprite("unbreak"));
            case "2" -> new BreakableWall(x,y, ResourcesManager.getSprite(("break1")));
            case "4" -> new Health(x,y,ResourcesManager.getSprite("health"));
            case "5" -> new Speed(x,y,ResourcesManager.getSprite("speed"));
            case "6" -> new Shield(x,y,ResourcesManager.getSprite("shield"));
            default -> throw new UnsupportedOperationException();
        };
    }
}