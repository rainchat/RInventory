package com.rainchat.rinventory.gui.ui.button;

import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import org.bukkit.inventory.ItemStack;

public interface BaseItem {

    ItemStack getItem();

    SimpleInventory getInventory();

    void setInventory(SimpleInventory inventory);

    String getName();

    void setName(String name);
}
