package game.minefield;

import game.minefield.fields.EmptyField;
import game.minefield.fields.Field;
import utility.customTypes.Vector2Int;

public class MineFieldGenerator {
    public static Minefield generateMinefield(Vector2Int dimensions, int mineCount) {
        Field[] fields = new Field[dimensions.getX() *  dimensions.getY()];

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new EmptyField();
        }

        Minefield minefield = new Minefield(dimensions, fields);

        return minefield;
    }
}
