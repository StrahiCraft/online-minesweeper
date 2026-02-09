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

    /**
     * Default minefield constructor, sets the dimensions to 16x16.
     */
    public Minefield() {
        dimensions = new Vector2Int(16, 16);
        minefield = new HashMap<>();
        GameManager.setMinefield(this);
    }

    /**
     * Minefield constructor with specified dimensions.
     * @param dimensions
     * The dimensions of the minefield
     */
    public Minefield(Vector2Int dimensions) {
        this.dimensions = dimensions;
        minefield = new HashMap<>();
        GameManager.setMinefield(this);
    }

    /**
     * Constructor for an already generated minefield, called when starting a new game.
     * @param dimensions
     * The dimensions of the minefiel
     * @param minefield
     * The generated minefield
     */
    public Minefield(Vector2Int dimensions, HashMap<String, Field> minefield) {
        this.dimensions = dimensions;
        this.minefield = minefield;
        GameManager.setMinefield(this);
    }

    /**
     * Returns the dimensions of the minefield
     * @return
     * Dimensions of the minefield
     */
    public Vector2Int getDimensions() {
        return dimensions;
    }

    /**
     * Sets the dimensions of the minefield
     * @param dimensions
     * New dimensions of the minefield
     */
    public void setDimensions(Vector2Int dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Returns the current minefield
     * @return
     * The current minefield
     */
    public HashMap<String, Field> getMinefield() {
        return minefield;
    }

    /**
     * Sets the current minefield
     * @param minefield
     * New minefield to replace the current one
     */
    public void setMinefield(HashMap<String, Field> minefield) {
        this.minefield = minefield;
    }

    /**
     * Returns the field of the specified position
     * @param position
     * Position of the field
     * @return
     * The field with the specified position
     */
    public Field getFieldAt(Vector2Int position) {
        return minefield.get(position.toString());
    }

    /**
     * Sets the field at the chosen position to a specific field
     * @param position
     * The position of the field that is being changed
     * @param field
     * The new field being set to the position
     */
    public void setFieldAt(Vector2Int position, Field field) {
        minefield.put(position.toString(), field);
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
