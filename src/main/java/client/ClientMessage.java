package client;

import utility.customTypes.ServerMessageType;

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
    private ServerMessageType messageType;

    private Object messageData;

    public ClientMessage(UUID playerId, ServerMessageType message, Object messageData) {
        this.playerId = playerId;
        this.messageType = message;
        this.messageData = messageData;
    }

    public ClientMessage(UUID playerId, ServerMessageType message) {
        this.playerId = playerId;
        this.messageType = message;
        messageData = null;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public ServerMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ServerMessageType messageType) {
        this.messageType = messageType;
    }

    public Object getMessageData() {
        return messageData;
    }

    public void setMessageData(Object messageData) {
        this.messageData = messageData;
    }
}
