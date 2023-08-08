package tankgame.game;

import tankgame.constants.GameObjectID;
import tankgame.constants.ResourceConstants;
import tankgame.game.dynamicObjects.zombies.Zombie;
import tankgame.menus.SingInMenuPanel;
import tankgame.util.ResourceManager;
import tankgame.game.immobileObjects.powerups.DoubleShoot;
import tankgame.game.immobileObjects.powerups.Heal;
import tankgame.game.immobileObjects.powerups.SpeedBoost;
import tankgame.game.dynamicObjects.tanks.Tank;
import tankgame.game.immobileObjects.BreakableWall;
import tankgame.game.immobileObjects.UnbreakableWall;

public class GameObjectFactory {
    public GameObject createGameObject(String id, int row, int col, GameWorld gw) {
        if(id == null || id.isEmpty() || GameObjectID.EMPTY.equals(id)) {
            return null;
        }

        switch(id) {
            case GameObjectID.ZOMBIE -> {
                return new Zombie(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW).getHeight(),
                        0,
                        0,
                        0,
                        2,
                        ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW)
                );
            }

            case GameObjectID.PLAYER_1 -> {
                return new Tank(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW).getHeight(),
                        0,
                        0,
                        0,
                        ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW),
                        1,
                        "player1",
                        gw
                );
            }

            case GameObjectID.PLAYER_2 -> {
                return new Tank(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW).getHeight(),
                        0,
                        0,
                        0,
                        ResourceManager.getImage(ResourceConstants.IMAGES_TANK_ARROW),
                        2,
                        "Player2",
                        gw
                );
            }

            case GameObjectID.BORDER -> {
                return new UnbreakableWall(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_BORDER_WALL).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_BORDER_WALL).getHeight(),
                        ResourceManager.getImage(ResourceConstants.IMAGES_BORDER_WALL)
                );
            }

            case GameObjectID.UNBREAKABLE_WALL -> {
                return new UnbreakableWall(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight(),
                        ResourceManager.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL)
                );
            }

            case GameObjectID.BREAKABLE_WALL -> {
                return new BreakableWall(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL).getHeight(),
                        ResourceManager.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL)
                );
            }

            case GameObjectID.HEAL -> {
                return new Heal(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_HEAL).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_HEAL).getHeight(),
                        ResourceManager.getImage(ResourceConstants.IMAGES_HEAL)
                );
            }

            case GameObjectID.BARRAGE -> {
                return new DoubleShoot(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_BARRAGE).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_BARRAGE).getHeight(),
                        ResourceManager.getImage(ResourceConstants.IMAGES_BARRAGE)
                );
            }

            case GameObjectID.SPEED_BOOST -> {
                return new SpeedBoost(
                        row * ResourceManager.getImage(ResourceConstants.IMAGES_SPEED).getWidth(),
                        col * ResourceManager.getImage(ResourceConstants.IMAGES_SPEED).getHeight(),
                        ResourceManager.getImage(ResourceConstants.IMAGES_SPEED)
                );
            }

            default -> throw new IllegalArgumentException("Unknown Game object ID: " + id + " @ row: " + row + " column: " + col);
        }
    }
}

