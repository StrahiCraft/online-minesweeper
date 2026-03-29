package game.minefield.fields;

import client.ClientApplication;
import client_server_comunication.ServerMessageType;
import server.ClientHandler;
import utility.customTypes.Vector2Int;

/**
 * Mine field type, if this one is discovered, the players lose and the game is over.
 */
public class Mine extends Field{
    /**
     * Empty mine constructor, uses the default Field constructor.
     */
    public Mine() {
        super();
    }

    /**
     * Creates a mine on a given position.
     * @param position
     * The position of the mine.
     */
    public Mine(Vector2Int position) {
        super(position);
    }

    /**
     * After this type of field is discovered, the players lose and the game is over.
     */
    @Override
    protected void onFieldDiscovered() {
        setDiscoveredGraphics();
        ClientApplication.getClientInstance().sendMessage(ServerMessageType.GAME_LOST, ClientApplication.getClientInstance().getCurrentLobbyData());
    }

    /**
     * Sets the discovered graphics of the mine if it is currently discovered
     */
    @Override
    protected void setDiscoveredGraphics() {
        if(undiscovered){
            return;
        }

        fieldGraphics.getStyleClass().remove("undiscovered-field");
        fieldGraphics.getStyleClass().add("mine");
    }
}
