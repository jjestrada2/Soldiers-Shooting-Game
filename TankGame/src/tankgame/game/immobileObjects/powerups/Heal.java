package tankgame.game.immobileObjects.powerups;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.util.ResourceManager;
import tankgame.game.dynamicObjects.tanks.Tank;

import java.awt.image.BufferedImage;

public class Heal extends PowerUp {

    public Heal(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.heal(20);
    }

    @Override
    public void playSound() {
        (new Sound(ResourceManager.getSound(ResourceConstants.SOUND_HEAL))).playSound();
    }
}
