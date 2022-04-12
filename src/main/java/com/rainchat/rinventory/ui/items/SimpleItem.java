package com.rainchat.rinventory.ui.items;

import com.rainchat.rinventory.ui.click.BaseClick;
import com.rainchat.rinventory.ui.inventory.BaseInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class SimpleItem implements BaseItem {



    private final ItemStack item;
    private final Consumer<BaseClick> inventoryClickEvent;

    public SimpleItem(ItemStack item, Consumer<BaseClick> inventoryClickEvent) {
        this.item = item;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public BaseInventory getInventory() {
        return null;
    }


    public Consumer<BaseClick> getInventoryClickEvent() {
        return inventoryClickEvent;
    }

}
