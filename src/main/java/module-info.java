module com.example.onlineminesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jakarta.persistence;
    requires mysql.connector.j;
    requires org.hibernate.orm.core;
    requires java.persistence;


    opens client to javafx.fxml;
    exports client;
    exports client.scene;
    opens client.scene to javafx.fxml;
}