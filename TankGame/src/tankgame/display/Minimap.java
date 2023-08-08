package tankgame.display;

import tankgame.constants.GameScreenConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Minimap {

    private static final double SCALE = 0.15;
    private final double scaledWidth;
    private final double scaledHeight;


    public Minimap() {
        this.scaledWidth = GameScreenConstants.GAME_SCREEN_WIDTH / 2f - (GameScreenConstants.WORLD_WIDTH * SCALE) / 2f;
        this.scaledHeight = GameScreenConstants.GAME_SCREEN_HEIGHT - GameScreenConstants.WORLD_HEIGHT * (SCALE * 1.14);
    }

    public void drawMinimap(BufferedImage world, Graphics2D g2) {
        BufferedImage minimap = world.getSubimage(0, 0, GameScreenConstants.WORLD_WIDTH, GameScreenConstants.WORLD_HEIGHT);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(this.scaledWidth, this.scaledHeight);
        affineTransform.scale(SCALE, SCALE);
        g2.drawImage(minimap, affineTransform, null);
    }

    public int getScaledWidth() {
        return (int) this.scaledWidth;
    }

    public int getScaledHeight() {
        return (int) this.scaledHeight;
    }

}
