package com.example.wordsolve;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class TileSlotRowHBox extends HBox
{
    private int numberOfSlots;

    public TileSlotRowHBox()
    { }

    /// Remove all letter tiles. Leave same number of blank slots.
    public void clearTiles()
    {
        this.getChildren().clear();
        this.initialiseSlots(this.numberOfSlots);
    }

    public void initialiseSlots(int slotsToAdd)
    {
        numberOfSlots = slotsToAdd;
        for (int i = 0; i < slotsToAdd; i++)
        {
            var tileSlot = new EmptyTileSlot();
            this.getChildren().add(tileSlot);
        }
    }

    /// Gets the letter tiles submitted.
    public List<Tile> GetTiles()
    {
        var tiles = new ArrayList<Tile>();

        for (var child : this.getChildren())
        {
            if (child instanceof Tile tile)
            {
                tiles.add(tile);
            }
        }

        return tiles;
    }

    /// Gets curernt word in lowercase.
    public String getCurrentWord()
    {
        var word = new StringBuilder();

        for (var child : this.getChildren()){
            if (child instanceof Tile childAsTile)
            {
                word.append(childAsTile.getLetter());
            }
        }

        return word.toString().toLowerCase();
    }

    public boolean HasSpaceForAnotherLetter()
    {
        var numberOfLetterTiles = 0;

        for (var child : this.getChildren()){
            if (child instanceof Tile)
            {
                numberOfLetterTiles++;
            }
        }

        return numberOfLetterTiles < this.numberOfSlots;
    }

    public boolean IsTileAlreadyAdded(Tile tile)
    {
        for (var child : this.getChildren())
        {
            if (child == tile){
                return true;
            }
        }

        return false;
    }

    public void RemoveTile(Tile tile)
    {
        for (var child : this.getChildren()){
            if (child == tile){
                this.getChildren().remove(tile);
                break;
            }
        }

        var emptySlot = new EmptyTileSlot();
        this.getChildren().add(emptySlot);
    }

    public void AddTile(Tile tile)
    {
        var index = 0;
        for (var child : this.getChildren())
        {
            if (child instanceof EmptyTileSlot)
            {
                break;
            }
            index++;
        }

        // add letter tile to left most empty spot
        this.getChildren().add(index, tile);
        this.getChildren().removeLast(); // remove empty slot at end
    }
}
