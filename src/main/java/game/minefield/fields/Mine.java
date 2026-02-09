package game.minefield.fields;

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
        fieldGraphics.getStyleClass().remove("undiscovered-field");
        fieldGraphics.getStyleClass().add("mine");
        // TODO lose game
    }
}
