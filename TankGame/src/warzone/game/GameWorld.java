package warzone.game;


import warzone.constants.GameConstants;
import warzone.Launcher;
import warzone.constants.ResourcesConstants;
import warzone.display.Camera;
import warzone.display.GameHUD;
import warzone.display.Minimap;
import warzone.game.moveableObjects.MoveableObject;
import warzone.game.moveableObjects.projectiles.Bullet;
import warzone.game.moveableObjects.projectiles.Projectile;
import warzone.game.moveableObjects.tanks.Tank;
import warzone.game.moveableObjects.tanks.TankControl;
import warzone.game.stationaryObjects.StationaryObject;
import warzone.game.stationaryObjects.powerups.Heal;
import warzone.game.stationaryObjects.powerups.FastBulletLoading;
import warzone.game.stationaryObjects.powerups.Speed;
import warzone.game.stationaryObjects.walls.BreakableWall;
import warzone.game.stationaryObjects.walls.UnbreakableWall;
import warzone.loaders.BackgroundLoader;
import warzone.loaders.GameMapLoader;
import warzone.loaders.ResourcesManager;
import warzone.util.CheatController;
import warzone.util.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;// entire world image

    private Minimap minimap;
    private Camera camera1;
    private Camera camera2;
    private GameHUD gameHUD1;
    private GameHUD gameHUD2;
    private Tank t1;
    private Tank t2;

    private TankControl tc1;
    private TankControl tc2;
    private CheatController cheatController;
    private final Launcher lf; // go back to the screens I need (pause function)?
    private long tick = 0;
    private GameObjectCollections<MoveableObject> moveableObjectGameObjectCollections;
    private GameObjectCollections<StationaryObject> stationaryObjectGameObjectCollections;
    private GameObjectCollections<GameObject> collisionLessGameObjectCollections;
    List<GameObject> gobjs = new ArrayList<>(800);
    private ArrayList<int[]> emptySpaces;
    private GameState.RunningState runningState;
    private String gameMap;


    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
        ResourcesManager.loadResources();
        System.out.println("Resources Loaded Successfully");
    }

    @Override
    public void run() {
        try {
            if (!this.runningState.getState()) {
                System.out.println("Reset Game");
                this.resetGame();
            }

            Sound music = new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_BACKGROUND_MUSIC));
            Thread musicThread = new Thread(music);
            musicThread.start();

            while(true){
                this.tick++;
                this.moveableObjectGameObjectCollections.update(); //make update to tank and bullets
                checkCollisions();
                deleteGarbage();
                this.repaint();

                Thread.sleep(1000/144); //To loop at a fixed rate per/sec

                if(this.t2.getIsLoser()){
                    GameState.PLAYER_WINNER = 1;
                    this.runningState = this.runningState.nextState();
                    music.stopSound();
                    musicThread.interrupt();
                    this.lf.setFrame("end");
                    return;
                } else if (this.t1.getIsLoser()) {
                    GameState.PLAYER_WINNER = 2;
                    this.runningState = this.runningState.nextState();
                    music.stopSound();
                    musicThread.interrupt();
                    this.lf.setFrame("end");
                }
            }


        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }
    public void resetGame(){
        this.tick = 0;
        this.runningState = GameState.RunningState.RUNNING;
        GameState.hitboxState = GameState.hitboxState.OFF;
        this.moveableObjectGameObjectCollections.clear();
        this.stationaryObjectGameObjectCollections.clear();
        this.collisionLessGameObjectCollections.clear();
        this.emptySpaces.clear();
        this.loadMap();
        this.emptySpaces = GameMapLoader.getInstance().getEmptySpaces();
        BackgroundLoader.getInstance().initializeBackground();
        this.initTanks();
        this.t1.setValidSpawnLocation(this.emptySpaces);
        this.t2.setValidSpawnLocation(this.emptySpaces);
        this.initHUD();
        this.initControllers();
        
    }

    public void InitializeGame(){
        this.runningState = GameState.RunningState.RUNNING;
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,GameConstants.GAME_WORLD_HEIGHT,BufferedImage.TYPE_INT_RGB);
        this.moveableObjectGameObjectCollections = new GameObjectCollections<>();
        this.stationaryObjectGameObjectCollections = new GameObjectCollections<>();
        this.collisionLessGameObjectCollections = new GameObjectCollections<>();
        this.emptySpaces = new ArrayList<>();
        loadMap();
        this.emptySpaces = GameMapLoader.getInstance().getEmptySpaces();
        BackgroundLoader.getInstance().initializeBackground();
        this.initTanks();
        this.t1.setValidSpawnLocation(this.emptySpaces);
        this.t2.setValidSpawnLocation(this.emptySpaces);
        this.initHUD();
        this.initControllers();
        cheatController = new CheatController();
        this.lf.getJf().addKeyListener(cheatController);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        BackgroundLoader.getInstance().drawImage(buffer);

        g2.setColor(Color.black);
        g2.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

        this.moveableObjectGameObjectCollections.draw(buffer);
        this.stationaryObjectGameObjectCollections.draw(buffer);
        this.collisionLessGameObjectCollections.draw(buffer);

        camera1.drawSplitScreen(world);
        camera2.drawSplitScreen(world);
        BufferedImage leftScreen = camera1.getSplitScreen();
        BufferedImage rightScreen = camera2.getSplitScreen();
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, (GameConstants.GAME_SCREEN_WIDTH / 2) + 4, 0, null);

        gameHUD1.drawHUD(g2);
        gameHUD2.drawHUD(g2);
        minimap.drawMinimap(world, g2);

    }

    private void initTanks() {
        if(((Tank) moveableObjectGameObjectCollections.get(0)).getPlayerId() == 1) {
            this.t1 = (Tank) moveableObjectGameObjectCollections.get(0);
            this.t2 = (Tank) moveableObjectGameObjectCollections.get(1);
        } else {
            this.t1 = (Tank) moveableObjectGameObjectCollections.get(1);
            this.t2 = (Tank) moveableObjectGameObjectCollections.get(0);
        }
    }

    private void initControllers() {
        this.tc1 = new TankControl(
                this.t1,
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE,
                KeyEvent.VK_1

        );

        this.tc2 = new TankControl(
                this.t1,
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER,
                KeyEvent.VK_SHIFT

        );
    }

    private void initHUD() {
        this.minimap = new Minimap();
        this.camera1 = new Camera(t1, (minimap.getScaledHeight() / 2) + 12);
        this.camera2 = new Camera(t2, (minimap.getScaledHeight() / 2) + 12);
        this.gameHUD1 = new GameHUD(
                this.t1,
                0,
                (GameConstants.GAME_SCREEN_HEIGHT - ((this.minimap.getScaledHeight() / 2 + 12))),
                this.minimap.getScaledWidth(),
                ResourcesManager.getSprite(ResourcesConstants.IMAGES_HUD_1)
        );

        this.gameHUD2 = new GameHUD(
                this.t2,
                (GameConstants.GAME_SCREEN_WIDTH - this.minimap.getScaledWidth()),
                (GameConstants.GAME_SCREEN_HEIGHT - ((this.minimap.getScaledHeight() / 2 + 12))),
                this.minimap.getScaledWidth(),
                ResourcesManager.getSprite(ResourcesConstants.IMAGES_HUD_2)
        );
    }

    private void loadMap() {
        //choose map function...
        GameMapLoader.getInstance().initializeMap(this, ResourcesManager.getGameMap(this.gameMap));
    }

    private void deleteGarbage() {
        for(int i = 0; i < this.moveableObjectGameObjectCollections.size(); i++) {
            MoveableObject moveableObject = this.moveableObjectGameObjectCollections.get(i);
            //Delete used bullets
            if(moveableObject instanceof Projectile && ((Projectile) moveableObject).getIsDestroyed()) {
                this.moveableObjectGameObjectCollections.remove(moveableObject);
            }
        }

        for(int i = 0; i < this.stationaryObjectGameObjectCollections.size(); i++) {
            StationaryObject stationaryObject = this.stationaryObjectGameObjectCollections.get(i);
            //Delete breakable walls if destroy it
            if(stationaryObject.getIsDestroyed()) {
                this.stationaryObjectGameObjectCollections.remove(stationaryObject);
            }
        }
    }

    private void checkCollisions() {

        for(int i = 0; i < this.moveableObjectGameObjectCollections.size(); i++) {
            MoveableObject moveableObject = this.moveableObjectGameObjectCollections.get(i);
            //check collision between stationary objects
            for(int j = 0; j < this.stationaryObjectGameObjectCollections.size(); j++) {
                StationaryObject stationaryObject = this.stationaryObjectGameObjectCollections.get(j);
                if(moveableObject.getHitBox().intersects(stationaryObject.getHitBox())) {
                    moveableObject.handleCollision(stationaryObject);
                }
            }
            //check collision between movable objects
            for(int k = 0; k < this.moveableObjectGameObjectCollections.size(); k++) {
                MoveableObject otherMoveableObject = this.moveableObjectGameObjectCollections.get(k);
                if(moveableObject.getHitBox().intersects(otherMoveableObject.getHitBox())) {
                    moveableObject.handleCollision(otherMoveableObject);
                }
            }
        }
    }



    public void addToCollisionlessGameObjectCollections(GameObject gameObject) {
        this.collisionLessGameObjectCollections.add(gameObject);
    }

    public void addToStationaryGameObjectCollections(StationaryObject stationaryObject) {
        this.stationaryObjectGameObjectCollections.add(stationaryObject);
    }

    public void addToMovableGameObjectCollections(MoveableObject  moveableObject) {
        this.moveableObjectGameObjectCollections.add(moveableObject);
    }
    public void selectMap(String map) {
        this.gameMap = map;
        this.resetGame();
    }
}