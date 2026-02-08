package game;

import utility.customTypes.Vector2Int;

public class Field {
    private Vector2Int position;
    private boolean uncovered = false;

    public Field() {
        position = Vector2Int.zero();
    }

    public Field(Vector2Int position) {
        this.position = position;
    }

    public void uncoverField(){
        uncovered = true;
        onFieldUncovered();
    }

    protected void onFieldUncovered(){

    }
}
