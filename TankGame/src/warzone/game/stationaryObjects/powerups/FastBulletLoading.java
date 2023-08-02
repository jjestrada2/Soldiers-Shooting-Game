package warzone.game.stationaryObjects.powerups;

import warzone.constants.ResourcesConstants;
import warzone.game.moveableObjects.tanks.Tank;
import warzone.loaders.ResourcesManager;
import warzone.util.Sound;

import java.awt.image.BufferedImage;

public class FastBulletLoading extends PowerUp {


    public FastBulletLoading(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.changeDelayBetweenShots(10);
    }

    @Override
    public void playSound() {
        (new Sound(ResourcesManager.getSound(ResourcesConstants.SOUND_PICKUP))).playSound();
    }
}
