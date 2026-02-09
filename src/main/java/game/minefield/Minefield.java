package game.minefield;

import game.GameManager;
import game.minefield.fields.Field;
import game.minefield.fields.Mine;
import utility.customTypes.Vector2Int;

import java.util.HashMap;

public class Minefield {
    /**
     * The dimensions of the minefield, they store how big it is
     */
    private Vector2Int dimensions;
    /**
     * Stores all fields by position, uses String instead of Vector2Int because String isn't stored by reference
     */
    private HashMap<String, Field> minefield;

    public Minefield() {
        dimensions = new Vector2Int(16, 16);
        minefield = new HashMap<>();
        GameManager.setMinefield(this);
    }

    public Minefield(Vector2Int dimensions) {
        this.dimensions = dimensions;
        minefield = new HashMap<>();
        GameManager.setMinefield(this);
    }

    public Minefield(Vector2Int dimensions, HashMap<String, Field> minefield) {
        this.dimensions = dimensions;
        this.minefield = minefield;
        GameManager.setMinefield(this);
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

    /**
     * Checks if there is a mine at the given position
     * @param position
     * Position that is being checked for a mine
     * @return
     * True if there is a mine at the given position, False if there isn't or if the position is out of bounds
     */
    public boolean isMineAt(Vector2Int position) {
        if(!minefield.containsKey(position.toString())) {
            return false;
        }
        return minefield.get(position.toString()).getClass() == Mine.class;
    }

    /**
     * Discovers all fields surrounding the given position
     * @param position
     * Position of the field to discover around
     */
    public void discoverSurroundingFields(Vector2Int position){
        if(minefield.containsKey(position.add(Vector2Int.left()).toString())) {
            minefield.get(position.add(Vector2Int.left()).toString()).discoverField();
        }
        if(minefield.containsKey(position.add(Vector2Int.right()).toString())) {
            minefield.get(position.add(Vector2Int.right()).toString()).discoverField();
        }
        if(minefield.containsKey(position.add(Vector2Int.up()).toString())) {
            minefield.get(position.add(Vector2Int.up()).toString()).discoverField();
        }
        if(minefield.containsKey(position.add(Vector2Int.down()).toString())) {
            minefield.get(position.add(Vector2Int.down()).toString()).discoverField();
        }

        if(minefield.containsKey(position.add(Vector2Int.left().add(Vector2Int.up())).toString())) {
            minefield.get(position.add(Vector2Int.left().add(Vector2Int.up())).toString()).discoverField();
        }
        if(minefield.containsKey(position.add(Vector2Int.left().add(Vector2Int.down())).toString())){
            minefield.get(position.add(Vector2Int.left().add(Vector2Int.down())).toString()).discoverField();
        }
        if(minefield.containsKey(position.add(Vector2Int.right().add(Vector2Int.up())).toString())) {
            minefield.get(position.add(Vector2Int.right().add(Vector2Int.up())).toString()).discoverField();
        }
        if(minefield.containsKey(position.add(Vector2Int.right().add(Vector2Int.down())).toString())){
            minefield.get(position.add(Vector2Int.right().add(Vector2Int.down())).toString()).discoverField();
        }
    }
}
