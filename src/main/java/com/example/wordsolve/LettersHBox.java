package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.Random;

public class LettersHBox extends WordSolveHBox
{
    private Button[] tileButtons;

    public char[] getCurrentTiles()
    {
        char[] letterTiles = new char[tileButtons.length];

        for (int i = 0; i < tileButtons.length; i++)
        {
            if (tileButtons[i].isDisabled())
            {
                continue;
            }

            letterTiles[i] = tileButtons[i].getText().charAt(0);
        }

        return letterTiles;
    }

    private char[] generateLetterTiles(int tileNumber)
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] result = new char[tileNumber];
        Random random = new Random();

        for (int i = 0; i < tileNumber; i++) {
            int index = random.nextInt(alphabet.length());
            result[i] = alphabet.charAt(index);
        }

        return result;
    }

    /** Fills the lower viewing tiles with letters. **/
    public void CreateNewLetterTiles(int numberOfTiles)
    {
        char[] letters = generateLetterTiles(numberOfTiles);

        this.createTileButtons(numberOfTiles);

        for (int i = 0; i < letters.length; i++)
        {
            tileButtons[i].setText(Character.toString(letters[i]));
            tileButtons[i].setDisable(false);
        }
    }

    public void EnableTile(char letter)
    {
        // Find First letter matching
        for (Button b: this.tileButtons)
        {
            if ((b.getText().charAt(0) == letter) && (b.isDisabled()))
            {
                b.setDisable(false);
                return;
            }
        }
    }

    public void DisableTile(char letter)
    {
        // Find First letter matching
        for (Button b: this.tileButtons)
        {
            if ((b.getText().charAt(0) == letter) && !b.isDisabled())
            {
                b.setDisable(true);
                return;
            }
        }
    }

    public LettersHBox(int numberOfTiles)
    {
        super();

        this.createTileButtons(numberOfTiles);
    }

    private void createTileButtons(int number)
    {
        getChildren().clear();
        tileButtons = new Button[number];

        for (int i = 0; i < number; i++)
        {
            Button singleLetter = new Button();
            singleLetter.setPrefWidth(WIDTH);
            singleLetter.setFont(Font.font(20));
            singleLetter.setAlignment(Pos.CENTER);
            tileButtons[i] = singleLetter;
            getChildren().add(singleLetter);
        }
    }

}
