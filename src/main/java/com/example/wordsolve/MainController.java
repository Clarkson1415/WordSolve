package com.example.wordsolve;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MainController {

    @FXML
    private StackPane pane;  // match fx:id in your FXML

    private final VBox verticalBox = new VBox();

    private InputHBox inputBoxContainer;
    private LettersHBox LetterContainer;
    private char[] currentLetterTiles;

    private void updateWord()
    {
        LetterContainer.UpdateWord(currentLetterTiles);
    }

    @FXML
    public void initialize()
    {
        verticalBox.setAlignment(Pos.CENTER);
        // space between children
        verticalBox.setSpacing(20);

        // Input textboxes
        inputBoxContainer = new InputHBox();
        verticalBox.getChildren().add(inputBoxContainer);

        // Letter tiles
        LetterContainer = new LettersHBox();
        verticalBox.getChildren().add(LetterContainer);
        currentLetterTiles = generateLetterTiles();
        updateWord();

        // Add to main window pane layout
        pane.getChildren().add(verticalBox);

        // Timer top right
        Label timerLabel = new Label("0s");
        timerLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5;");

        // Anchor to top and right
        StackPane.setAlignment(timerLabel, Pos.TOP_RIGHT);
        StackPane.setMargin(timerLabel, new Insets(10));
        pane.getChildren().add(timerLabel);

        VBox bottomVBox = new VBox();

        bottomVBox.setSpacing(5);

        // aligns VBox children
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setAlignment(bottomVBox, Pos.BOTTOM_CENTER); // aligns VBox itself in StackPane

        StackPane.setMargin(bottomVBox, new Insets(10));

        // Add redraw button
        Button redraw = new Button("Redraw");
        redraw.setFont(Font.font(15));
        bottomVBox.getChildren().add(redraw);

        // Add tutorial text
        Label tutorial = new Label("Enter to complete word.");
        tutorial.setFont(Font.font(15));
        bottomVBox.getChildren().add(tutorial);

        bottomVBox.setMaxHeight(Region.USE_PREF_SIZE);
        pane.getChildren().add(bottomVBox);
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
