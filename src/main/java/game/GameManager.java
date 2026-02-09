package game;

import client.scene.SceneManager;
import client.scene.SceneType;
import game.minefield.MineFieldGenerator;
import game.minefield.Minefield;
import utility.customTypes.Vector2Int;

/**
 * Helper class for managing the game
 */
public class GameManager {
    /**
     * The instance of the minefield being used for playing currently
     */
    private static Minefield minefield;

    /**
     * Starts a new game with the specified parameters.
     * @param minefieldDimensions
     * Dimensions of the gameboard
     * @param mineCount
     * Number of mines in the current gameboard
     */
    public static void startGame(Vector2Int minefieldDimensions, int mineCount) {
        minefield = MineFieldGenerator.generateMinefield(minefieldDimensions, mineCount);
        SceneManager.changeScene(SceneType.GAME);
    }

    /**
     * Sets the minefield that's currently being used for play.
     * @param minefield
     * The minefield the game is being played on
     */
    public static void setMinefield(Minefield minefield) {
        GameManager.minefield = minefield;
    }

    /**
     * Returns the instance of the current minefield that's currently being used for play.
     * @return
     * The instance of the current minefield
     */
    public static Minefield getMinefield() {
        return minefield;
    }
}
