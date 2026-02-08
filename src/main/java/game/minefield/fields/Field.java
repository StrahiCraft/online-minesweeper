package game.minefield.fields;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
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
     * Determines if the field is discovered
     */
    private boolean undiscovered = true;
    /**
     * Determines if the field is marked
     */
    private boolean marked = false;

    /**
     * Stores the data of this fields button. This is used for changing style at runtime.
     */
    protected Button fieldGraphics;

    public Field() {
        position = Vector2Int.zero();
        setupFieldButton();
    }

    public Field(Vector2Int position) {
        this.position = position;
        setupFieldButton();
    }

    /**
     * Sets up field graphics button events for mouse click events.
     */
    private void setupFieldButton(){
        fieldGraphics = new Button();
        fieldGraphics.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                discoverField();
            }
            if(event.getButton() == MouseButton.SECONDARY){
                toggleFieldMarked();
            }
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

        System.out.println("Field discovered");
        // TODO change field to discovered style class

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
        System.out.println("Field toggle marked " + marked);

        // TODO change field to marked style class
    }

    /**
     * Uncovering functionality, different for every derived class.
     */
    protected abstract void onFieldDiscovered();

    /**
     * Gets field graphics (this fields button). Used for rendering.
     * @return The field graphics button
     */
    public Button getFieldGraphics(){
        return fieldGraphics;
    }
}
