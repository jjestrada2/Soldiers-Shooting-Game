package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends  GameObject{

    float x,y;
    BufferedImage img;


    public Bullet(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;


    }
    @Override
    public void drawImage(Graphics g) {

    }
}
