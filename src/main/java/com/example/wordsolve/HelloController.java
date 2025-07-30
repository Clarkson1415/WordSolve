package com.example.wordsolve;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    private String getRandomNumber()
    {
        int num = (int)(Math.random() * 100);
        return Integer.toString(num);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("You Got: " + getRandomNumber());
    }
}