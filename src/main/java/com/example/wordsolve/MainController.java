package com.example.wordsolve;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class MainController {

    @FXML
    private StackPane pane;  // match fx:id in your FXML

    @FXML
    private Pane particleEffectLayer;

    @FXML
    /// Row for the players letter tiles in hand.
    private HBox tileRow;

    @FXML
    /// Row to hold selected tile.
    private TileSlotRowHBox rowToHoldSelectedTiles;

    @FXML
    private Button playButton;

    @FXML
    private Button redrawButton;

    @FXML
    private Label rawScoreText;

    @FXML
    private Label roundScoreText;

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

    private int roundScorePoints = 0;

    /// Will be the level score to beat to win level.
    private int scoreRequiredToWin = 0;

    @FXML
    private Label scoreToBeatText;

    @FXML
    public void initialize()
    {
        System.out.println("Initialsed");
        rowToHoldSelectedTiles.initialiseSlots(this.defaultHandTiles);
        setupLevel();

        particleEffectLayer = new Pane();
        this.pane.getChildren().add(particleEffectLayer);
        particleEffectLayer.setMouseTransparent(true);

        this.IncrementScoreToBeat(50);
    }

    private void OnTilePressed(Tile tilePressed)
    {
        // If tile in row of letters and clicked on again remove
        // else if tile is to be added check if space and then add.

        if (this.rowToHoldSelectedTiles.IsTileAlreadyAdded(tilePressed))
        {
            this.rowToHoldSelectedTiles.RemoveTile(tilePressed);
            this.tileRow.getChildren().add(tilePressed);
        }
        else if (this.rowToHoldSelectedTiles.HasSpaceForAnotherLetter())
        {
            this.tileRow.getChildren().remove(tilePressed);
            this.rowToHoldSelectedTiles.AddTile(tilePressed);
        }

        ParticleEffects.ShowDustEffect(tilePressed);

        System.out.println("current word = " + this.rowToHoldSelectedTiles.getCurrentWord());
        UpdatePlayButton();
        UpdateRedrawButton();
    }

    private boolean isWordValid()
    {
        // TODO implement SQL dictionary here.
        var isWordEmpty = this.rowToHoldSelectedTiles.getCurrentWord().isEmpty();
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
        var tilePopUpDuration = 0.2;
        SequentialTransition tilePopupTransitions = new SequentialTransition();

        for (var t : this.rowToHoldSelectedTiles.GetTiles())
        {
            // Create transition: move UP by 20px
            TranslateTransition tt = new TranslateTransition(Duration.seconds(tilePopUpDuration), t);
            tt.setByY(-20); // negative = up
            tt.setCycleCount(1);
            tt.setAutoReverse(false);

            // Add to sequential transition
            tilePopupTransitions.getChildren().add(tt);

            // wait for above tile animation to finish before the next one

            // TODO: +x score amount nice UI on the scorer thing like in balatro. Update score (can also hook into tt
            //  .setOnFinished if you want delay). Will do so ui is updated after each tile popup anim.
            // Update score count after the tile animation has finished.
            tt.setOnFinished(e -> AddToRawScorePoints(t.getTileScore()));
        }

        // time delay between tiles finished scoring and the jokers being scored so user has time to like take in
        // information i guess.
        var smallTimeDelay = new TranslateTransition(Duration.seconds(1));
        tilePopupTransitions.getChildren().add(smallTimeDelay);

        tilePopupTransitions.setOnFinished(e -> ScoreJokers());
        // Play all transitions in order
        tilePopupTransitions.play();
    }

    private void ScoreJokers()
    {
        SequentialTransition st = new SequentialTransition();

        // For ()
        // for each joker add to sequence animation then play animtion
        st.setOnFinished(e -> OnWordAndJokerScoringFinished());
        st.play();
    }

    /// Happens after letter tiles and jokers have been applied to the score. This will be the raw score getting
    /// added to the current Level score.
    private void OnWordAndJokerScoringFinished()
    {
        AddToRoundScore(this.rawScorePoints);
        this.rawScorePoints = 0;

        if (this.roundScorePoints > scoreRequiredToWin)
        {
            // Won
            System.out.println("Won");
            IncrementScoreToBeat(50);
            RefillHand();
            return;
        }

        if (this.wordPlaysRemaining == 0)
        {
            System.out.println("Lost");
            return;
        }

        // if not won or lost continue next turn.
        RefillHand();
    }

    private void IncrementScoreToBeat(int amount)
    {
        this.scoreRequiredToWin += amount;
        this.scoreToBeatText.setText(String.format("Score to beat %d", this.scoreRequiredToWin));
    }

    private void RefillHand()
    {
        this.drawNewTiles(this.rowToHoldSelectedTiles.getCurrentWord().length());
        this.rowToHoldSelectedTiles.clearTiles();
        UpdatePlayButton();
        UpdateRedrawButton();
    }


    private void CheckLevelConditions()
    {

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
            ParticleEffects.ShowDustEffect(tile);
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
        var redrawActive = this.redrawsRemaining > 0 && !this.rowToHoldSelectedTiles.getCurrentWord().isEmpty();
        this.redrawButton.setDisable(!redrawActive);
    }

    private void UpdatePlayButton()
    {
        this.playButton.setText(String.format("Play Word. %d remaining", this.wordPlaysRemaining));
        var playButtonActive = this.wordPlaysRemaining > 0 && !this.rowToHoldSelectedTiles.getCurrentWord().isEmpty();
        this.playButton.setDisable(!playButtonActive);
    }

    private int rawScorePoints = 0;

    private void AddToRawScorePoints(int add)
    {
        this.rawScorePoints += add;
        this.rawScoreText.setText(String.format("%d", this.rawScorePoints));
    }

    private void AddToRoundScore(int add)
    {
        this.roundScorePoints += add;
        this.roundScoreText.setText(String.format("Round Score: %d", this.roundScorePoints));

        // Reset the word play score back to 0 for the next turn.
        this.rawScorePoints = 0;
        this.rawScoreText.setText(String.format("%d", this.rawScorePoints));
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

        var numberOfTilesToRedraw = this.rowToHoldSelectedTiles.getCurrentWord().length();
        this.rowToHoldSelectedTiles.clearTiles();
        this.drawNewTiles(numberOfTilesToRedraw);
    }
}
