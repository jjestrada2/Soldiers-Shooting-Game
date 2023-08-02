package warzone.game.moveableObjects.tanks;

import warzone.constants.GameConstants;
import warzone.constants.ResourcesConstants;
import warzone.game.*;
import warzone.game.moveableObjects.MoveableObject;
import warzone.game.moveableObjects.projectiles.Bullet;
import warzone.game.moveableObjects.projectiles.Projectile;
import warzone.game.stationaryObjects.powerups.PowerUp;
import warzone.game.stationaryObjects.walls.UnbreakableWall;
import warzone.game.stationaryObjects.walls.Wall;
import warzone.loaders.ResourcesManager;
import warzone.util.Animation;
import warzone.util.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author
 */
public class Tank extends MoveableObject {

    private float x;
    private float y;
    private float screen_x, screen_y;
    private float vx; //speed of x
    private float vy; //speed of Y
    private float angle;
    //static ResourcesPool<Bullet> bPool;
    List<Bullet> ammo = new ArrayList<>();
    private float R = 5; //speed factor how fast go my tank
    private float ROTATIONSPEED = 3.0f;

    long timeSinceLastShot = 0L;
    long coolDown = 4000;
    Bullet currentChargeBullet=null;

   /* static {
        bPool = new ResourcesPool<>("bullet", 300);
        bPool.fillPool(Bullet.class,300);
    }*/

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private Rectangle hitBox;
    private boolean changeWeapon;
    private String name;
    private GameWorld gameWorld;
    private int playerId;
    private Animation animation;
    private int weapon;
    private BufferedImage bulletImage;
    private Sound bulletSpawnSound;
    private Sound bulletCollideSound;
    private ArrayList<int[]> validSpawnLocation;
    private int tick = 100;
    private int ticksTillNextShot = 100;
    private final int maxHealthPoints = 100;
    private int currentHealthPoints = 100;
    private int damage = 10;
    private int lives = 4;
    private boolean isLoser = false;


    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img, int playerId, String name, GameWorld gameWorld) {
       super(x,y,vx,vy,angle,2,img);
       this.playerId = playerId;
       this.name = name;
       this.gameWorld = gameWorld;
       this.weapon = 0;
       this.animation = this.initAnimation();
       this.animation.start();
       this.initBullet();




    }

    private void initBullet() {
        this.bulletImage = ResourcesManager.getSprite(ResourcesConstants.BULLET);
        switch (this.weapon){

            case 0->{
                this.bulletSpawnSound = new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_BULLET_HANDGUN));
            }
            case 1->{
                this.bulletSpawnSound = new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_BULLET_SHOTGUN));
            }
            case 2 ->{
                this.bulletSpawnSound = new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_BULLET_RIFLE));
            }
            case 3 ->{
                this.bulletSpawnSound = new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_BULLET_KNIFE));
            }
            default->{
                this.bulletSpawnSound = new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_HIT));
            }
        }
    }

    public Sound getBulletSpawnSound(){
        return this.bulletSpawnSound;
    }
    private Animation initAnimation() {
        return new Animation(this.x,this.y,ResourcesManager.getAnimation("moveHandGun"));
    }
    @Override
    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof PowerUp){
            //applypowerup
        }else if(obj instanceof Projectile){
            if(((Projectile)obj).getOwnership()!= this){
                ((Projectile)obj).setDestroyed(true);
                ((Projectile)obj).playSound();
                this.takeDamage();
            }
        } else if (obj instanceof Wall || obj instanceof Tank) {
            Rectangle intersection = this.hitBox.intersection(obj.getHitBox());
            if(intersection.height>intersection.width && this.x < intersection.x){//left detect
                this.setPosition(this.x-(intersection.width/2f),this.y);
            }else if(intersection.height > intersection.width && this.x > ((GameObject)obj).getX()){//right detect
                this.setPosition(this.x+(intersection.width/2f),this.y);
            } else if (intersection.height<intersection.width && this.y< intersection.y) {//top detect
                this.setPosition(this.x,this.y-(intersection.height/2f));
            }else if(intersection.height < intersection.width && this.y > ((GameObject) obj).getY()) { // bottom detection
                this.setPosition(this.x, this.y + (intersection.height / 2f));
            }
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    public float getScreen_x() {
        return screen_x;
    }

    public float getScreen_y() {
        return screen_y;
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this. y = y;}
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        this.hitBox.setLocation((int) x,(int)y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setValidSpawnLocation(ArrayList<int[]> validSpawnLocation){
        this.validSpawnLocation = validSpawnLocation;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.shootPressed && tick >= ticksTillNextShot){
            this.shoot();
            tick = 0;
            /*
            this.timeSinceLastShot = System.currentTimeMillis();
            if(currentChargeBullet == null){
                this.currentChargeBullet = new Bullet(this.safeShootX(),this.safeShootY(),ResourcesManager.getSprite("bullet"),angle);
            }else{

                this.currentChargeBullet.setHeading(x,y,angle);
            }*/

        }else{
            if(this.currentChargeBullet != null){
                this.ammo.add(currentChargeBullet);
                this.timeSinceLastShot = System.currentTimeMillis();
                this.currentChargeBullet=null;
            }
            this.animation.setX(this.x);
            this.animation.setY(this.y);
          
            tick++;
            checkAlive();
            checkBorder();

        }

    }

    

    private void shoot(){
        Bullet bullet = new Bullet(
                (float)(this.x + (Math.cos(Math.toRadians(angle))) + (this.img.getWidth() / 2.0)),
                (float)(this.y + (Math.sin(Math.toRadians(angle))) + (this.img.getHeight() / 2.0)),
                0,
                0,
                this.angle,
                4,
                this.bulletImage,
                this

        );
        gameWorld.addToMovableGameObjectCollections(bullet);
        this.bulletSpawnSound.playSound();
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();//keep tank on the screen
       centerScreen();
    }



    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        centerScreen();
    }

    private void checkAlive(){
        if(this.currentHealthPoints<=0 && this.lives > 0){
            this.lives -=1;
            this.currentHealthPoints = this.maxHealthPoints;
            this.randomizeSpawnLocation();
        }else if(this.currentHealthPoints <= 0){
            this.isLoser =true;
        }
    }

    private void randomizeSpawnLocation() {
        int maxChoices = this.validSpawnLocation.size();
        int randomSelection = (new Random()).nextInt(maxChoices);
        int [] location = this.validSpawnLocation.get(randomSelection);
        this.setPosition(
                location[1]*ResourcesConstants.FLOOR_TILE_DIMENSION_W,
                location[2]*ResourcesConstants.FLOOR_TILE_DIMENSION_H
        );
    }

    public boolean getIsLoser(){
        return this.isLoser;
    }
    public void takeDamage(){
        this.currentHealthPoints -= this.damage;
    }
    public void changeSpeed(float speed){
        this.R = speed;
        this.animation.setDelay((int)(this.animation.getInitialDelay()/(this.R/2)));
    }

    public void changeDelayBetweenShots(int delay){
        this.ticksTillNextShot = delay;
    }
    private void centerScreen() {
        this.screen_x = this.x - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.screen_y = this.y - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        if(screen_x < 0){
            this.screen_x = 0;
        }
        if(screen_y < 0){
            this.screen_y = 0;
        }
        if(screen_x > GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2){
            this.screen_x = GameConstants.GAME_WORLD_WIDTH - (GameConstants.GAME_SCREEN_WIDTH/2);
        }
        if(screen_y >= GameConstants.GAME_WORLD_HEIGHT - (GameConstants.GAME_SCREEN_HEIGHT*2/3)){
            screen_y = GameConstants.GAME_WORLD_HEIGHT - (GameConstants.GAME_SCREEN_HEIGHT*2/3);
        }
    }
    private void checkBorder() {
       int limitX = ResourcesManager.getSprite(ResourcesConstants.UNBREAK).getWidth();
       int limitY = ResourcesManager.getSprite(ResourcesConstants.UNBREAK).getHeight();
        if (x < limitX) {
            x = limitX;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - limitX * 2.1) {
            x = (float) (GameConstants.GAME_WORLD_WIDTH - limitX * 2.1);
        }
        if (y < limitY) {
            y = limitY;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - limitY * 2) {
            y = GameConstants.GAME_SCREEN_HEIGHT - limitY * 2;
        }
    }

    public int getCurrentHealthPoints(){
        return getCurrentHealthPoints();
    }
    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    public int getLives() {
        return this.lives;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        this.animation.drawImage(g2d);
        //g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        //g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

        /*shoot bars
        g2d.setColor(Color.GREEN);
        g2d.drawRoundRect((int)x, (int)y-20,100,15,20,20);
        long currentWidth = 100 - ((this.timeSinceLastShot+this.coolDown)-System.currentTimeMillis())/40;
        if(currentWidth>100){
            currentWidth=100;
        }
        g2d.fillRoundRect((int)x,(int)y-20,(int)currentWidth,15,20,20);
        this.ammo.forEach(bullet -> bullet.drawImage(g2d));

        if(this.currentChargeBullet!=null){
            this.currentChargeBullet.drawImage(g2d);
        }
        */

    }


    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }

    public void toggleChangeWeaponPressed() {
    }

    public void unToggleChangeWeaponPressed() {
    }

    public void heal(int i) {
        this.currentHealthPoints +=i;
    }
}
