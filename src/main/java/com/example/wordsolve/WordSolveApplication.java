package com.example.wordsolve;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
    private static Stage mainStage;

    private static Parent levelParent;
    private static Parent shopParent;

    private static Scene currentScene;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(WordSolveApplication.class.getResource("main-view.fxml"));

        Pane mainViewPane = fxmlLoader.load();

        mainViewPane.setBackground(new Background(new BackgroundFill(
                Color.rgb(128, 171, 232), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene levelScene = new Scene(mainViewPane, 320, 240);
        levelScene.getStylesheets().add(getClass().getResource("/com/example/wordsolve/application.css").toExternalForm());

        stage.setTitle("EG Top Window Title.");
        stage.setScene(levelScene);
        stage.setFullScreen(true);
        stage.show();

        mainStage = stage;
        currentScene = levelScene;

        levelParent = FXMLLoader.load(getClass().getResource("main-view.fxml"));

        // TODO: Note the class ShopController.java initialise method will run here.
        shopParent = FXMLLoader.load(getClass().getResource("shop-view-test.fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

    public static void SwitchToShopScene()
    {
        currentScene.setRoot(shopParent);
        ShopController.RefreshShop();
    }

    public static void SwitchToLevelScene()
    {
        currentScene.setRoot(levelParent);
        var levelController = MainController.GetInstance();
        levelController.StartNewLevel();
    }

}