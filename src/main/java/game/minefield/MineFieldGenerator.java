package game.minefield;

import game.minefield.fields.EmptyField;
import game.minefield.fields.Field;
import game.minefield.fields.Mine;
import utility.customTypes.Vector2Int;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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
        HashMap<String, Field> fields = new HashMap<>();

        for(int x = 0; x < dimensions.getX(); x++){
            for(int y = 0; y < dimensions.getY(); y++){
                fields.put(new Vector2Int(x, y).toString(), new EmptyField(new Vector2Int(x, y)));
            }
        }

        HashSet<Vector2Int> mineIndexes = new HashSet<>();
        Random randomIndex = new Random();

        while (mineIndexes.size() < mineCount) {
            mineIndexes.add(new Vector2Int(randomIndex.nextInt(dimensions.getX()),
                    randomIndex.nextInt(dimensions.getY())));
        }

        Minefield minefield = new Minefield(dimensions, fields);

        for(Vector2Int position : mineIndexes){
            minefield.setFieldAt(position, new Mine(position));
        }

        for (Field field : fields.values()) {
            if (field.getClass() == EmptyField.class) {
                ((EmptyField) field).setSurroundingMinesCount(getSurroundingMineCount(field.getPosition(), minefield));
            }
        }

        return minefield;
    }

    /**
     * Gets the surrounding mine count based on the field's position.
     * @param position
     * Position of the field that's being checked for surrounding mines
     * @param minefield
     * The minefield that the field belongs to
     * @return
     * The number of surrounding mines
     */
    private static int getSurroundingMineCount(Vector2Int position, Minefield minefield) {
        int surroundingMineCount = 0;

        if(minefield.isMineAt(position.add(Vector2Int.up()))){
            surroundingMineCount++;
        }
        if(minefield.isMineAt(position.add(Vector2Int.down()))){
            surroundingMineCount++;
        }
        if(minefield.isMineAt(position.add(Vector2Int.left()))){
            surroundingMineCount++;
        }
        if(minefield.isMineAt(position.add(Vector2Int.right()))){
            surroundingMineCount++;
        }

        if(minefield.isMineAt(position.add(Vector2Int.up().add(Vector2Int.left())))){
            surroundingMineCount++;
        }
        if(minefield.isMineAt(position.add(Vector2Int.up().add(Vector2Int.right())))){
            surroundingMineCount++;
        }
        if(minefield.isMineAt(position.add(Vector2Int.down().add(Vector2Int.left())))){
            surroundingMineCount++;
        }
        if(minefield.isMineAt(position.add(Vector2Int.down().add(Vector2Int.right())))){
            surroundingMineCount++;
        }

        return surroundingMineCount;
    }
}
