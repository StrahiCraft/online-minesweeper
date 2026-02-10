package server.database.tables;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;
    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_players",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")}
    )
    private ArrayList<Game> games;

    public Player() {
    }

    public Player(Long playerId, String username, String password) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
