package game.minefield;

import game.minefield.fields.Field;
import game.minefield.fields.Mine;
import utility.customTypes.Vector2Int;

import java.util.HashMap;

public class Minefield {
    private Vector2Int dimensions;
    private HashMap<String, Field> minefield;

    public Minefield() {
        dimensions = new Vector2Int(16, 16);
        minefield = new HashMap<>();
    }

    public Minefield(Vector2Int dimensions) {
        this.dimensions = dimensions;
        minefield = new HashMap<>();
    }

    public Minefield(Vector2Int dimensions, HashMap<String, Field> minefield) {
        this.dimensions = dimensions;
        this.minefield = minefield;
    }

    public Vector2Int getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector2Int dimensions) {
        this.dimensions = dimensions;
    }

    public HashMap<String, Field> getMinefield() {
        return minefield;
    }

    public void setMinefield(HashMap<String, Field> minefield) {
        this.minefield = minefield;
    }

    public Field getFieldAt(int x, int y) {
        return getFieldAt(new Vector2Int(x, y));
    }

    public void setFieldAt(Vector2Int position, Field field) {
        minefield.put(position.toString(), field);
    }

    public Field getFieldAt(Vector2Int position) {
        return minefield.get(position.toString());
    }

    public boolean isMineAt(Vector2Int position) {
        if(!minefield.containsKey(position.toString())) {
            return false;
        }
        return minefield.get(position.toString()).getClass() == Mine.class;
    }
}
