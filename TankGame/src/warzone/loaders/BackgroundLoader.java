package warzone.loaders;

import warzone.constants.GameConstants;
import warzone.constants.ResourcesConstants;

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
        backgroundImage = ResourcesManager.getSprite(ResourcesConstants.FLOOR);
        bgWidth =ResourcesManager.getSprite(ResourcesConstants.FLOOR).getWidth();
        bgHeight = ResourcesManager.getSprite(ResourcesConstants.FLOOR).getHeight();
        maxRowTile = GameConstants.GAME_WORLD_WIDTH / bgWidth;
        maxColumnTile = GameConstants.GAME_WORLD_HEIGHT / bgHeight;
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
