package client;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class used for sending messages to the server or back to the client using player ID
 */
public class ClientMessage implements Serializable {
    /**
     * ID of the player sending / receiving the message
     */
    private UUID playerId;
    /**
     * The message being sent / received
     */
    private String message;

    public ClientMessage(UUID playerId, String message) {
        this.playerId = playerId;
        this.message = message;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
