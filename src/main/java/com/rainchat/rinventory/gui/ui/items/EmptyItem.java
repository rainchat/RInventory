package com.rainchat.rinventory.gui.ui.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EmptyItem extends SimpleItem {

    public EmptyItem() {
        super(new ItemStack(Material.AIR), inventoryClickEvent -> {});
    }

}
