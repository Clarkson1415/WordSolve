package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class LettersHBox extends HBox
{
    private static final int NumberOfTextboxes = 5;
    private final Label[] fields = new Label[NumberOfTextboxes];

    /** Fills the lower viewing tiles with letters. **/
    public void UpdateWord(char[] letters)
    {
        for (int i = 0; i < letters.length; i++)
        {
            fields[i].setText(Character.toString(letters[i]));
        }
    }

    public LettersHBox() {
        setSpacing(10);
        setAlignment(Pos.CENTER);

        for (int i = 0; i < NumberOfTextboxes; i++) {
            Label label = new Label();
            label.setPrefWidth(40);
            label.setFont(Font.font(20));
            label.setAlignment(Pos.CENTER);
            fields[i] = label;
            getChildren().add(label);
        }
    }

    /** Clears all fields */
    public void clear() {
        for (Label label : fields) {
            label.setText("");
        }
        if (fields.length > 0) fields[0].requestFocus();
    }
}
