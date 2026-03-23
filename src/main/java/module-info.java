module com.example.onlineminesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.sql;

    opens client to javafx.fxml;
    exports client;
    exports client.scene;
    opens client.scene to javafx.fxml;
}