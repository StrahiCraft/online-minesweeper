module com.example.onlineminesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.sql;
    requires ons;

    opens client to javafx.fxml;
    exports client;
    exports client.scene;
    opens client.scene to javafx.fxml;
    exports client_server_comunication;
    opens client_server_comunication to javafx.fxml;
}