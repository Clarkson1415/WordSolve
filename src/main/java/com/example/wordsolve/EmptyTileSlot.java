package com.example.wordsolve;

import javafx.scene.layout.Region;

/// For the game slots for a player to make a word in.
public class EmptyTileSlot extends Region
{
    private static final double TILE_SIZE = 50; // width & height

    public EmptyTileSlot()
    {
        // Layout stuff. I would prefer to put this in css or directly in the fxml.
        getStyleClass().add("empty-tile-slot");
        setPrefSize(TILE_SIZE, TILE_SIZE);
    }
}
