package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class LettersHBox extends WordSolveHBox
{
    private static final int NumberOfLetters = 5;
    private final Button[] fields = new Button[NumberOfLetters];

    /** Fills the lower viewing tiles with letters. **/
    public void UpdateWord(char[] letters)
    {
        for (int i = 0; i < letters.length; i++)
        {
            fields[i].setText(Character.toString(letters[i]));
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
