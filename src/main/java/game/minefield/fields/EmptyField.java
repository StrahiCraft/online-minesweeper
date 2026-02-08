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

    }
}
