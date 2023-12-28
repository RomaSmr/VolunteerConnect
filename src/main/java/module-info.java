module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

    opens com.example.demo.db to javafx.fxml;
    exports com.example.demo.db;

    opens com.example.demo.models to javafx.fxml;
    exports com.example.demo.models;
}