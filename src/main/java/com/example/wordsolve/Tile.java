package com.example.wordsolve;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/// Letter tiles.
public class Tile extends StackPane
{
    private static final double TILE_SIZE = 50; // width & height
    private final Label letterLabel;

    // base letter score
    private final Label scoreLabel;

    public double getTileSize()
    {
        return this.TILE_SIZE;
    }

    public Tile(char letter)
    {
        // Layout stuff. I would prefer to put this in css or directly in the fxml.
        letterLabel = new Label();
        letterLabel.getStyleClass().add("tile-letter-label"); // CSS for label
        getChildren().add(letterLabel);

        getStyleClass().add("tile"); // CSS for tile background
        setPrefSize(TILE_SIZE, TILE_SIZE);

        scoreLabel = new Label();
        // tile-score-label
        scoreLabel.getStyleClass().add("tile-score-label");
        getChildren().add(scoreLabel);

        this.setLetter(letter);

        // put score in the bottom left.
        StackPane.setAlignment(scoreLabel, Pos.BOTTOM_LEFT);
        StackPane.setMargin(scoreLabel, new Insets(0, 0, 3, 3)); // top, right, bottom, left
    }

    private int tileScore = 0;

    /// If diff tile has diff type etc.
    private int tileScoreMod = 0;

    public void setLetter(char letter)
    {
        letterLabel.setText(Character.toString(letter));
        var baseScore = LetterScores.getLetterScore(letterLabel.getText());
        tileScore = baseScore + tileScoreMod;
        this.scoreLabel.setText(String.format("%d", tileScore));
    }

    public String getLetter()
    {
        return this.letterLabel.getText();
    }

    public int getTileScore()
    {
        return tileScore;
    }
}
