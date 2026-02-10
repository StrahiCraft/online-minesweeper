package client.scene;

import javafx.stage.Stage;

/**
 * Class for managing the current scene.
 */
public class SceneManager {
    /**
     * The stage that scenes will be set to.
     */
    private static Stage mainStage;

    /**
     * Sets the stage that will be used for changing scenes. Only call this function once to set up the scene manager.
     * @param stage
     * Stage given in the start function of the ClientApplication.
     */
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Changes the current scene to the scene of a specified type.
     * @param sceneType
     * The type of scene that will be set.
     */
    public static void changeScene(SceneType sceneType) {
        switch (sceneType) {
            case LOGIN:
                mainStage.setScene(SceneFactory.getLoginScene());
                break;
            case REGISTER:
                mainStage.setScene(SceneFactory.getRegisterScene());
                break;
            case MAIN_MENU:
                mainStage.setScene(SceneFactory.getMainMenuScene());
                break;
            case JOIN:
                mainStage.setScene(SceneFactory.getJoinScene());
                break;
            case HOST:
                mainStage.setScene(SceneFactory.getHostScene());
                break;
            case STATISTICS:
                break;
            case GAME:
                mainStage.setScene(SceneFactory.getGameScene());
                break;
            default:
                mainStage.setScene(SceneFactory.getMainMenuScene());
                // TODO add some exception to notify that something has gone wrong
                break;
        }
    }
}
