package warzone.game;

import warzone.constants.GameObjectID;
import warzone.constants.ResourcesConstants;
import warzone.game.moveableObjects.tanks.Tank;
import warzone.game.stationaryObjects.powerups.FastBulletLoading;
import warzone.game.stationaryObjects.powerups.Heal;
import warzone.game.stationaryObjects.walls.BreakableWall;
import warzone.game.stationaryObjects.walls.UnbreakableWall;
import warzone.loaders.ResourcesManager;

public class GameObjectProduction {

        public static GameObject newInstance(String id, int row,int col, GameWorld gw){
            if(id == null){
                return null;
            }
            switch (id){
                case GameObjectID.PLAYER_1 -> {
                    return new Tank(
                            row* ResourcesManager.getSprite(ResourcesConstants.TANK1).getWidth(),
                            col* ResourcesManager.getSprite(ResourcesConstants.TANK1).getHeight(),
                            0,
                            0,
                            0,
                            ResourcesManager.getSprite(ResourcesConstants.TANK1),
                            1,
                            "player1",
                            gw
                    );
                }

                case GameObjectID.PLAYER_2 -> {
                    return new Tank(
                            row* ResourcesManager.getSprite(ResourcesConstants.TANK2).getWidth(),
                            col* ResourcesManager.getSprite(ResourcesConstants.TANK2).getHeight(),
                            0,
                            0,
                            0,
                            ResourcesManager.getSprite(ResourcesConstants.TANK2),
                            2,
                            "player2",
                            gw
                    );
                }
                case GameObjectID.BORDER -> {
                    return new UnbreakableWall(
                            row*ResourcesManager.getSprite(ResourcesConstants.UNBREAK).getWidth(),
                            col*ResourcesManager.getSprite(ResourcesConstants.UNBREAK).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.UNBREAK)
                    );
                }

                case GameObjectID.UNBREAKABLE_WALL1 -> {
                    return new UnbreakableWall(
                            row*ResourcesManager.getSprite(ResourcesConstants.BREAK1).getWidth(),
                            col*ResourcesManager.getSprite(ResourcesConstants.BREAK1).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.BREAK1)
                    );
                }
                case GameObjectID.BREAKABLE_WALL1 -> {
                    return new BreakableWall(
                            row*ResourcesManager.getSprite(ResourcesConstants.BREAK2).getWidth(),
                            col*ResourcesManager.getSprite(ResourcesConstants.BREAK2).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.BREAK2)
                    );
                }

                case GameObjectID.BREAKABLE_WALL2 -> {
                    return new BreakableWall(
                            row*ResourcesManager.getSprite(ResourcesConstants.BREAK3).getWidth(),
                            col*ResourcesManager.getSprite(ResourcesConstants.BREAK3).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.BREAK3)
                    );
                }

                case GameObjectID.UNBREAKABLE_WALL2 -> {
                    return new UnbreakableWall(
                            row*ResourcesManager.getSprite(ResourcesConstants.BREAK4).getWidth(),
                            col*ResourcesManager.getSprite(ResourcesConstants.BREAK4).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.BREAK4)
                    );
                }


                case GameObjectID.HEAL -> {
                    return new Heal(
                            row * ResourcesManager.getSprite(ResourcesConstants.HEALTH).getWidth(),
                            col * ResourcesManager.getSprite(ResourcesConstants.HEALTH).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.HEALTH)
                    );
                }

                case GameObjectID.FAST_LOADING -> {
                    return new FastBulletLoading(
                            row * ResourcesManager.getSprite(ResourcesConstants.SHIELD).getWidth(),
                            col * ResourcesManager.getSprite(ResourcesConstants.SHIELD).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.SHIELD)

                    );
                }

                case GameObjectID.SPEED_BOOST -> {
                    return new FastBulletLoading(
                            row * ResourcesManager.getSprite(ResourcesConstants.SPEED).getWidth(),
                            col * ResourcesManager.getSprite(ResourcesConstants.SPEED).getHeight(),
                            ResourcesManager.getSprite(ResourcesConstants.SPEED)

                    );
                }
                default -> throw new IllegalArgumentException("Unknown Game object ID: " + id + " @ row: " + row + " column: " + col);

            }
        }
    }
/*
    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException{
        return switch (type){
            case "9" -> new UnbreakableWall(x,y,ResourcesManager.getSprite("unbreak"));
            case "1" -> new BreakableWall(x,y, ResourcesManager.getSprite("break1"));
            case "2" -> new BreakableWall(x,y, ResourcesManager.getSprite("break2"));
            case "3" -> new BreakableWall(x,y, ResourcesManager.getSprite("break3"));
            case "4" -> new Health(x,y,ResourcesManager.getSprite("break4"));
            case "5" -> new Speed(x,y,ResourcesManager.getSprite("break4"));
            case "6" -> new Shield(x,y,ResourcesManager.getSprite("shield"));
            case "7" -> new BreakableWall(x,y, ResourcesManager.getSprite("health"));
            case "8" -> new BreakableWall(x,y, ResourcesManager.getSprite("speed"));
            default -> throw new UnsupportedOperationException();*/