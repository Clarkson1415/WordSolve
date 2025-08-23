package com.example.wordsolve;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class WordSolveApplication extends Application
{
    private Stage stage;

    private static WordSolveApplication instance;

    public WordSolveApplication() {
        instance = this;
    }

    public static WordSolveApplication getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(WordSolveApplication.class.getResource("main-view.fxml"));

        Pane mainViewPane = fxmlLoader.load();

        mainViewPane.setBackground(new Background(new BackgroundFill(
                Color.rgb(128, 171, 232), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(mainViewPane, 320, 240);
        scene.getStylesheets().add(getClass().getResource("/com/example/wordsolve/application.css").toExternalForm());

        stage.setTitle("EG Top Window Title.");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        this.stage = stage;
    }

    public void switchScene(String fxmlPath) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Pane newRoot = fxmlLoader.load();

        Scene newScene = new Scene(newRoot);
        newScene.getStylesheets().add(getClass().getResource("/com/example/wordsolve/application.css").toExternalForm());

        stage.setScene(newScene);
    }

    public static void main(String[] args)
    {
        launch();
    }
}