package game.minefield.fields;

import client.ClientApplication;
import client_server_comunication.ServerMessageType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import utility.customTypes.Vector2Int;

import java.io.Serializable;

/**
 * Abstract class used for making all types of fields.
 */
public abstract class Field implements Serializable {
    /**
     * Position of the field.
     */
    private Vector2Int position;

    /**
     * Determines if the field is discovered
     */
    protected boolean undiscovered = true;
    /**
     * Determines if the field is marked
     */
    private boolean marked = false;

    /**
     * Stores the data of this fields button. This is used for changing style at runtime.
     */
    protected transient Button fieldGraphics;

    /**
     * Default field constructor, sets field position to (0, 0).
     */
    public Field() {
        position = Vector2Int.zero();
        setupFieldButton();
    }

    /**
     * Creates a field with a specified position.
     * @param position
     * Position of the field
     */
    public Field(Vector2Int position) {
        this.position = position;
        setupFieldButton();
    }

    /**
     * Sets up field graphics button events for mouse click events.
     */
    private void setupFieldButton(){
        fieldGraphics = new Button();

        fieldGraphics.getStyleClass().add("base-field");
        fieldGraphics.getStyleClass().add("undiscovered-field");

        setMarkedGraphics();
        setDiscoveredGraphics();

        fieldGraphics.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                discoverField();
            }
            if(event.getButton() == MouseButton.SECONDARY){
                toggleFieldMarked();
            }

            ClientApplication.getClientInstance().sendMessage(ServerMessageType.REFRESH_GAME, ClientApplication.getClientInstance().getCurrentLobbyData());
        });
    }

    /**
     * Uncovers this field, setting its base style to uncovered and doing its uncovering functionality. A field can't
     * be discovered if marked.
     */
    public void discoverField(){
        if(marked){
            return;
        }

        if(!undiscovered){
            return;
        }

        undiscovered = false;

        onFieldDiscovered();
    }

    /**
     * Toggles the marked variable, a field can not be marked if it is discovered.
     */
    public void toggleFieldMarked(){
        if(!undiscovered){
            return;
        }

        marked = !marked;

        setMarkedGraphics();
    }

    private void setMarkedGraphics(){
        if(marked){
            fieldGraphics.getStyleClass().add("marked-field");
            fieldGraphics.getStyleClass().remove("undiscovered-field");
            return;
        }
        fieldGraphics.getStyleClass().remove("marked-field");
        fieldGraphics.getStyleClass().add("undiscovered-field");
    }

    /**
     * Uncovering functionality, different for every derived class. Always implement a CSS class change
     * to the proper CSS class when implementing this function.
     */
    protected abstract void onFieldDiscovered();

    protected abstract void setDiscoveredGraphics();

    /**
     * Gets field graphics (this fields button). Used for rendering.
     * @return The field graphics button
     */
    public Button getFieldGraphics(){
        setupFieldButton();
        return fieldGraphics;
    }

    public Vector2Int getPosition() {
        return position;
    }


}
