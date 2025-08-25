package com.example.wordsolve;

import com.almasb.fxgl.trade.Shop;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;

/// Shared game upgrades and money shared across different views.
public class GameState
{
    private static GameState instance = null;

    // Observable properties that UI can bind to
    private final IntegerProperty money = new SimpleIntegerProperty(0); // Starting money

    /// Starting items. TODO: for testing remove later.
    private ArrayList<ShopItem> initialItems = new ArrayList<>();

    /// The players boons or upgrades.
    private ObservableList<ShopItem> playersItems = FXCollections.observableList(initialItems);

    private GameState() {}

    public static GameState getInstance()
    {
        if (instance == null) {
            instance = new GameState();
        }

        return instance;
    }

    private IntegerProperty moneyProperty() {
        return money;
    }

    /// get bindable property as a formatted string to keep formatting in one place.
    public StringBinding getBindableString()
    {
        return this.moneyProperty().asString("$%d");
    }

    public int getMoney()
    {
        return money.get();
    }

    public void addMoney(int amount) {
        money.set(money.get() + amount);
    }

    // Player items methods
    public ObservableList<ShopItem> getPlayersItems() {
        return playersItems;
    }

    public void addItem(FlowPane boonsContainer, ShopItem item) {
        playersItems.add(item);
        updateItemsUI(boonsContainer);
    }

    public void removeItem(ShopItem item) {
        playersItems.remove(item);
    }

    public boolean hasItem(ShopItem item) {
        return playersItems.contains(item);
    }

    public int getItemCount(ShopItem item) {
        return (int) playersItems.stream()
                .filter(i -> i.equals(item))
                .count();
    }

    public void clearItems(FlowPane boonsContainer)
    {
        playersItems.clear();
        updateItemsUI(boonsContainer);
    }

    // Purchase item method that handles both money and inventory
    public boolean purchaseItem(FlowPane boonsContainer, ShopItem item)
    {
        if (money.get() >= (item.getPrice())) {
            addItem(boonsContainer, item);
            updateItemsUI(boonsContainer);
            return true;
        }

        updateItemsUI(boonsContainer);
        return false;
    }

    public void updateItemsUI(FlowPane boonsContainer)
    {
        // Update the UI to show current active items/boons
        if (boonsContainer != null)
        {
            boonsContainer.getChildren().clear();
            for (ShopItem item : this.getPlayersItems())
            {
                // boon item formatting.
                var itemsUI = item.getShopItemUI();
                boonsContainer.getChildren().add(itemsUI);
            }
        }
    }
}
