package com.example.wordsolve;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class InputController {

    @FXML
    private StackPane pane;  // match fx:id in your FXML

    private final VBox verticalBox = new VBox();

    private InputHBox inputBoxContainer;
    private LettersHBox LetterContainer;

    // How many tiles can the user pick from. This is only for initialising for now. TODO: be able to change tile number at runtime.
    private int tileNumber = 10;

    private void changeLetterTiles()
    {
        LetterContainer.CreateNewLetterTiles(tileNumber);
        inputBoxContainer.UpdateAllowedLetters(LetterContainer.getCurrentTiles());
    }

    /// Refresh letter tiles and empty typed inputs.
    private void resetTiles()
    {
        this.inputBoxContainer.clearLetters();
        this.changeLetterTiles();
    }

    private void resetRedraw()
    {
        redrawsRemaining = 3;
        drawsLeftLabel.setText(String.format("Redraws remaining %d / 3", redrawsRemaining));
    }

    private int redrawsRemaining = 3;
    private final Label drawsLeftLabel = new Label(String.format("Redraws remaining %d / 3", redrawsRemaining));

    private void OnLetterAdded(char newCode)
    {
        System.out.println("Updated input code: " + newCode);

        this.LetterContainer.DisableTile(newCode);
        var allowed = this.LetterContainer.getCurrentTiles();
        this.inputBoxContainer.UpdateAllowedLetters(allowed);
    }

    private void OnLetterRemoved(char letterRemoved)
    {
        System.out.println("letter removed called. in main controller.");
        this.LetterContainer.EnableTile(letterRemoved);
        var allowed = this.LetterContainer.getCurrentTiles();
        this.inputBoxContainer.UpdateAllowedLetters(allowed);
    }

    private void OnEnterPressed()
    {
        if (!this.inputBoxContainer.isWordComplete())
        {
            return;
        }

        System.out.println("Enter was pressed!");
        this.resetRedraw();
        this.resetTiles();
    }

    @FXML
    public void initialize()
    {
        pane.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                OnEnterPressed();
            }
        });

        verticalBox.setAlignment(Pos.CENTER);
        // space between children
        verticalBox.setSpacing(20);

        // Input textboxes
        inputBoxContainer = new InputHBox();
        verticalBox.getChildren().add(inputBoxContainer);

        inputBoxContainer.OnLetterAdded().addListener((obs, oldCode, letter) ->
        {
            if (letter == null || letter.isEmpty()){
                return;
            }

            OnLetterAdded(letter.charAt(0));
        });

        inputBoxContainer.OnLetterRemoved().addListener((obs, oldCode, letterRemoved) ->
        {
            if (letterRemoved == null || letterRemoved.isEmpty()){
                return;
            }

            OnLetterRemoved(letterRemoved.charAt(0));
        });

        // Letter tiles
        LetterContainer = new LettersHBox(tileNumber);
        verticalBox.getChildren().add(LetterContainer);
        changeLetterTiles();

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

        // Add redraw button and text.
        HBox redrawSection = new HBox();
        redrawSection.setSpacing(10);
        redrawSection.setAlignment(Pos.CENTER);

        // redraws remaining text.
        drawsLeftLabel.setFont(Font.font(15));
        redrawSection.getChildren().add(drawsLeftLabel);

        // Redraw button text.
        Button redrawButton = new Button("Redraw");
        redrawButton.setFont(Font.font(15));
        redrawButton.setOnAction(event ->
        {
            OnRedrawButtonClicked();
        });

        redrawSection.getChildren().add(redrawButton);

        // add redraw button and text to button vbox.
        bottomVBox.getChildren().add(redrawSection);

        // Add tutorial text
        Label tutorial = new Label("Enter to complete word. Then also get more redraws.");
        tutorial.setFont(Font.font(15));
        bottomVBox.getChildren().add(tutorial);

        bottomVBox.setMaxHeight(Region.USE_PREF_SIZE);
        pane.getChildren().add(bottomVBox);
    }

    private void OnRedrawButtonClicked()
    {
        if (redrawsRemaining == 0)
        {
            return;
        }

        changeLetterTiles();
        redrawsRemaining -= 1;

        drawsLeftLabel.setText(String.format("Redraws remaining %d / 3", redrawsRemaining));
    }
}
