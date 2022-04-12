package com.rainchat.rinventory.ui.items;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class EmptyItem extends SimpleItem {

    public EmptyItem() {
        super(new ItemStack(Material.AIR), inventoryClickEvent -> {});
    }

}
