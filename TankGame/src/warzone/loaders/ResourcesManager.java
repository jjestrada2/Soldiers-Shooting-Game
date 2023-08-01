package warzone.loaders;

import warzone.Resources.ResourcesPool;
import warzone.constants.ResourcesConstants;
import warzone.game.Bullet;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourcesManager {
    private  final static Map<String, BufferedImage> sprites =new HashMap<>();
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<> ();
    private static final ArrayList<String> gameMaps = new ArrayList<>();








    public static BufferedImage getSprite(String type) {
        if(!ResourcesManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourcesManager.sprites.get(type);
    }

    public static Clip getSound(String key){
        return ResourcesManager.sounds.get(key);
    }

    public static List<BufferedImage> getAnimation(String key){
        return ResourcesManager.animation.get(key);
    }
    public static String getGameMap(String key){
        if(gameMaps.contains(key)) {
            return gameMaps.get(gameMaps.indexOf(key));
        }
        return null;
    }

    public static String getGameMap(int index) {
        return gameMaps.get(index);
    }

    public static int getNumberOfMaps() {
        return gameMaps.size();
    }
    private static void initSprites(){
        try {
            ResourcesManager.sprites.put("tank1", loadSprite("tank/tank1.png"));
            ResourcesManager.sprites.put("tank2", loadSprite("tank/tank2.png"));
            ResourcesManager.sprites.put("menu", loadSprite("menu/title1.png"));
            ResourcesManager.sprites.put("splash",loadSprite("menu/title.png"));
            ResourcesManager.sprites.put("controls",loadSprite("menu/controls.png"));
            ResourcesManager.sprites.put("floor",loadSprite("floor/bg.jpg"));
            ResourcesManager.sprites.put("health",loadSprite("powerups/health.png"));
            ResourcesManager.sprites.put("shield",loadSprite("powerups/shield.png"));
            ResourcesManager.sprites.put("speed",loadSprite("powerups/speed.png"));
            ResourcesManager.sprites.put("unbreak",loadSprite("walls/unbreak.png"));
            ResourcesManager.sprites.put("break1",loadSprite("walls/break1.png"));
            ResourcesManager.sprites.put("break2",loadSprite("walls/break2.png"));
            ResourcesManager.sprites.put("break3",loadSprite("walls/break3.png"));
            ResourcesManager.sprites.put("break4",loadSprite("walls/break4.png"));
            ResourcesManager.sprites.put("bullet",loadSprite("bullet/bullet.jpg"));

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static void initSounds(){
        try {
            AudioInputStream as;
            Clip clip;

            //----------------------------------------------------------------------------
            as = readAudio("sounds/" + ResourcesConstants.SOUND_BULLET_HANDGUN);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_BULLET_HANDGUN, clip);
            //----------------------------------------------------------------------------

            as = readAudio("sounds/" + ResourcesConstants.SOUND_BULLET_RIFLE);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_BULLET_RIFLE, clip);
            //----------------------------------------------------------------------------
            as = readAudio("sounds/" + ResourcesConstants.SOUND_BULLET_KNIFE);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_BULLET_KNIFE, clip);
            //----------------------------------------------------------------------------

            as = readAudio("sounds/" + ResourcesConstants.SOUND_BULLET_SHOTGUN);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_BULLET_SHOTGUN, clip);
            //----------------------------------------------------------------------------
           /* as = readAudio("sounds/" + ResourcesConstants.SOUND_HIT);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_HIT, clip);
            //----------------------------------------------------------------------------
            as = readAudio("sounds/" + ResourcesConstants.SOUND_DIE);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_DIE, clip);*/
            //----------------------------------------------------------------------------
            as = readAudio("sounds/" + ResourcesConstants.SOUND_PICKUP);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_PICKUP, clip);
            //----------------------------------------------------------------------------
            as = readAudio("music/" + ResourcesConstants.SOUND_BACKGROUND_MUSIC);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_BACKGROUND_MUSIC, clip);
            //----------------------------------------------------------------------------
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(-2);
        }

    }

    private static void initAnimations(){
        try{

           //----------------------------------------------------------bullet----------------------------------------
            String basename = "expl_08_000%d";
            ResourcesManager.animation.put(
                    "explosion",readAnimation(31,basename,"animations/bullet/",".png")
            );

            //----------------------------------------------------------handgun-----------------------------------------

            basename = "survivor-idle_handgun_%d";
            ResourcesManager.animation.put(
                    "idelHandGun",readAnimation(19,basename,"animations/handgun/idle/",".png")
            );
            //---------------------------------------------------------------------------------------------------
            basename = "survivor-meleeattack_handgun_%d";
            ResourcesManager.animation.put(
                    "meleeHandGun",readAnimation(14,basename,"animations/handgun/meleeattack/",".png")
            );
            //---------------------------------------------------------------------------------------------------

            basename = "survivor-move_handgun_%d";
            ResourcesManager.animation.put(
                    "moveHandGun",readAnimation(19,basename,"animations/handgun/move/",".png")
            );
            //---------------------------------------------------------------------------------------------------
            basename = "survivor-reload_handgun_%d";
            ResourcesManager.animation.put(
                    "reloadHandGun",readAnimation(14,basename,"animations/handgun/reload/",".png")
            );
            //---------------------------------------------------------------------------------------------------
            basename = "survivor-shoot_handgun_%d";
            ResourcesManager.animation.put(
                    "shootHandGun",readAnimation(2,basename,"animations/handgun/shoot/",".png")
            );
            //---------------------------------------------------------------------------------------------------



        }catch(IOException e){
            System.out.println(e);
            System.exit(-2);
        }
    }

    private static void initMaps(){
        ResourcesManager.gameMaps.add(ResourcesConstants.MAP_ONE);
        ResourcesManager.gameMaps.add(ResourcesConstants.MAP_TWO);
        ResourcesManager.gameMaps.add(ResourcesConstants.MAP_THREE);
    }


    //read Helper functions

    private static BufferedImage loadSprite(String path) throws IOException {

        return ImageIO.read(
                Objects.requireNonNull(ResourcesManager
                        .class
                        .getClassLoader()
                        .getResource(path)));

    }
    private static AudioInputStream readAudio(String resource) throws UnsupportedAudioFileException,IOException {
        return AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourcesManager.class.getClassLoader().getResource(resource),
                String.format("Could not find %s",resource)));
    }

    private static ArrayList<BufferedImage> readAnimation(int length,String basename,String path,String extension) throws IOException{
        ArrayList<BufferedImage> result = new ArrayList<>();
        for(int i = 0;i <=length;i++){
            String fname = String.format(basename,i);
            String fullPath = path + fname +extension;
            result.add(loadSprite(fullPath));
        }
        return result;
    }

    //Load all my files
    public static void loadResources(){
        ResourcesManager.initSprites();
        ResourcesManager.initSounds();
        ResourcesManager.initAnimations();
    }

    public static void main(String[] args){
        ResourcesPool<Bullet> bPool = new ResourcesPool<>("bullet", 300);
        bPool.fillPool(Bullet.class,300);
        ResourcesManager.loadResources();

    }
}
