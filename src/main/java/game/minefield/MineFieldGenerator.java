package game.minefield;

import game.minefield.fields.EmptyField;
import game.minefield.fields.Field;
import utility.customTypes.Vector2Int;

/**
 * Class for generating minefields based on desired dimensions and mine count.
 */
public class MineFieldGenerator {
    /**
     * Generates a minefield based on dimensions and mine count.
     * @param dimensions
     * The size of the minefield
     * @param mineCount
     * How many mines should the minefield have
     * @return
     * The generated minefield with the desired dimensions and mine count
     */
    public static Minefield generateMinefield(Vector2Int dimensions, int mineCount) {
        Field[] fields = new Field[dimensions.getX() *  dimensions.getY()];

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new EmptyField(new Vector2Int(i % dimensions.getX(), i / dimensions.getY()));
        }

        Minefield minefield = new Minefield(dimensions, fields);

        return minefield;
    }
}
