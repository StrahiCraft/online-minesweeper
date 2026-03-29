package client_server_comunication;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for player statistics
 */
public class GameStatistics implements Serializable {
    /**
     * Width of the board played in this game
     */
    private int boardWidth;
    /**
     * Height of the board played in this game
     */
    private int boardHeight;
    /**
     * Count of the mines in this game
     */
    private int mineCount;
    /**
     * Weather or not this game was won
     */
    private boolean gameWon;

    public GameStatistics() {
    }

    public GameStatistics(int boardWidth, int boardHeight, int mineCount, boolean gameWon) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.mineCount = mineCount;
        this.gameWon = gameWon;
    }

    /**
     * Calculates the winrate of the given list of game statistics
     * @param playerStatistics Statistics of all games the player has played
     * @return The winrate
     */
    public static float calculateWinrate(ArrayList<GameStatistics> playerStatistics){
        int gamesWon = 0;
        for(GameStatistics gameStatistics : playerStatistics){
            if(gameStatistics.isGameWon()){
                gamesWon++;
            }
        }
        return (float) playerStatistics.size() / gamesWon;
    }

    /**
     * Returns the average mine count of all the games a player has played
     * @param playerStatistics Statistics of all games the player has played
     * @return The average mine count of all the games a player has played
     */
    public static float averageMineCount(ArrayList<GameStatistics> playerStatistics){
        int totalMineCount = 0;
        for(GameStatistics gameStatistics : playerStatistics){
            totalMineCount += gameStatistics.getMineCount();
        }
        return (float) totalMineCount / playerStatistics.size();
    }

    /**
     * Returns the average width of all the games a player has played
     * @param playerStatistics Statistics of all games the player has played
     * @return The average width of all the games a player has played
     */
    public static float averageWidth(ArrayList<GameStatistics> playerStatistics){
        int totalWidth = 0;
        for(GameStatistics gameStatistics : playerStatistics){
            totalWidth += gameStatistics.getBoardWidth();
        }
        return (float) totalWidth / playerStatistics.size();
    }

    /**
     * Returns the average height of all the games a player has played
     * @param playerStatistics Statistics of all games the player has played
     * @return The average height of all the games a player has played
     */
    public static float averageHeight(ArrayList<GameStatistics> playerStatistics){
        int totalHeight = 0;
        for(GameStatistics gameStatistics : playerStatistics){
            totalHeight += gameStatistics.getBoardHeight();
        }
        return (float) totalHeight / playerStatistics.size();
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
}
