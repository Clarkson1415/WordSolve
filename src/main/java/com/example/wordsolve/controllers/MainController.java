package com.example.wordsolve.controllers;

import com.example.wordsolve.*;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import javax.swing.plaf.DimensionUIResource;
import java.util.ArrayList;
import java.util.Random;

/// Single class that controls the level view.
public class MainController implements IWordSolveController
{
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
    private Label handScoreText;

    @FXML
    private Label handScoreMultiplierText;

    @FXML
    private Label roundScoreText;

    // How many tiles the user has in their hand at the start of a round.
    private int tileNumber = 10;

    /// Tiles in the players hand.
    private ArrayList<Tile> tiles = new ArrayList<>();

    /// Redraws remaining in this level
    private static int redrawsRemaining = 4;

    /// Redraws the play starts with at the start of a run. This never changes.
    private final int defaultRedrawAmount = 4;

    /// Redraws added or - if a shop items is bought.
    private int redrawModifier;

    /// Word plays remaining in this level
    private static int wordPlaysRemaining = 4;

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

    /// Scored from this hand.
    private int handScorePoints = 0;

    /// Score multiplier for the current hand score being scored.
    private int handScoreMultiplier = 1;

    /// Scored within the round.
    private int roundScorePoints = 0;

    /// Will be the level score to beat to win level.
    private static int scoreRequiredToWin = 0;

    @FXML
    private Label scoreToBeatText;

    private static DatabaseConnection database;

    private GameState globalGameState;

    @Override
    public Pane getTooltipPane()
    {
        return this.tooltipsPane;
    }

    @FXML
    public void initialize()
    {
        // TODO: will this be called again if the scene root changes back to this page? NO it will not run aagin.
        // Only if reload.
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        // Parent levelParent = loader.load();  // new object

        System.out.println("Initialsed");
        rowToHoldSelectedTiles.initialiseSlots(this.defaultHandTiles);
        setupLevel();

        this.IncrementScoreToBeat(2);

        // https://github.com/CloudBytes-Academy/English-Dictionary-Open-Source?
        database = new DatabaseConnection();

        try
        {
            database.testConnection();
        }
        catch (Exception e)
        {
            System.out.println("issue with db");
        }

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

    private void OnTilePressed(Tile tilePressed)
    {
        // If tile in row of letters and clicked on again remove
        // else if tile is to be added check if space and then add.
        if (rowToHoldSelectedTiles.IsTileAlreadyAdded(tilePressed))
        {
            rowToHoldSelectedTiles.RemoveTile(tilePressed);
            tileRow.getChildren().add(tilePressed);
        }
        else if (rowToHoldSelectedTiles.HasSpaceForAnotherLetter())
        {
            tileRow.getChildren().remove(tilePressed);
            rowToHoldSelectedTiles.AddTile(tilePressed);
        }

        ParticleEffects.ShowDustEffect(tilePressed);

        System.out.println("current word = " + rowToHoldSelectedTiles.getCurrentWord());
        UpdatePlayButton();
        UpdateRedrawButton();
    }

    private boolean isWordValid()
    {
        var isWordInDictionary = false;

        // TODO cleanup. also add a little UI to show errors if found.
        try
        {
            isWordInDictionary = database.CheckWordExists(rowToHoldSelectedTiles.getCurrentWord());
        }
        catch (Exception e)
        {
            System.out.println("issue with database");
        }

        if (isWordInDictionary)
        {
            try
            {
                var definition = database.GetWordDefinition(rowToHoldSelectedTiles.getCurrentWord());
                System.out.println(definition);
            }
            catch (Exception e){
                System.out.println("unable to def def");
            }
        }

        var isWordEmpty = rowToHoldSelectedTiles.getCurrentWord().isEmpty();
        return !isWordEmpty && isWordInDictionary;
    }

    @FXML
    private void OnClickPlayWord()
    {
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
            tt.setOnFinished(e -> addToHandScorePoints(t.getTileScore()));
        }

        // time delay between tiles finished scoring and the jokers being scored so user has time to like take in
        // information i guess.
        var smallTimeDelay = new TranslateTransition(Duration.seconds(1));
        tilePopupTransitions.getChildren().add(smallTimeDelay);

        tilePopupTransitions.setOnFinished(e -> ScorePlayersUpgrades());
        // Play all transitions in order
        tilePopupTransitions.play();
    }

