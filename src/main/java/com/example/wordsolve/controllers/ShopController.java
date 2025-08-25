package com.example.wordsolve.controllers;

import com.example.wordsolve.WordSolveApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ShopController implements IWordSolveController
{
    WordSolveApplication application;

    @FXML
    private Button nextLevelButton;

    @FXML
    /// Called once in applications runtime. when the page is FXML.load
    public void initialize()
    {

    }

    public void OnNextLevelClick()
    {
        System.out.println("change to level.");
        this.application.ChangeToLevelScene();
    }

    public void RefreshShop()
    {
        System.out.println("Shop refreshed \n");
        System.out.printf("player money = %d", MainController.getPlayersMoney());
        updateMoneyLabel();
    }

    @FXML
    private Label moneyLabel;

    private void updateMoneyLabel()
    {
        moneyLabel.setText(String.format("Money: %d $", MainController.getPlayersMoney()));
    }

    @Override
    public void injectApplication(WordSolveApplication app)
    {
        this.application = app;
    }
}
