package tankgame.game.immobileObjects.powerups;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.util.ResourceManager;
import tankgame.game.dynamicObjects.tanks.Tank;

import java.awt.image.BufferedImage;

public class DoubleShoot extends PowerUp {

    public DoubleShoot(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.changeDelayBetweenShots(10);
        tank.setCoolDown(10);
    }

    @Override
    public void playSound() {
        //ResourceHandler.getSound(ResourceConstants.RESOURCE_BARRAGE_SOUND).play();
        (new Sound(ResourceManager.getSound(ResourceConstants.SOUND_BARRAGE))).playSound();
    }
}
