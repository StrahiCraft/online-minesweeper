package game.minefield.fields;

import game.GameManager;
import javafx.scene.control.Button;
import utility.customTypes.Vector2Int;

public class EmptyField extends Field {
    private int surroundingMinesCount;

    public EmptyField(Vector2Int position) {
        super(position);
    }

    public EmptyField() {
    }

    @Override
    protected void onFieldDiscovered() {
        fieldGraphics.getStyleClass().remove("undiscovered-field");

        switch (surroundingMinesCount){
            case 0:
                fieldGraphics.getStyleClass().add("empty-field");
                GameManager.getMinefield().discoverSurroundingFields(getPosition());
                break;
            case 1:
                fieldGraphics.getStyleClass().add("one-mine");
                break;
            case 2:
                fieldGraphics.getStyleClass().add("two-mines");
                break;
            case 3:
                fieldGraphics.getStyleClass().add("three-mines");
                break;
            case 4:
                fieldGraphics.getStyleClass().add("four-mines");
                break;
            case 5:
                fieldGraphics.getStyleClass().add("five-mines");
                break;
            case 6:
                fieldGraphics.getStyleClass().add("six-mines");
                break;
            case 7:
                fieldGraphics.getStyleClass().add("seven-mines");
                break;
            case 8:
                fieldGraphics.getStyleClass().add("eight-mines");
                break;
        }
    }

    public int getSurroundingMinesCount() {
        return surroundingMinesCount;
    }

    public void setSurroundingMinesCount(int surroundingMinesCount) {
        this.surroundingMinesCount = surroundingMinesCount;
    }
}
