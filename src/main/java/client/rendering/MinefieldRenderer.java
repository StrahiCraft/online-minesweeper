package client.rendering;

import game.Minefield;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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
                // TODO change this so it uses actual minefield data
                minefieldRender.add(new Button(), x, y);
            }
        }

        return minefieldRender;
    }

}
