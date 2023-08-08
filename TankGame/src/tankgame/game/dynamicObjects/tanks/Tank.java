package tankgame.game.dynamicObjects.tanks;

import tankgame.game.GameState;
import tankgame.util.Animation;
import tankgame.util.Sound;
import tankgame.constants.GameScreenConstants;
import tankgame.constants.ResourceConstants;
import tankgame.util.ResourceManager;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.GameWorld;
import tankgame.game.immobileObjects.powerups.PowerUp;
import tankgame.game.dynamicObjects.projectiles.Bullet;
import tankgame.game.dynamicObjects.DynamicObject;
import tankgame.game.dynamicObjects.projectiles.Projectile;
import tankgame.game.immobileObjects.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Tank extends DynamicObject {
    long timeSinceLastShot = 0L;
    long coolDown = 3500;
    Bullet currentChargeBullet=null;
    private float R = 2;
    private final float ROTATIONSPEED = 1f;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private int tick = 100;
    private int ticksTillNextShot = 100;
    private final int maxHealthPoints = 100;
    private int currentHealthPoints = 100;
    private int damage = 10;
    private int lives = 0;
    private boolean isLoser = false;
    private int playerID;
    private String name;
    private final GameWorld gw;
    private ArrayList<int[]> validSpawnLocations;
    private Animation animation;
    private String animationType;
    private BufferedImage bulletImage;
    private Sound bulletSpawnSound;
    private Sound bulletCollideSound;
    private boolean weaponPressed;
    private ArrayList<Integer> weaponPool;
    private int currentWeapon;
    private int numOfWeapons = 2;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img, int playerID, String name, GameWorld gw) {
        super(x, y,  vx, vy, angle,2, img);
        this.playerID = playerID;
        this.name = name;
        this.gw = gw;
        this.animation = this.initAnimation("WALK");
        this.animation.start();
        this.initBullet();
        currentWeapon = 1;
    }

    public void setName(String name){
        this.name = name;
    }
    private Animation initAnimation(String animationType) {

            if(animationType.equals("WALK")){
                if(currentWeapon == 0){
                    return new Animation(this.x, this.y, ResourceManager.getAnimation("WALK_HANDGUN"));
                } else if (currentWeapon == 1) {
                    return new Animation(this.x, this.y, ResourceManager.getAnimation("WALK_RIFLE"));
                }
            } else if (animationType.equals("SHOOT")) {
                if(currentWeapon == 0){
                    return new Animation(this.x, this.y, ResourceManager.getAnimation("SHOOT_HANDGUN"));
                } else if (currentWeapon == 1) {
                    return new Animation(this.x, this.y, ResourceManager.getAnimation("SHOOT_RIFLE"));
                }
            }else if(animationType.equals("RELOAD")){
                if(currentWeapon == 0){
                    return new Animation(this.x, this.y, ResourceManager.getAnimation("RELOAD_HANDGUN"));
                } else if (currentWeapon == 1) {
                    return new Animation(this.x, this.y, ResourceManager.getAnimation("RELOAD_HANDGUN"));
                }
            }

        if(animationType.equals("WALK_HANDGUN")){
                return new Animation(this.x, this.y, ResourceManager.getAnimation("WALK_RIFLE"));

            } else if (animationType.equals("SHOOT_HANDGUN")) {
                return new Animation(this.x, this.y, ResourceManager.getAnimation("SHOOT_RIFLE"));
            } else if (animationType.equals("RELOAD_HANDGUN")) {
                return new Animation(this.x, this.y, ResourceManager.getAnimation("RELOAD_HANDGUN"));
            }

        return new Animation(this.x, this.y, ResourceManager.getAnimation("WALK_HANDGUN"));


    }

    public void setCoolDown(int coolDown){
        this.coolDown=coolDown;
    }
    private void initBullet() {
        switch(this.playerID) {
            case 1 -> {
                this.bulletImage = ResourceManager.getImage(ResourceConstants.IMAGES_BULLET_TRAINER);
                this.bulletSpawnSound = new Sound(ResourceManager.getSound(ResourceConstants.SOUND_BULLET_PLAYER1));
                this.bulletCollideSound = new Sound(ResourceManager.getSound(ResourceConstants.SOUND_BULLET_COLLIDE_PLAYER1));
            }
            case 2 -> {
                this.bulletImage = ResourceManager.getImage(ResourceConstants.IMAGES_BULLET_POKEMON);
                this.bulletSpawnSound = new Sound(ResourceManager.getSound(ResourceConstants.SOUND_BULLET_PLAYER2));
                this.bulletCollideSound = new Sound(ResourceManager.getSound(ResourceConstants.SOUND_BULLET_COLLIDE_PLAYER2));
            }
        }
    }

    public Sound getBulletCollideSound() {
        return this.bulletCollideSound;
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this. y = y;}

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitBox.setLocation((int) x, (int) y);
    }

    public void setValidSpawnLocations(ArrayList<int[]> validSpawnLocations) {
        this.validSpawnLocations = validSpawnLocations;
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

    public void toggleShootPressed() {
        if((this.timeSinceLastShot + this.coolDown) < System.currentTimeMillis()){
            this.animation = this.initAnimation("RELOAD");
            this.animation.start();
        }else {
            this.animation = this.initAnimation("SHOOT");
            this.animation.start();
        }
        this.shootPressed = true;
    }
    public void toggleWeaponPressed(){
        this.weaponPressed = true;
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

    public void unToggleShootPressed() {
        this.shootPressed = false;
        this.animation = this.initAnimation("WALK");
        this.animation.start();

    }

    void unToggleWeaponPressed(){
        this.weaponPressed = false;
    }

    @Override
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

        if(this.shootPressed && tick >= ticksTillNextShot && (this.timeSinceLastShot + this.coolDown) < System.currentTimeMillis()) {
            this.timeSinceLastShot = System.currentTimeMillis();
            this.shoot();
            tick = 0;
        }
        
        if(this.weaponPressed){
            this.changeWeapon();
        }

        this.animation.setX(this.x);
        this.animation.setY(this.y);

        tick++;
        checkOneShotOneKillMode();
        checkAlive();
        checkBorder();
    }

    private void changeWeapon() {

        if(this.currentWeapon > this.numOfWeapons){
            currentWeapon = 0;
            this.animation = this.initAnimation("WALK");
            this.animation.start();
        }else{
            currentWeapon++;
            this.animation = this.initAnimation("WALK");
            this.animation.start();
        }
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
        checkBorder();
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void shoot() {
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
        gw.addToMovableGameObjectCollections(bullet);
        this.bulletSpawnSound.playSound();

    }

    private void checkAlive() {
        if(this.currentHealthPoints <= 0 && this.lives > 0) {
            this.lives -= 1;
            this.currentHealthPoints = this.maxHealthPoints;
            this.randomizeSpawnLocation();
        } else if(this.currentHealthPoints <= 0) {
            this.isLoser = true;
        }
    }

    private void randomizeSpawnLocation() {
        int maxChoices = this.validSpawnLocations.size();
        int randomSelection = (new Random()).nextInt(maxChoices);
        int[] location = this.validSpawnLocations.get(randomSelection);

        this.setPosition(
                location[1] * ResourceConstants.FLOOR_TILE_DIMENSION,
                location[0] * ResourceConstants.FLOOR_TILE_DIMENSION
        );
    }

    public boolean getIsLoser() {
        return this.isLoser;
    }

    public void takeDamage() {
        getBulletCollideSound().playSound();
        this.currentHealthPoints -= this.damage;
    }

    public void heal(int amount) {
        this.currentHealthPoints += amount;
        this.currentHealthPoints = Math.min(currentHealthPoints, maxHealthPoints);
    }

    public void changeSpeed(float speed) {
        this.R = speed;
        this.animation.setDelay((int) (this.animation.getInitialDelay() / (this.R / 2)));
    }

    public void changeDelayBetweenShots(int delay) {
        this.ticksTillNextShot = delay;
    }

    private void checkBorder() {
        int limitX = ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth();
        int limitY = ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight();
        if (x < limitX) {
            x = limitX;
        }
        if (x >= GameScreenConstants.WORLD_WIDTH - limitX * 2.1) {
            x = (float) (GameScreenConstants.WORLD_WIDTH - limitX * 2.1);
        }
        if (y < limitY) {
            y = limitY;
        }
        if (y >= GameScreenConstants.WORLD_HEIGHT - limitY * 2) {
            y = GameScreenConstants.WORLD_HEIGHT - limitY * 2;
        }
    }

    public int getCurrentHealthPoints() {
        return this.currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    public int getLives() {
        return this.lives;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof PowerUp) {
            ((PowerUp) obj).empower(this);
            ((PowerUp) obj).setDestroyed(true);
            ((PowerUp) obj).playSound();
        } else if(obj instanceof Projectile) {
            if(((Projectile) obj).getOwnership() != this) {
                ((Projectile) obj).setDestroyed(true);
                ((Projectile) obj).playSound();
                this.takeDamage();
            }
        } else if(obj instanceof Wall || obj instanceof Tank) {
            Rectangle intersection = this.hitBox.intersection(obj.getHitBox());
            if(intersection.height > intersection.width && this.x < intersection.x) { // left detection
                this.setPosition(this.x - (intersection.width / 2f), this.y);
            } else if(intersection.height > intersection.width && this.x > ((GameObject) obj).getX()) { // right detection
                this.setPosition(this.x + (intersection.width / 2f), this.y);
            } else if(intersection.height < intersection.width && this.y < intersection.y) { // top detection
                this.setPosition(this.x, this.y - (intersection.height / 2f));
            } else if(intersection.height < intersection.width && this.y > ((GameObject) obj).getY()) { // bottom detection
                this.setPosition(this.x, this.y + (intersection.height / 2f));
            }
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }


    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.drawImage(this.img, rotation, null);
        this.animation.drawImage(g2d,angle);
        //super.drawHitbox(g);

        g2d.setColor(Color.GREEN);
        g2d.drawRoundRect((int)x, (int)y-20,100,15,20,20);
        long currentWidth = 100 - ((this.timeSinceLastShot+this.coolDown)-System.currentTimeMillis())/40;
        if(currentWidth>100){
            currentWidth=100;
        }
        g2d.fillRoundRect((int)x,(int)y-20,(int)currentWidth,15,20,20);
    }

    private void checkOneShotOneKillMode() {
        if(GameState.oneShotOneKillState == GameState.OneShotOneKillState.ON) {
            this.damage = 100;
            this.R = 8;
        } else if(GameState.oneShotOneKillState == GameState.OneShotOneKillState.OFF) {
            this.damage = 10;
            this.R = 4;
        }
    }

}
