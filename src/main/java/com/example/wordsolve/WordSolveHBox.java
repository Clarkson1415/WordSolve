package com.example.wordsolve;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public abstract class WordSolveHBox extends HBox
{
    protected static int WIDTH = 50;

    protected WordSolveHBox() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
    }
}