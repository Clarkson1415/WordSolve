package com.example.wordsolve;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ShopController
{
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
        WordSolveApplication.SwitchToLevelScene();
    }

    public static void RefreshShop()
    {
        System.out.println("Shop refreshed \n");
        System.out.printf("player money = %d", MainController.getPlayersMoney());
    }
}
