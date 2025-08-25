package com.example.wordsolve.controllers;

import com.example.wordsolve.GameState;
import com.example.wordsolve.ShopItem;
import com.example.wordsolve.WordSolveApplication;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class ShopController implements IWordSolveController
{
    WordSolveApplication application;

    @FXML
    private Label moneyLabel;

    @FXML
    /// Container to display active items/upgrades
    private FlowPane boonsContainer;

    @FXML
    private StackPane pane;

    @FXML
    private Pane tooltipsPane;

    @FXML
    private Button nextLevelButton;

    private GameState globalGameState;

    @Override
    public Pane getTooltipPane()
    {
        return this.tooltipsPane;
    }

    @FXML
    /// Called once in applications runtime. when the page is FXML.load
    public void initialize()
    {
        globalGameState = GameState.getInstance();

        // Bind the money label to the shared model
        moneyLabel.textProperty().bind(this.globalGameState.getBindableString());

        // Listen for changes to player's items and update UI reactively
        globalGameState.getPlayersItems().addListener((ListChangeListener<ShopItem>) change -> {
            globalGameState.updateItemsUI(this.boonsContainer); // Always refresh entire UI - simpler and more reliable
        });

        // Trigger initial UI update
        globalGameState.updateItemsUI(this.boonsContainer);

        tooltipsPane.prefWidthProperty().bind(pane.widthProperty());
        tooltipsPane.prefHeightProperty().bind(pane.heightProperty());
    }

    public void OnNextLevelClick()
    {
        System.out.println("change to level.");
        this.application.ChangeToLevelScene();
    }

    public void RefreshShop()
    {
        System.out.println("Shop refreshed \n");
        System.out.printf("player money = %d", this.globalGameState.getMoney());
    }

    /// Event when item clicked on
    public void BuyItem()
    {
        // TODO update the shop items.
        // Add shop items to the top bar.

        // TODO: top upgrade item bar and the shop money are the same in both views. How can i make sure they are the
        //  same thing? Shared Model Class that both controllers observe:
    }

    @Override
    public void injectApplication(WordSolveApplication app)
    {
        this.application = app;
    }
}
