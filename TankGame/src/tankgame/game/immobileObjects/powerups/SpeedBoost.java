package tankgame.game.immobileObjects.powerups;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.util.ResourceManager;
import tankgame.game.dynamicObjects.tanks.Tank;

import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp {

    public SpeedBoost(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.changeSpeed(4);
    }

    @Override
    public void playSound() {
        (new Sound(ResourceManager.getSound(ResourceConstants.SOUND_SPEED))).playSound();
    }
}
