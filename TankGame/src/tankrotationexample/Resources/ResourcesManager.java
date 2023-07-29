package tankrotationexample.Resources;

import tankrotationexample.game.Bullet;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourcesManager {
    private  final static Map<String, BufferedImage> sprites =new HashMap<>();
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<> ();

    private static BufferedImage loadSprite(String path) throws IOException {

        return ImageIO.read(
                Objects.requireNonNull(ResourcesManager
                .class
                .getClassLoader()
                .getResource(path)));

    }
    private static void initSprites(){
        try {
            ResourcesManager.sprites.put("tank1", loadSprite("tank/tank1.png"));
            ResourcesManager.sprites.put("tank2", loadSprite("tank/tank2.png"));
            ResourcesManager.sprites.put("menu", loadSprite("menu/title1.png"));
            ResourcesManager.sprites.put("splash",loadSprite("menu/title.png"));
            ResourcesManager.sprites.put("controls",loadSprite("menu/controls.png"));
            ResourcesManager.sprites.put("floor",loadSprite("floor/bg.bmp"));
            ResourcesManager.sprites.put("health",loadSprite("powerups/health.png"));
            ResourcesManager.sprites.put("shield",loadSprite("powerups/shield.png"));
            ResourcesManager.sprites.put("speed",loadSprite("powerups/speed.png"));
            ResourcesManager.sprites.put("unbreak",loadSprite("walls/unbreak.jpg"));
            ResourcesManager.sprites.put("break1",loadSprite("walls/break1.jpg"));
            ResourcesManager.sprites.put("break2",loadSprite("walls/break2.jpg"));
            ResourcesManager.sprites.put("bullet",loadSprite("bullet/bullet.jpg"));

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    public static BufferedImage getSprite(String type) {
        if(!ResourcesManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourcesManager.sprites.get(type);
    }
    public static void loadResources(){
        ResourcesManager.initSprites();
    }

    public static void main(String[] args){
        ResourcesPool<Bullet> bPool = new ResourcesPool<>("bullet", 300);
        bPool.fillPool(Bullet.class,300);
        ResourcesManager.loadResources();

    }
}
