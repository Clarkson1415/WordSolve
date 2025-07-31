package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.Random;

public class LettersHBox extends WordSolveHBox
{
    private static final int NumberOfLetters = 5;
    private final Button[] fields = new Button[NumberOfLetters];

    public char[] getCurrentTiles()
    {
        char[] letterTiles = new char[fields.length];

        for (int i = 0; i < fields.length; i++)
        {
            if (fields[i].isDisabled())
            {
                continue;
            }

            letterTiles[i] = fields[i].getText().charAt(0);
        }

        return letterTiles;
    }

    private char[] generateLetterTiles()
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] result = new char[5];
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(alphabet.length());
            result[i] = alphabet.charAt(index);
        }

        return result;
    }

    /** Fills the lower viewing tiles with letters. **/
    public void CreateNewLetterTiles()
    {
        char[] letters = generateLetterTiles();

        for (int i = 0; i < letters.length; i++)
        {
            fields[i].setText(Character.toString(letters[i]));
            fields[i].setDisable(false);
        }
    }

    public void RemoveTile(char letter)
    {
        // Find First letter matching
        for (Button b: this.fields)
        {
            if (b.getText().charAt(0) == letter)
            {
                b.setDisable(true);
                return;
            }
        }
    }

    public LettersHBox()
    {
        super();

        // TODO: this is the same in both InputHBox and LettersHBox so put in super.
        for (int i = 0; i < NumberOfLetters; i++) {
            Button singleLetter = new Button();
            singleLetter.setPrefWidth(WIDTH);
            singleLetter.setFont(Font.font(20));
            singleLetter.setAlignment(Pos.CENTER);
            fields[i] = singleLetter;
            getChildren().add(singleLetter);
        }
    }
}
