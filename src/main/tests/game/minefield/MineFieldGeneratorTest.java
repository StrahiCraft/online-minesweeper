package game.minefield;

import game.minefield.fields.Field;
import game.minefield.fields.Mine;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import utility.customTypes.Vector2Int;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MineFieldGeneratorTest {
    @Test
    void correctMineCountGenerated(){
        Platform.startup(() -> {
            Vector2Int dimensions = new Vector2Int(16, 16);
            int mineCount = 64;

            int actualMineCount = 0;

            Minefield minefield = MineFieldGenerator.generateMinefield(dimensions, mineCount);

            for (Field field : minefield.getMinefield().values()){
                if(field.getClass() == Mine.class){
                    actualMineCount++;
                }
            }

            assertEquals(mineCount, actualMineCount);
        });
    }
}