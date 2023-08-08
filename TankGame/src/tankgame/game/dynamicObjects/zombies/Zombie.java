package tankgame.game.dynamicObjects.zombies;

import tankgame.game.GameState;
import tankgame.game.dynamicObjects.tanks.Tank;
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

public class Zombie extends DynamicObject{
    public Zombie(float x, float y, float vx, float vy, float angle, float R, BufferedImage img) {
        super(x, y, vx, vy, angle, R, img);
    }

    @Override
    public void handleCollision(Collidable obj) {

    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void drawImage(Graphics g) {

    }

    @Override
    public void update() {

    }
}

/*

public class Zombie extends DynamicObject {
    private int tick = 100;
    private  Animation animation;
    private int currentHealthPoints;
    private int damage = 30;
    private int lives = 5;
    private int maxHealthPoints = 100;
    private ArrayList<int[]> validSpawnLocations;

    public Zombie(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        super(x, y,  vx, vy, angle,2, img);
        this.animation = this.initAnimation();
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this. y = y;}
    private Animation initAnimation() {
       return new Animation(this.x,this.y,ResourceManager.getAnimation("ZOMBIE"));
    }
    public void setValidSpawnLocations(ArrayList<int[]> validSpawnLocations) {
        this.validSpawnLocations = validSpawnLocations;
    }

    @Override
    public void handleCollision(Collidable obj) {

        if(obj instanceof Projectile) {
                ((Projectile) obj).setDestroyed(true);
                ((Projectile) obj).playSound();
               // this.zombieDamage();

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

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void zombieDamage() {
     //   getBulletCollideSound().playSound();
        this.currentHealthPoints -= this.damage;
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
        this.animation.drawImage(g2d,angle);
    }

    @Override
    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkAlive();
        checkBorder();

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

    private void checkAlive() {
        if(this.currentHealthPoints <= 0 && this.lives > 0) {
            this.lives -= 1;
            this.currentHealthPoints = this.maxHealthPoints;
            this.randomizeSpawnLocation();
        } else if(this.currentHealthPoints <= 0) {
           //
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
}*/