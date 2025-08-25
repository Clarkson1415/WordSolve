module com.example.wordsolve {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jdk.compiler;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.sql;

    opens com.example.wordsolve to javafx.fxml;
    exports com.example.wordsolve;
    exports com.example.wordsolve.controllers;
    opens com.example.wordsolve.controllers to javafx.fxml;
}