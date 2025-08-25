package com.example.wordsolve;

import com.example.wordsolve.controllers.IWordSolveController;
import com.example.wordsolve.controllers.MainController;
import com.example.wordsolve.controllers.ShopController;
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
    private Parent shopParent;

    private Parent levelParent;

    private Scene currentScene;

    private static Stage mainStage;

    private IWordSolveController currentController;

    public static Stage getMainStage()
    {
        return mainStage;
    }

    private static WordSolveApplication instance;

    public static WordSolveApplication getInstance()
    {
        return instance;
    }

    public IWordSolveController getCurrentController()
    {
        return currentController;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        instance = this;

        // Load level scene
        FXMLLoader levelLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Pane mainViewPane = levelLoader.load();
        levelController = levelLoader.getController();

        // inject after getting controller
        if (levelController != null) {
            levelController.injectApplication(this);
        }

        // Set background
        mainViewPane.setBackground(new Background(new BackgroundFill(
                Color.rgb(128, 171, 232), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene levelScene = new Scene(mainViewPane, 320, 240);
        levelScene.getStylesheets().add(getClass().getResource("/com/example/wordsolve/application.css").toExternalForm());

        // Setup stage
        currentScene = levelScene;

        mainStage = stage;
        mainStage.setTitle("EG Top Window Title.");
        mainStage.setScene(levelScene);
        mainStage.setFullScreen(true);
        mainStage.show();

        // Store reference to already loaded parent
        levelParent = mainViewPane;

        // Load shop scene
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("shop-view-test.fxml"));
        shopParent = shopLoader.load();
        shopController = shopLoader.getController();

        // inject after getting controller
        if (shopController != null) {
            shopController.injectApplication(this);
        }

        currentController = levelController;
    }

    private ShopController shopController;

    private MainController levelController;

    public void ChangeToShop()
    {
        this.currentScene.setRoot(shopParent);
        this.shopController.RefreshShop();
        currentController = shopController;
    }

    public void ChangeToLevelScene()
    {
        this.currentScene.setRoot(levelParent);
        this.levelController.StartNewLevel();
        currentController = levelController;
    }

    public static void main(String[] args) {
        launch();
    }
}