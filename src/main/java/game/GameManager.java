package game;

import game.minefield.Minefield;

public class GameManager {
    private static Minefield minefield;

    public static void setMinefield(Minefield minefield) {
        GameManager.minefield = minefield;
    }

    public static Minefield getMinefield() {
        return minefield;
    }
}
