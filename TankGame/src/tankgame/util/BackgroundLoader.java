package tankgame.util;

import tankgame.constants.GameScreenConstants;
import tankgame.constants.ResourceConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundLoader {

    private static BackgroundLoader instance;
    private static BufferedImage backgroundImage;
    private static int maxRowTile;
    private static int maxColumnTile;
    private static int bgWidth;
    private static int bgHeight;

    private BackgroundLoader() {}

    public static BackgroundLoader getInstance() {
        if(instance == null)
            instance = new BackgroundLoader();
        return instance;
    }
    public void initializeBackground() {
        backgroundImage = ResourceManager.getImage(ResourceConstants.IMAGES_FLOOR_TILE);
        bgWidth = ResourceManager.getImage(ResourceConstants.IMAGES_FLOOR_TILE).getWidth();
        bgHeight = ResourceManager.getImage(ResourceConstants.IMAGES_FLOOR_TILE).getHeight();
        maxRowTile = GameScreenConstants.WORLD_WIDTH / bgWidth;
        maxColumnTile = GameScreenConstants.WORLD_HEIGHT / bgHeight;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int row = 0; row < maxRowTile; row++) {
            for(int col = 0; col < maxColumnTile; col++) {
                g2d.drawImage(backgroundImage, row * bgWidth, col * bgHeight, null);
            }
        }
    }
}
