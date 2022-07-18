module test.homingcircle {
    requires javafx.controls;
    requires javafx.fxml;


    opens test.homingcircle to javafx.fxml;
    exports test.homingcircle;
}