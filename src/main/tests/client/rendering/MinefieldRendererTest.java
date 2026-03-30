package client.rendering;

import client.minefield.MineFieldGenerator;
import client.minefield.Minefield;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;
import utility.customTypes.Vector2Int;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinefieldRendererTest {

    @Test
    void correctRenderedMinefieldSize() {
        Platform.startup(() -> {
            Vector2Int dimensions = new Vector2Int(16, 16);

            Minefield minefield = MineFieldGenerator.generateMinefield(dimensions, 64);

            GridPane minefieldRender = MinefieldRenderer.renderMinefield(minefield);

            int expectedFieldCount = dimensions.getX() * dimensions.getY();
            int actualFieldCount = minefieldRender.getColumnCount() * minefieldRender.getRowCount();

            assertEquals(expectedFieldCount, actualFieldCount);
        });
    }
}