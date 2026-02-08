package game.minefield.fields;

import javafx.scene.control.Button;
import utility.customTypes.Vector2Int;

public class EmptyField extends Field {

    public EmptyField(Vector2Int position) {
        super(position);
    }

    public EmptyField() {
    }

    @Override
    protected void onFieldDiscovered() {
        fieldGraphics.getStyleClass().remove("undiscovered-field");
        // TODO set different class depending on surrounding mines
        fieldGraphics.getStyleClass().add("empty-field");

        // TODO Discover all neighbours if surrounding mine count is 0
    }
}
