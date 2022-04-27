package com.rainchat.rinventory.gui.ui.items.button;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EmptyItem extends SimpleItem {

    public EmptyItem() {
        setItem(new ItemStack(Material.AIR));
        setInventoryClickEvent(inventoryClickEvent -> {});
    }

}
