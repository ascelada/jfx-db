module com.test.jfxdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.test.jfxdb to javafx.fxml;
    exports com.test.jfxdb;
    exports controllers;
    exports entities;
    opens controllers to javafx.fxml;
    opens entities to javafx.fxml;
}