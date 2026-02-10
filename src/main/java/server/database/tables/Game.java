package server.database.tables;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @Column(name = "board_width", nullable = false)
    private int boardWidth;
    @Column(name = "board_height", nullable = false)
    private int boardHeight;
    @Column(name = "mine_count", nullable = false)
    private int mineCount;
    @Column(name = "game_won", nullable = false)
    private boolean gameWon;

    @ManyToMany(mappedBy = "games")
    private ArrayList<Player> players;

    public Game() {
    }

    public Game(Long gameId, int boardWidth, int boardHeight, int mineCount, boolean gameWon) {
        this.gameId = gameId;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.mineCount = mineCount;
        this.gameWon = gameWon;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
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
