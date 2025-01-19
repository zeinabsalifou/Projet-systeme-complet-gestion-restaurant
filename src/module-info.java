module tp1.interfacedevoire {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens tp1.interfacedevoire to javafx.fxml;
    exports tp1.interfacedevoire;
}