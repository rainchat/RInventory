package com.rainchat.rinventory.gui.ui.items;

import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.inventory.BaseInventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class SimpleItem implements BaseItem {


    private BaseInventory baseInventory;
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

    @Override
    public void setInventory(BaseInventory inventory) {
        this.baseInventory = baseInventory;
    }


    public Consumer<BaseClick> getInventoryClickEvent() {
        return inventoryClickEvent;
    }

}
