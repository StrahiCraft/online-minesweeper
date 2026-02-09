package client.rendering;

import game.minefield.Minefield;
import javafx.scene.layout.GridPane;
import utility.customTypes.Vector2Int;

/**
 * Class used for rendering the minefield.
 */
public class MinefieldRenderer {
    /**
     * Renders the minefield using the Minefield class.
     * @param minefield
     * The instance of the minefield class that is going to be rendered.
     * @return
     * A grid pane containing all the fields as buttons.
     */
    public static GridPane renderMinefield(Minefield minefield) {
        GridPane minefieldRender = new GridPane();

        for(int x = 0; x < minefield.getDimensions().getX(); x++){
            for(int y = 0; y < minefield.getDimensions().getY(); y++){
                minefieldRender.add(minefield.getFieldAt(new Vector2Int(x, y)).getFieldGraphics(), x, y);
            }
        }

        return minefieldRender;
    }
}
