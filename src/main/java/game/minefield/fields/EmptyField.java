package game.minefield.fields;

import game.GameManager;
import utility.customTypes.Vector2Int;

/**
 * Empty field type, this type of field is one that has no mine, it can however have a number between 1 and 8.
 * The number depends on how many mines surround the field. If no mines surround the field, it will not display
 * any number.
 */
public class EmptyField extends Field {
    /**
     * Count of how many mines surround this field. This number is calculated upon minefield creation.
     */
    private int surroundingMinesCount;

    /**
     * Creates an empty field and sets it's position to the specified one.
     * @param position
     * The specified position of this field
     */
    public EmptyField(Vector2Int position) {
        super(position);
    }

    /**
     * Creates an empty field without a specified positon. Uses the default Field constructor.
     */
    public EmptyField() {
        super();
    }

    /**
     * Is called when the field is discovered. When this type of field is discovered, it will try to discover all
     * surrounding fields if there are no mines surrounding it.
     */
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

    /**
     * Returns how many mines surround this field.
     * @return
     * The number of mines surrounding this field.
     */
    public int getSurroundingMinesCount() {
        return surroundingMinesCount;
    }

    /**
     * Sets how many mines surround this field. This function should only be called at field creation.
     * @param surroundingMinesCount
     * How many mines surround this field?
     */
    public void setSurroundingMinesCount(int surroundingMinesCount) {
        this.surroundingMinesCount = surroundingMinesCount;
    }
}
