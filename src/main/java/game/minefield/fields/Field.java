package game.minefield.fields;

import javafx.scene.control.Button;
import utility.customTypes.Vector2Int;

/**
 * Abstract class used for making all types of fields.
 */
public abstract class Field {
    /**
     * Position of the field.
     */
    private Vector2Int position;

    /**
     * Position of the field.
     */
    private boolean undiscovered = true;
    private boolean marked = false;

    public Field() {
        position = Vector2Int.zero();
    }

    public Field(Vector2Int position) {
        this.position = position;
    }

    public void uncoverField(){
        undiscovered = true;
        onFieldUndiscovered();
    }

    public void toggleFieldMarked(){
        marked = !marked;
    }

    protected abstract void onFieldUndiscovered();

    public Button getFieldGraphics(){
        if(undiscovered){
            return getUndiscoveredGraphics();
        }

        if(marked){
            return getMarkedGraphics();
        }

        return getDiscoveredGraphics();
    }

    private Button getUndiscoveredGraphics() {
        return new Button();
    }

    private Button getMarkedGraphics() {
        return  new Button();
    }

    protected abstract Button getDiscoveredGraphics();
}
