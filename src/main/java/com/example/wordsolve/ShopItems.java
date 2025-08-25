package com.example.wordsolve;

import com.almasb.fxgl.trade.Shop;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/// List of game items to choose from to show in the shop.
public class ShopItems
{
    public static ArrayList<ShopItem> getItemList()
    {
        ArrayList<ShopItem> items = new ArrayList<>();

        var doubleTrouble = new ShopItem("double trouble", 1, 0, 1);
        // TODO add more items here. will need to make class for each all implementing "Action" function from ShopItem. That event fires when the shop items is used.

        items.add(doubleTrouble);

        return items;
    }
}
