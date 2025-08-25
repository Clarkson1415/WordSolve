package com.example.wordsolve;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.stage.PopupWindow;

public class ShopItem
{
    private int price;

    private String itemName;

    /// How much multiplier to add to the score multiplier for the current hand being scored.
    private int multiplier;

    /// Points added to score before scoring this hand when ability triggered.
    private int extraPoints;

    public ShopItem(String name, int price, int extraPoints, int additionalMultiplier)
    {
        this.price = price;
        this.itemName = name;
        this.extraPoints = extraPoints;
        this.multiplier = additionalMultiplier;
    }

    public String getName()
    {
        return itemName;
    }

    public Node getShopItemUI()
    {
        // Create a square tile
        StackPane tile = new StackPane();
        tile.setPrefSize(80, 80);
        tile.setMinSize(80, 80);
        tile.setMaxSize(80, 80);

        // Apply CSS styling
        tile.getStyleClass().add("shop-item-tile");

        // Add item name label
        Label nameLabel = new Label(this.getName());
        nameLabel.setMouseTransparent(true);
        nameLabel.getStyleClass().add("shop-item-name-label");

        tile.getChildren().add(nameLabel);

        // Create a JavaFX Tooltip
        javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip("Item name here");
        tooltip.setAutoFix(true);   // adjusts position to stay inside window
        tooltip.setAutoHide(true);  // hides when mouse leaves
        tooltip.setHideDelay(javafx.util.Duration.ZERO); // hide immediately
        tooltip.setShowDelay(javafx.util.Duration.ZERO); // show immediately
        Tooltip.install(tile, tooltip);

        tile.setOnMouseEntered(e ->
        {
            tile.setScaleX(1.05);
            tile.setScaleY(1.05);
        });

        tile.setOnMouseExited(e ->
        {
            tile.setScaleX(1.0);
            tile.setScaleY(1.0);
        });

//        tile.setOnMouseMoved(e -> {
//            tooltip.setX(e.getScreenX() + offsetX);
//            tooltip.setY(e.getScreenY() - (tooltip.getHeight() - (tooltip.getHeight()/4)));
//        });

        return tile;
    }

    public int getPrice()
    {
        return price;
    }

    public int getMultiplier()
    {
        return this.multiplier;
    }
}
