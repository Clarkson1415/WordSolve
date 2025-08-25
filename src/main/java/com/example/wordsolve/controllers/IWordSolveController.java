package com.example.wordsolve.controllers;

import com.example.wordsolve.WordSolveApplication;
import javafx.scene.layout.Pane;

public interface IWordSolveController
{
    public void injectApplication(WordSolveApplication app);

    public Pane getTooltipPane();
}
