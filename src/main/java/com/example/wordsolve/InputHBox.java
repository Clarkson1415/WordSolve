package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

/**
 * Custom HBox extension class to encapsulate all the little textboxes.
 * ...**/
public class InputHBox extends WordSolveHBox
{
    private static final int NumberOfTextboxes = 5;
    private final TextField[] fields = new TextField[NumberOfTextboxes];

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

    /** Returns the combined code as a String */
    public String getCode() {
        StringBuilder sb = new StringBuilder();
        for (TextField tf : fields) {
            sb.append(tf.getText());
        }
        return sb.toString();
    }
}
