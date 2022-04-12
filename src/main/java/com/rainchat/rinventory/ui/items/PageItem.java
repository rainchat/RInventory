package com.rainchat.rinventory.ui.items;

import com.rainchat.rinventory.ui.inventory.BaseInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PageItem implements BaseItem {



    private final ItemStack item;
    private final Consumer<InventoryClickEvent> inventoryClickEvent;

    public PageItem(ItemStack item, Consumer<InventoryClickEvent> inventoryClickEvent) {
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


    public Consumer<InventoryClickEvent> getInventoryClickEvent() {
        return inventoryClickEvent;
    }

}
