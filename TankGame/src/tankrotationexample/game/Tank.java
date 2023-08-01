package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourcesManager;
import tankrotationexample.Resources.ResourcesPool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author
 */
public class Tank extends GameObject{

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

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        hitBox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());

    }
    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }
    public float getScreen_x() {
        return screen_x;
    }

    public float getScreen_y() {
        return screen_y;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
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

    void update() {
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
        if(this.shootPressed && (this.timeSinceLastShot + this.coolDown) < System.currentTimeMillis()){
            this.timeSinceLastShot = System.currentTimeMillis();
            if(currentChargeBullet == null){
                this.currentChargeBullet = new Bullet(this.safeShootX(),this.safeShootY(),ResourcesManager.getSprite("bullet"),angle);
            }else{

                this.currentChargeBullet.setHeading(x,y,angle);
            }

        }else{
            if(this.currentChargeBullet != null){
                this.ammo.add(currentChargeBullet);
                this.timeSinceLastShot = System.currentTimeMillis();
                this.currentChargeBullet=null;
            }
        }

        this.ammo.forEach(bullet -> bullet.update());
        this.hitBox.setLocation((int)x,(int)y);

    }

    private float safeShootY() {
        //Needs to be updated it(AutoShooting)
        return y;
    }

    private float safeShootX() {
        //Needs to be updated it(AutoSHooting)
        return x;
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
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
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
        //g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        //g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

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


    }
public void collides(GameObject with){
        if(with instanceof Wall){
            System.out.println("Hit wall");
            //stop
        } else if (with instanceof Bullet) {
            //lose health

            
        } else if (with instanceof PowerUp) {
            System.out.println("Hit Powerup");
          //  ((PowerUp)with).applyPower(this);
            
        }
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
}
