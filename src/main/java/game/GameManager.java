package game;

import client.scene.SceneManager;
import client.scene.SceneType;
import game.minefield.MineFieldGenerator;
import game.minefield.Minefield;
import utility.customTypes.Vector2Int;

public class GameManager {
    private static Minefield minefield;

    public static void startGame(Vector2Int minefieldDimensions, int mineCount) {
        minefield = MineFieldGenerator.generateMinefield(minefieldDimensions, mineCount);
        SceneManager.changeScene(SceneType.GAME);
    }

    public static void setMinefield(Minefield minefield) {
        GameManager.minefield = minefield;
    }

    public static Minefield getMinefield() {
        return minefield;
    }
}
