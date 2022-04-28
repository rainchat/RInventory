package com.rainchat.rinventory.gui.ui.button;

import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class SimpleItem implements BaseItem {

    private String name = "";
    private SimpleInventory baseInventory;
    private ItemStack item;
    private Consumer<BaseClick> inventoryClickEvent;

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public SimpleInventory getInventory() {
        return baseInventory;
    }

    @Override
    public void setInventory(SimpleInventory inventory) {
        this.baseInventory = inventory;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Consumer<BaseClick> getInventoryClickEvent() {
        return inventoryClickEvent;
    }

    public void setInventoryClickEvent(Consumer<BaseClick> inventoryClickEvent) {
        this.inventoryClickEvent = inventoryClickEvent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
