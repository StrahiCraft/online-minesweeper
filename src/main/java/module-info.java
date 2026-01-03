module com.example.onlineminesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.onlineminesweeper to javafx.fxml;
    exports com.example.onlineminesweeper;
}