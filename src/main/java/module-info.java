module com.example.onlineminesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens client to javafx.fxml;
    exports client;
    exports client.scene;
    opens client.scene to javafx.fxml;
}