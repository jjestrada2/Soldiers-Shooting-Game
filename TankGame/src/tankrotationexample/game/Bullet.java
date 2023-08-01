package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourcesPool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends  GameObject{
    private float x;
    private float y;
    private float vx; //speed of x
    private float vy; //speed of Y
    private float angle;
    private float R = 5; //speed factor how fast go my tank
    private BufferedImage img;
    private Rectangle hitBox;
    private boolean changeWeapon;

    Bullet(float x, float y, BufferedImage img,float angle) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.img = img;
        this.angle = angle;
        hitBox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());

    }
    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }

    @Override
    public void collides(GameObject obj2) {

    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }



    void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int)x,(int)y);

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


    }

    public void setHeading(float x,float y , float angle){
        this.x=x;
        this.y=y;
        this.angle=angle;
    }


    public void toggleChangeWeaponPressed() {
    }

    public void unToggleChangeWeaponPressed() {
    }
}