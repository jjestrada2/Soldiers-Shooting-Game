package warzone.loaders;

import warzone.constants.GameObjectID;
import warzone.game.GameObject;
import warzone.game.GameObjectProduction;
import warzone.game.GameWorld;
import warzone.game.moveableObjects.MoveableObject;
import warzone.game.stationaryObjects.StationaryObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class GameMapLoader {
    private static GameMapLoader instance;
    private static final ArrayList<int[]> emptySpaces = new ArrayList<>();

    private GameMapLoader() {}

    public static GameMapLoader getInstance() {
        if(instance == null)
            instance = new GameMapLoader();
        return instance;
    }

    public ArrayList<int[]> getEmptySpaces() {
        return emptySpaces;
    }

    public void initializeMap(GameWorld gw, String map) {
        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("maps/" + map))))) {
        GameObjectProduction gameObjectProduction = new GameObjectProduction();
        for(int col = 0;mapReader.ready();col++){
            String[] items = mapReader.readLine().split(",");
            for(int row = 0; row < items.length; row++){
                String gameObjectID = items[row].replace("\\s+","");
                GameObject gameObject = GameObjectProduction.newInstance(gameObjectID,row,col,gw);

                if(GameObjectID.BORDER.equals(gameObjectID)){
                    gw.addToCollisionlessGameObjectCollections(gameObject);
                } else if (gameObject instanceof StationaryObject) {
                    gw.addToStationaryGameObjectCollections((StationaryObject) gameObject);
                } else if (gameObject instanceof MoveableObject) {
                    gw.addToMovableGameObjectCollections((MoveableObject) gameObject);
                }else if(gameObject==null){
                    emptySpaces.add(new int[]{col, row});
                }
            }
        }


        } catch (IOException e) {
            System.out.println(e);
            System.exit(-2);
        }
    }
}
