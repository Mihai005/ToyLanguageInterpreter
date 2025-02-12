module com.example.toylanguageinterpretergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.management;

    opens com.example.toylanguageinterpretergui to javafx.fxml;
    exports com.example.toylanguageinterpretergui;
}