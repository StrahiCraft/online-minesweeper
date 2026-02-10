package server.database.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "game_players")
public class GamePlayers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gamePlayersId;
    @Column(name = "player_id", nullable = false)
    private Long playerId;
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    public GamePlayers() {
    }

    public GamePlayers(Long gamePlayersId, Long playerId, Long gameId) {
        this.gamePlayersId = gamePlayersId;
        this.playerId = playerId;
        this.gameId = gameId;
    }

    public Long getGamePlayersId() {
        return gamePlayersId;
    }

    public void setGamePlayersId(Long gamePlayersId) {
        this.gamePlayersId = gamePlayersId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
