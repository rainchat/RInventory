package com.rainchat.rinventory.gui.ui.items.button;

import com.rainchat.rinventory.gui.ui.inventory.BaseInventory;
import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
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
    public SimpleInventory getInventory() {
        return null;
    }

    @Override
    public void setInventory(SimpleInventory inventory) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }


    public Consumer<InventoryClickEvent> getInventoryClickEvent() {
        return inventoryClickEvent;
    }

}
