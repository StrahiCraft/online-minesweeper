package game;

import utility.customTypes.Vector2Int;

public class Minefield {
    private Vector2Int dimensions;
    private int mineCount;
    private Field[] minefield;

    public Minefield() {
        dimensions = new Vector2Int(16, 16);
        mineCount = 64;
        minefield = new Field[256];
    }

    public Minefield(Vector2Int dimensions, int mineCount) {
        this.dimensions = dimensions;
        this.mineCount = mineCount;
        minefield = new Field[dimensions.getX() * dimensions.getY()];
    }

    public Minefield(Vector2Int dimensions, int mineCount,Field[] minefield) {
        this.dimensions = dimensions;
        this.mineCount = mineCount;
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

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }
}
