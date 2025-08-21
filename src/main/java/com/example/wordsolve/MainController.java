package com.example.wordsolve;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Random;

public class MainController {

    @FXML
    private StackPane pane;  // match fx:id in your FXML

    @FXML
    /// Row for the players letter tiles in hand.
    private HBox tileRow;

    @FXML
    /// Row for the empty slots which the tiles will fill.
    private TileSlotRowHBox rowOfEmptySlotsToFill;

    @FXML
    private Button playButton;

    @FXML
    private Button redrawButton;

    @FXML
    private Label currentScoreTest;

    // How many tiles the user has in their hand at the start of a round.
    private int tileNumber = 10;

    /// Tiles in the players hand.
    private ArrayList<Tile> tiles = new ArrayList<>();

    /// Redraws remaining in this level
    private int redrawsRemaining = 4;

    /// Redraws the play starts with at the start of a run. This never changes.
    private final int defaultRedrawAmount = 4;

    /// Redraws added or - if a shop items is bought.
    private int redrawModifier;

    /// Word plays remaining in this level
    private int wordPlaysRemaining = 4;

    /// Redraws the play starts with at the start of a run. This never changes.
    private final int defaultWordPlays = 4;

    /// word plays modifier.
    private int wordPlaysModifier;

    /// Number of empty tile slots. Defines how many maximum letters in a word the player can play.
    private final int defaultEmptyLetterSlots = 10;

    /// Number of tiles initialised in the players hand.
    private final int defaultHandTiles = 9;

    /// Number of tiles that the 'hand' is increased by.
    private int handTilesModifier = 0;

    @FXML
    public void initialize()
    {
        System.out.println("Initialsed");

        rowOfEmptySlotsToFill.initialiseSlots(this.defaultHandTiles);
        setupLevel();
    }

    private void OnTilePressed(Tile tilePressed)
    {
        // If tile in row of letters and clicked on again remove
        // else if tile is to be added check if space and then add.
        if (this.rowOfEmptySlotsToFill.IsTileAlreadyAdded(tilePressed))
        {
            this.rowOfEmptySlotsToFill.RemoveTile(tilePressed);
            this.tileRow.getChildren().add(tilePressed);
        }
        else if (this.rowOfEmptySlotsToFill.HasSpaceForAnotherLetter())
        {
            this.tileRow.getChildren().remove(tilePressed);
            this.rowOfEmptySlotsToFill.AddTile(tilePressed);
        }

        System.out.println("current word = " + this.rowOfEmptySlotsToFill.getCurrentWord());
        UpdatePlayButton();
        UpdateRedrawButton();
    }

    private boolean isWordValid()
    {
        // TODO implement SQL dictionary here.
        var isWordEmpty = this.rowOfEmptySlotsToFill.getCurrentWord().isEmpty();
        return !isWordEmpty;
    }

    @FXML
    private void OnClickPlayWord()
    {
        if (!isWordValid()){
            System.out.println("TODO visual illegal word feedback");
            return;
        }

        wordPlaysRemaining--;

        // TODO: when have 1 play remaining wait for that play to finish. Then if the player lost then it's game over. State Machine with time for animations.

        var score = 0;
        for (var t : this.rowOfEmptySlotsToFill.GetTiles())
        {
            score += t.getTileScore();
        }

        UpdateScore(score);

        this.drawNewTiles(this.rowOfEmptySlotsToFill.getCurrentWord().length());
        this.rowOfEmptySlotsToFill.clearTiles();
        UpdatePlayButton();
        UpdateRedrawButton();
    }

    private final Random random = new Random();

    private void drawNewTiles(int numNewTiles)
    {
        for (var i = 0; i < numNewTiles; i++)
        {
            // Generate a random int between 0 and 25
            int index = random.nextInt(26);

            // Convert to letter (A–Z)
            char randomChar = (char) ('A' + index);

            Tile tile = new Tile(randomChar);
            tileRow.getChildren().add(tile);

            tile.setOnMouseClicked(e -> OnTilePressed(tile));
        }
    }

    /// Called on every new level loaded.
    private void setupLevel()
    {
        drawNewTiles(this.defaultHandTiles + this.handTilesModifier);

        // reset redraws
        redrawsRemaining = this.defaultRedrawAmount + this.redrawModifier;
        UpdateRedrawButton();

        // reset hand plays
        this.wordPlaysRemaining = this.defaultWordPlays + this.wordPlaysModifier;
        UpdatePlayButton();
    }

    private void UpdateRedrawButton()
    {
        redrawButton.setText(String.format("Redraws remaining %d", redrawsRemaining));
        var redrawActive = this.redrawsRemaining > 0 && !this.rowOfEmptySlotsToFill.getCurrentWord().isEmpty();
        this.redrawButton.setDisable(!redrawActive);
    }

    private void UpdatePlayButton()
    {
        this.playButton.setText(String.format("Play Word. %d remaining", this.wordPlaysRemaining));
        var playButtonActive = this.wordPlaysRemaining > 0 && !this.rowOfEmptySlotsToFill.getCurrentWord().isEmpty();
        this.playButton.setDisable(!playButtonActive);
    }

    private int currentScore = 0;

    private void UpdateScore(int add)
    {
        this.currentScore += add;
        this.currentScoreTest.setText(String.format("Score = %d", this.currentScore));
    }

    @FXML
    private void OnRedrawButtonClicked()
    {
        if (redrawsRemaining == 0)
        {
            return;
        }

        redrawsRemaining -= 1;
        UpdateRedrawButton();

        var numberOfTilesToRedraw = this.rowOfEmptySlotsToFill.getCurrentWord().length();
        this.rowOfEmptySlotsToFill.clearTiles();
        this.drawNewTiles(numberOfTilesToRedraw);
    }
}
