package tankgame.game.immobileObjects;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.util.ResourceManager;

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
        (new Sound(ResourceManager.getSound(ResourceConstants.SOUND_ROCK_SMASH))).playSound();
    }

}
