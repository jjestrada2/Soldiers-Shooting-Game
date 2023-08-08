package tankgame.util;

import tankgame.constants.ResourceConstants;
import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ResourceManager {
    private static final Map<String, BufferedImage> images = new HashMap<>();
    private static final Map<String, Clip> sounds = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

    private static final ArrayList<String> gameMaps = new ArrayList<>();

    public static BufferedImage getImage(String key) {
        return ResourceManager.images.get(key);
    }

    public static Clip getSound(String key) {
        return ResourceManager.sounds.get(key);
    }

    public static List<BufferedImage> getAnimation(String key) {
        return ResourceManager.animations.get(key);
    }

    public static String getGameMap(String key) {
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

    public static void initImages() {
        try {
            ResourceManager.images.put(ResourceConstants.IMAGES_BULLET_TRAINER, readImg("bullet/" + ResourceConstants.IMAGES_BULLET_TRAINER));
            ResourceManager.images.put(ResourceConstants.IMAGES_BULLET_POKEMON, readImg("bullet/" + ResourceConstants.IMAGES_BULLET_POKEMON));
            ResourceManager.images.put(ResourceConstants.IMAGES_FLOOR_TILE, readImg("floor/" + ResourceConstants.IMAGES_FLOOR_TILE));
            ResourceManager.images.put(ResourceConstants.IMAGES_UNBREAKABLE_WALL, readImg("walls/" + ResourceConstants.IMAGES_UNBREAKABLE_WALL));
            ResourceManager.images.put(ResourceConstants.IMAGES_BREAKABLE_WALL, readImg("walls/" + ResourceConstants.IMAGES_BREAKABLE_WALL));
            ResourceManager.images.put(ResourceConstants.IMAGES_BORDER_WALL, readImg("walls/" + ResourceConstants.IMAGES_BORDER_WALL));
            ResourceManager.images.put(ResourceConstants.IMAGES_BARRAGE, readImg("powerups/" + ResourceConstants.IMAGES_BARRAGE));
            ResourceManager.images.put(ResourceConstants.IMAGES_HEAL, readImg("powerups/" + ResourceConstants.IMAGES_HEAL));
            ResourceManager.images.put(ResourceConstants.IMAGES_SPEED, readImg("powerups/" + ResourceConstants.IMAGES_SPEED));
            ResourceManager.images.put(ResourceConstants.IMAGES_MENU_TITLE, readImg("menu/" + ResourceConstants.IMAGES_MENU_TITLE));
            ResourceManager.images.put(ResourceConstants.IMAGES_LIVES, readImg("lives/" + ResourceConstants.IMAGES_LIVES));
            ResourceManager.images.put(ResourceConstants.IMAGES_HUD_1, readImg("hud/" + ResourceConstants.IMAGES_HUD_1));
            ResourceManager.images.put(ResourceConstants.IMAGES_HUD_2, readImg("hud/" + ResourceConstants.IMAGES_HUD_2));
            ResourceManager.images.put(ResourceConstants.IMAGES_TANK_ARROW, readImg("tanks/" + ResourceConstants.IMAGES_TANK_ARROW));
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void initSounds() {
        try {
            AudioInputStream as;
            Clip clip;

            as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_PLAYER1);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_BULLET_PLAYER1, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_COLLIDE_PLAYER1);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_BULLET_COLLIDE_PLAYER1, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_PLAYER2);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_BULLET_PLAYER2, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_COLLIDE_PLAYER2);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_BULLET_COLLIDE_PLAYER2, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_BARRAGE);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_BARRAGE, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_HEAL);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_HEAL, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_SPEED);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_SPEED, clip);

            as = readAudio("sounds/" + ResourceConstants.SOUND_ROCK_SMASH);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_ROCK_SMASH, clip);

            as = readAudio("music/" + ResourceConstants.SOUND_MUSIC_BACKGROUND);
            clip = AudioSystem.getClip();
            clip.open(as);
            ResourceManager.sounds.put(ResourceConstants.SOUND_MUSIC_BACKGROUND, clip);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(-2);
        }
    }

    public static void initAnimations() {
        try {
            //-----------HANDGUN----------------------------
            String basename = "survivor-idle_handgun_%d";
            ResourceManager.animations.put(
                    "WALK_HANDGUN",
                    readAnimations(18, "survivor-idle_handgun_%d", "animations/idle/", ".png")
            );

            basename = "survivor-meleeattack_handgun_%d";
            ResourceManager.animations.put(
                    "MELEE_HANDGUN",
                    readAnimations(14, basename, "animations/meleeattack/", ".png")
            );


            basename = "survivor-shoot_handgun_%d";
            ResourceManager.animations.put(
                    "SHOOT_HANDGUN",
                    readAnimations(2, basename, "animations/shoot/", ".png")
            );

            basename = "survivor-reload_handgun_%d";
            ResourceManager.animations.put(
                    "RELOAD_HANDGUN",
                    readAnimations(14, basename, "animations/reload/", ".png")
            );

            //-----------RIFLE----------------------------
            basename = "survivor-move_rifle_%d";
            ResourceManager.animations.put(
                    "WALK_RIFLE",
                    readAnimations(18, basename, "animations/rifle/move/", ".png")
            );


            basename = "survivor-shoot_rifle_%d";
            ResourceManager.animations.put(
                    "SHOOT_RIFLE",
                    readAnimations(2, basename, "animations/rifle/shoot/", ".png")
            );



            /*

            //-----------SHOTGUN----------------------------
            basename = "survivor-move_shotgun_%d";
            ResourceManager.animations.put(
                    "WALK_SHOTGUN",
                    readAnimations(18, basename, "animations/shotgun/move/", ".png")
            );


            basename = "survivor-shoot_shotgun_%d";
            ResourceManager.animations.put(
                    "SHOOT_SHOTGUN",
                    readAnimations(2, basename, "animations/shotgun/shoot/", ".png")
            );
*/
            basename = "skeleton-move_%d";
            ResourceManager.animations.put(
                    "ZOMBIE",
                    readAnimations(16, basename, "animations/zombie/move/", ".png")
            );


        } catch (IOException e) {
            System.out.println(e);
            System.exit(-2);
        }
    }

    public static void initMaps() {
        ResourceManager.gameMaps.add(ResourceConstants.MAP_1_CSV);
        ResourceManager.gameMaps.add(ResourceConstants.MAP_2_CSV);
        ResourceManager.gameMaps.add(ResourceConstants.MAP_3_CSV);
    }

    private static BufferedImage readImg(String resource) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(resource),
                        String.format("Could not find %s", resource))
        );
    }

    private static AudioInputStream readAudio(String resource) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource(resource),
                String.format("Could not find %s", resource)));
    }

    private static ArrayList<BufferedImage> readAnimations(int length, String basename, String path, String extension) throws IOException {
        ArrayList<BufferedImage> result = new ArrayList<>();
        for(int i = 0; i <= length; i++) {
            String fname = String.format(basename, i);
            String fullPath = path + fname + extension;
            BufferedImage img = readImg(fullPath);

            result.add(resize(img,80,80));
        }
        return result;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}