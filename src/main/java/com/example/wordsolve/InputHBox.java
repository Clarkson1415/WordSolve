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

    private final StringProperty currentTypedWord = new SimpleStringProperty("");

    public StringProperty getCurrentTypedWordProperty()
    {
        return currentTypedWord;
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
        TextField emptyField = null;
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
            if (newVal.isEmpty()){
                return;
            }

            // If a value was pasted that was > 1 trim characters
            if (newVal.length() > 1) {
                tf.setText(newVal.substring(0, 1));
            }

            // If value is not valid empty checkbox
            if (!IsCharacterAllowed(newVal.charAt(0)))
            {
                System.out.println("INVALID LETTER.");
                tf.setText("");
                return;
            }

            // If value is valid we should remove letter tile and move to the next.
            // TODO:
            System.out.println("todo remove tile " + newVal);
            currentTypedWord.set(getAllCurrentTypedLetters());

            // If the textbox has been updated and the next textbox exists move cursor
            if (!newVal.isEmpty() && textBoxCounter < NumberOfTextboxes - 1)
            {
                fields[textBoxCounter + 1].requestFocus();
            }
        });

        // Handle backspace.
        tf.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.BACK_SPACE){
                return;
            }

            if (tf.getText().isEmpty() && textBoxCounter > 0){
                fields[textBoxCounter - 1].requestFocus();
                fields[textBoxCounter - 1].clear();
                event.consume();
            }
        });

        fields[textBoxCounter] = tf;
        getChildren().add(tf);
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