    /// Adds any special multipliers from the players items to the score.
    private void ScorePlayersUpgrades()
    {
        SequentialTransition st = new SequentialTransition();

        for (var item: this.globalGameState.getPlayersItems())
        {
            var newTransition = new TranslateTransition(Duration.seconds(1));

            /// TODO add transition for animating the joker cards
            System.out.printf("player item: %s ... wait for transition (todo)", item.getName());
            this.addToHandScoreMultiplier(item.getMultiplier());

            st.getChildren().add(newTransition);
        }

        st.setOnFinished(e -> OnAllScoringFinished());
        st.play();
    }

    /// Called when shop next level button has been clicked and the level scene is now active.
    public void StartNewLevel()
    {
        IncrementScoreToBeat(2);
        RefillHand();
    }

    /// Happens after letter tiles and jokers have been applied to the score. Addss the raw word score to the round
    /// score.
    private void OnAllScoringFinished()
    {
        addToRoundScore(this.handScorePoints * this.handScoreMultiplier);

        if (this.roundScorePoints >= scoreRequiredToWin)
        {
            // Won
            System.out.println("Won");

            // TODO: do we want to increase money the same way as balatro.
            this.globalGameState.addMoney(this.wordPlaysRemaining);
            System.out.printf("increased money by word plays remaining +%d", this.wordPlaysRemaining);

            this.application.ChangeToShop();

            System.out.printf("(TESTING) added shop item double trouble. should appear on screen text.");
            this.globalGameState.addItem(this.boonsContainer, ShopItems.getItemList().getFirst());

            return;
        }

        if (this.wordPlaysRemaining == 0)
        {
            System.out.println("Lost");
            return;
        }

        // if not won or lost continue next turn.
        System.out.println("continue");
        RefillHand();
    }

    private void IncrementScoreToBeat(int amount)
    {
        scoreRequiredToWin += amount;
        scoreToBeatText.setText(String.format("Score to beat %d", scoreRequiredToWin));
    }

    private void RefillHand()
    {
        drawNewTiles(rowToHoldSelectedTiles.getCurrentWord().length());
        rowToHoldSelectedTiles.clearTiles();
        UpdatePlayButton();
        UpdateRedrawButton();
    }

    private static final Random random = new Random();

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
        this.roundScorePoints = 0;
        this.addToRoundScore(0);
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
        var redrawActive = redrawsRemaining > 0 && !rowToHoldSelectedTiles.getCurrentWord().isEmpty();
        redrawButton.setDisable(!redrawActive);
    }

    private void UpdatePlayButton()
    {
        playButton.setText(String.format("Play Word. %d remaining", wordPlaysRemaining));
        var playButtonActive = wordPlaysRemaining > 0 && !rowToHoldSelectedTiles.getCurrentWord().isEmpty();
        playButtonActive = playButtonActive && isWordValid();
        playButton.setDisable(!playButtonActive);
    }

    private void addToHandScorePoints(int add)
    {
        this.handScorePoints += add;
        this.handScoreText.setText(String.format("%d", this.handScorePoints));
    }

    private void addToHandScoreMultiplier(int add)
    {
        this.handScoreMultiplier += add;
        this.handScoreMultiplierText.setText(String.format("%d", this.handScoreMultiplier));
    }

    private void addToRoundScore(int add)
    {
        this.roundScorePoints += add;
        this.roundScoreText.setText(String.format("Round Score: %d", this.roundScorePoints));

        // Reset the word play score back to 0 for the next turn.
        this.handScorePoints = 0;
        this.handScoreText.setText(String.format("%d", this.handScorePoints));

        this.handScoreMultiplier = 1;
        this.handScoreMultiplierText.setText(String.format("%d", this.handScoreMultiplier));
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

    WordSolveApplication application;

    @Override
    public void injectApplication(WordSolveApplication app)
    {
        application = app;
    }
}
