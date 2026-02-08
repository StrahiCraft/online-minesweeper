package game.minefield;

import game.minefield.fields.Field;
import utility.customTypes.Vector2Int;

public class Minefield {
    private Vector2Int dimensions;
    private Field[] minefield;

    public Minefield() {
        dimensions = new Vector2Int(16, 16);
        minefield = new Field[256];
    }

    public Minefield(Vector2Int dimensions) {
        this.dimensions = dimensions;
        minefield = new Field[dimensions.getX() * dimensions.getY()];
    }

    public Minefield(Vector2Int dimensions,Field[] minefield) {
        this.dimensions = dimensions;
        this.minefield = minefield;
    }

    public Vector2Int getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector2Int dimensions) {
        this.dimensions = dimensions;
    }

    public Field[] getMinefield() {
        return minefield;
    }

    public void setMinefield(Field[] minefield) {
        this.minefield = minefield;
    }

    public Field getFieldAt(int x, int y) {
        return getFieldAt(new Vector2Int(x, y));
    }

    public Field getFieldAt(Vector2Int position) {
        return minefield[position.getX() * dimensions.getY() + position.getY()];
    }
}
