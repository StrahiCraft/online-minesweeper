package game.minefield.fields;

import javafx.scene.control.Button;
import utility.customTypes.Vector2Int;

public class Mine extends Field{
    public Mine() {

    }

    public Mine(Vector2Int position) {
        super(position);
    }

    @Override
    protected void onFieldDiscovered() {
        // TODO lose game
    }
}
