package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


/**
 * Custom HBox extension class to encapsulate all the little textboxes.
 * ...**/
public class InputHBox extends HBox
{
    private static final int NumberOfTextboxes = 5;
    private final TextField[] fields = new TextField[NumberOfTextboxes];

    public InputHBox() {
        setSpacing(10);
        setAlignment(Pos.CENTER);

        for (int i = 0; i < NumberOfTextboxes; i++) {
            TextField tf = new TextField();
            tf.setPrefWidth(40);
            tf.setFont(Font.font(20));
            tf.setAlignment(Pos.CENTER);

            final int textBoxCounter = i;

            // Limit input length to 1 and add listener to detect when should auto-move right
            tf.textProperty().addListener((obs, oldVal, newVal) -> {
                // If a value was pasted that was > 1 trim characters
                if (newVal.length() > 1) {
                    tf.setText(newVal.substring(0, 1));
                }
                // If the textbox has been updated and the next textbox exists move cursor
                if (!newVal.isEmpty() && textBoxCounter < NumberOfTextboxes - 1) {
                    fields[textBoxCounter + 1].requestFocus();
                }
            });

            // Handle backspace, left, right keys
            tf.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (tf.getText().isEmpty() && textBoxCounter > 0) {
                        fields[textBoxCounter - 1].requestFocus();
                        fields[textBoxCounter - 1].clear();
                        event.consume();
                    }
                } else if (event.getCode() == KeyCode.LEFT) {
                    if (textBoxCounter > 0) {
                        fields[textBoxCounter - 1].requestFocus();
                        event.consume();
                    }
                } else if (event.getCode() == KeyCode.RIGHT) {
                    if (textBoxCounter < NumberOfTextboxes - 1) {
                        fields[textBoxCounter + 1].requestFocus();
                        event.consume();
                    }
                }
            });

            fields[i] = tf;
            getChildren().add(tf);
        }
    }

    /** Returns the combined code as a String */
    public String getCode() {
        StringBuilder sb = new StringBuilder();
        for (TextField tf : fields) {
            sb.append(tf.getText());
        }
        return sb.toString();
    }

    /** Clears all fields */
    public void clear() {
        for (TextField tf : fields) {
            tf.clear();
        }
        if (fields.length > 0) fields[0].requestFocus();
    }
}
