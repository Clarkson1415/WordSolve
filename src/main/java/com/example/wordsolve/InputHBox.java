package com.example.wordsolve;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Collections;

/**
 * Custom HBox extension class to encapsulate all the little textboxes.
 * ...**/
public class InputHBox extends WordSolveHBox
{
    private static final int NumberOfTextboxes = 5;
    private final TextField[] fields = new TextField[NumberOfTextboxes];
    private char[] allowedLetters;

    private String previousLetters;

    private StringProperty letterRemoved = new SimpleStringProperty("");
    private StringProperty letterAdded = new SimpleStringProperty("");

    public StringProperty OnLetterRemoved()
    {
        return letterRemoved;
    }

    public StringProperty OnLetterAdded()
    {
        return letterAdded;
    }

    /** Returns the combined code as a String */
    private String getAllCurrentTypedLetters() {
        StringBuilder sb = new StringBuilder();
        for (TextField tf : fields) {
            sb.append(tf.getText());
        }

        return sb.toString();
    }

    public void UpdateAllowedLetters(char[] newAllowedLetters)
    {
        allowedLetters = newAllowedLetters;
    }

    public boolean isWordComplete()
    {
        boolean isWordComplete = true;

        for (TextField f: fields){
            if (f.getText().isEmpty()){
                isWordComplete = false;
            }
        }

        return isWordComplete;
    }

    public void clearLetters()
    {
        for (TextField text: fields)
        {
            text.setText("");
        }

        fields[0].requestFocus();
    }

    public InputHBox() {
        super();

        for (int i = 0; i < NumberOfTextboxes; i++)
        {
            initialiseTextboxSlot(i);
        }
    }

    private TextField getLeftMostEmptySlot()
    {
        TextField emptyField = fields[fields.length - 1];
        for (TextField field: fields)
        {
            if (field.getText().isEmpty())
            {
                emptyField = field;
                break;
            }
        }

        return emptyField;
    }

    private void initialiseTextboxSlot(int textBoxCounter)
    {
        TextField tf = new TextField();

        // If user clicks in a textbox should put the cursor in the leftmost one.
        tf.setOnMouseClicked(e ->
        {
            getLeftMostEmptySlot().requestFocus();
        });

        tf.setPrefWidth(WIDTH);
        tf.setFont(Font.font(20));
        tf.setAlignment(Pos.CENTER);

        // Limit input length to 1 and move 1 right when value is input.
        tf.textProperty().addListener((obs, oldVal, newVal) ->
        {
            OnTextBoxChanged(tf, newVal, textBoxCounter);
        });

        // Handle backspace.
        tf.setOnKeyPressed(event ->
        {
            if (event.getCode() != KeyCode.BACK_SPACE){
                return;
            }

            onBackspace(textBoxCounter);
            event.consume();
        });

        fields[textBoxCounter] = tf;
        getChildren().add(tf);
    }

    private void OnTextBoxChanged(TextField tf, String newVal, int textBoxCounter)
    {
        if (newVal.isEmpty()){
            return;
        }

        var character = newVal.charAt(newVal.length() - 1);

        // If value is not valid empty checkbox
        if (textBoxCounter < NumberOfTextboxes - 1)
        {
            if (!IsCharacterAllowed(character))
            {
                System.out.println("INVALID LETTER.");
                tf.setText("");
                return;
            }

            letterAdded.set(null); // reset first so the event fires.
            letterAdded.set(tf.getText());
        }
        else
        {
            if (!IsCharacterAllowed(character))
            {
                System.out.println("INVALID LETTER.");

                // If letter already in the last textbox set just single char. The same one.
                tf.setText(String.valueOf(tf.getText().charAt(0)));

                return;
            }

            tf.setText(String.valueOf(newVal.charAt(0)));
            letterAdded.set(null); // reset first so the event fires.
            letterAdded.set(tf.getText());
        }

        // If the textbox has been updated and the next textbox exists move cursor
        if (textBoxCounter < NumberOfTextboxes - 1)
        {
            fields[textBoxCounter + 1].requestFocus();
        }

        previousLetters = getAllCurrentTypedLetters();
    }

    private void onBackspace(int textBoxCounter)
    {
        var lastTextBox = fields.length - 1;
        if (textBoxCounter == lastTextBox)
        {
            if (previousLetters.length() == fields.length - 1)
            {
                TextField prev = fields[textBoxCounter - 1];
                prev.clear();
                prev.requestFocus();
            }
        }
        else if (textBoxCounter > 0)
        {
            TextField prev = fields[textBoxCounter - 1];
            prev.clear();
            prev.requestFocus();

        }

        letterRemoved.set(null);
        if (!previousLetters.isEmpty()) {
            letterRemoved.set(previousLetters.substring(previousLetters.length() - 1));
        }

        previousLetters = getAllCurrentTypedLetters();
    }

    private boolean IsCharacterAllowed(char newChar)
    {
        boolean isInArray = false;

        for (Character c: allowedLetters){
            if (newChar == c)
            {
                isInArray = true;
                break;
            }
        }

        return isInArray;
    }
}
