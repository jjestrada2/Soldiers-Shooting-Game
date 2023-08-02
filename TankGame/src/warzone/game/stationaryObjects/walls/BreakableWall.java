package warzone.game.stationaryObjects.walls;

import warzone.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    public BreakableWall(float x, float y, BufferedImage img) {
            super(x, y, img);
        }

        @Override
        public void setDestroyed(boolean status) {
            super.isDestroyed = status;
        }

        public void playSound() {
            //new sound(smashrock);
        }
}
