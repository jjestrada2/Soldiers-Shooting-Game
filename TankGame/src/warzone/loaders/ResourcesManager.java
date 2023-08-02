package warzone.loaders;

import warzone.Resources.ResourcesPool;
import warzone.constants.ResourcesConstants;
import warzone.game.moveableObjects.projectiles.Bullet;

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
            ResourcesManager.sprites.put(ResourcesConstants.TANK1, loadSprite("tank/"+ResourcesConstants.TANK1));
            ResourcesManager.sprites.put(ResourcesConstants.TANK2, loadSprite("tank/"+ResourcesConstants.TANK2));
            ResourcesManager.sprites.put(ResourcesConstants.MENU_TITLE1, loadSprite("menu/"+ResourcesConstants.MENU_TITLE1));
            ResourcesManager.sprites.put(ResourcesConstants.SPLASH,loadSprite("menu/"+ResourcesConstants.SPLASH));
            ResourcesManager.sprites.put(ResourcesConstants.CONTROLS,loadSprite("menu/"+ResourcesConstants.CONTROLS));
            ResourcesManager.sprites.put(ResourcesConstants.FLOOR,loadSprite("floor/"+ResourcesConstants.FLOOR));
            ResourcesManager.sprites.put(ResourcesConstants.HEALTH,loadSprite("powerups/"+ResourcesConstants.HEALTH));
            ResourcesManager.sprites.put(ResourcesConstants.SHIELD,loadSprite("powerups/"+ResourcesConstants.SHIELD));
            ResourcesManager.sprites.put(ResourcesConstants.SPEED,loadSprite("powerups/"+ResourcesConstants.SPEED));
            ResourcesManager.sprites.put(ResourcesConstants.UNBREAK,loadSprite("walls/"+ResourcesConstants.UNBREAK));
            ResourcesManager.sprites.put(ResourcesConstants.BREAK1,loadSprite("walls/"+ResourcesConstants.BREAK1));
            ResourcesManager.sprites.put(ResourcesConstants.BREAK2,loadSprite("walls/"+ResourcesConstants.BREAK2));
            ResourcesManager.sprites.put(ResourcesConstants.BREAK3,loadSprite("walls/"+ResourcesConstants.BREAK3));
            ResourcesManager.sprites.put(ResourcesConstants.BREAK4,loadSprite("walls/"+ResourcesConstants.BREAK4));
            ResourcesManager.sprites.put(ResourcesConstants.BULLET,loadSprite("bullet/"+ResourcesConstants.BULLET));
            ResourcesManager.sprites.put(ResourcesConstants.IMAGES_HUD_1,loadSprite("hud/"+ResourcesConstants.IMAGES_HUD_1));
            ResourcesManager.sprites.put(ResourcesConstants.IMAGES_HUD_2,loadSprite("hud/"+ResourcesConstants.IMAGES_HUD_2));


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
           as = readAudio("sounds/" + ResourcesConstants.SOUND_HIT);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_HIT, clip);
            //----------------------------------------------------------------------------
            as = readAudio("sounds/" + ResourcesConstants.SOUND_DIE);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourcesManager.sounds.put(ResourcesConstants.SOUND_DIE, clip);
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
        ResourcesManager.initMaps();
    }

    public static void main(String[] args){
        ResourcesPool<Bullet> bPool = new ResourcesPool<>("bullet", 300);
        bPool.fillPool(Bullet.class,300);
        ResourcesManager.loadResources();

    }
}
