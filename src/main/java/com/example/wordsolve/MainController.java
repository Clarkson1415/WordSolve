package com.example.wordsolve;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private VBox verticalBox;

    private InputHBox inputBoxContainer;
    private LettersHBox LetterContainer;
    private char[] currentLetterTiles;

    private void updateWord()
    {
        LetterContainer.UpdateWord(currentLetterTiles);
    }

    @FXML
    public void initialize() {
        inputBoxContainer = new InputHBox();
        verticalBox.getChildren().add(inputBoxContainer);
        LetterContainer = new LettersHBox();
        verticalBox.getChildren().add(LetterContainer);
        currentLetterTiles = generateLetterTiles();
        updateWord();
    }

    private char[] generateLetterTiles()
    {
        return "htdiw".toCharArray();
    }

    // Get the whole word typed.
    public String getCode() {
        return inputBoxContainer.getCode();
    }
}
