package client.scene;

import client.ClientApplication;
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
     * The type of the currently displayed scene
     */
    private static SceneType currentSceneType;

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
        currentSceneType = sceneType;
        switch (sceneType) {
            case LOGIN -> mainStage.setScene(SceneFactory.getLoginScene());
            case REGISTER -> mainStage.setScene(SceneFactory.getRegisterScene());
            case MAIN_MENU -> mainStage.setScene(SceneFactory.getMainMenuScene());
            case HOST ->  mainStage.setScene(SceneFactory.getHostScene());
            case LOBBY -> mainStage.setScene(SceneFactory.getLobbyScene());
            case JOIN -> mainStage.setScene(SceneFactory.getJoinScene());
            case GAME -> mainStage.setScene(SceneFactory.getGameScene());
            default -> System.out.println("Scene doesn't exist!");
        }
    }

    /**
     * Refreshes the current scene, this is done by changing the scene to the current scene type
     */
    public static void refreshCurrentScene(){
        changeScene(currentSceneType);
    }

    /**
     * Closes the application and
     */
    public static void close(){
        ClientApplication.getClientInstance().onGameClosed();
        mainStage.close();
    }
}
