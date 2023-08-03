package tankgame.display;

import tankgame.constants.GameScreenConstants;
import tankgame.game.dynamicObjects.tanks.Tank;

import java.awt.image.BufferedImage;

public class Camera {

    private static final int SPLIT_SCREEN_WIDTH = (GameScreenConstants.GAME_SCREEN_WIDTH / 2);
    private BufferedImage splitScreen;
    private final Tank tank;
    private final int HUD_LIMIT;

    public Camera(Tank tank, int HUD_LIMIT) {
        this.tank = tank;
        this.HUD_LIMIT = HUD_LIMIT;
    }

    public void drawSplitScreen(BufferedImage world) {
        int x = (int) tank.getX();
        int y = (int) tank.getY();
        splitScreen = world.getSubimage(this.checkBorderX(x), this.checkBorderY(y), SPLIT_SCREEN_WIDTH, GameScreenConstants.GAME_SCREEN_HEIGHT - HUD_LIMIT);
    }

    public BufferedImage getSplitScreen() {
        return this.splitScreen;
    }

    /**
     *
     * @param x this is the current x coordinate of the tank this camera is tracking
     * @return x value that will tell the camera where to position the sub-image without it going out the raster
     */
    private int checkBorderX(int x) {
        if(x < GameScreenConstants.GAME_SCREEN_WIDTH / 4) {
            x = GameScreenConstants.GAME_SCREEN_WIDTH / 4;
        } else if (x > GameScreenConstants.WORLD_WIDTH - GameScreenConstants.GAME_SCREEN_WIDTH / 4) {
            x = GameScreenConstants.WORLD_WIDTH - GameScreenConstants.GAME_SCREEN_WIDTH / 4;
        }
        x -= GameScreenConstants.GAME_SCREEN_WIDTH / 4;
        return x;
    }

    /**
     *
     * @param y this the current y coordinate of the tank this camera is tracking
     * @return y value that will tell the camera where to position the sub-image without it going out the raster
     */
    private int checkBorderY(int y) {
        if(y < GameScreenConstants.GAME_SCREEN_HEIGHT / 2) {
            y = GameScreenConstants.GAME_SCREEN_HEIGHT / 2;
        } else if(y > GameScreenConstants.WORLD_HEIGHT - GameScreenConstants.GAME_SCREEN_HEIGHT / 2 + HUD_LIMIT) {
            y = GameScreenConstants.WORLD_HEIGHT - GameScreenConstants.GAME_SCREEN_HEIGHT / 2 + HUD_LIMIT;
        }
        y -= GameScreenConstants.GAME_SCREEN_HEIGHT / 2;
        return y;
    }
}
