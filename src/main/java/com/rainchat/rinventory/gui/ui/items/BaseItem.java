package com.rainchat.rinventory.gui.ui.items;

import com.rainchat.rinventory.gui.ui.inventory.BaseInventory;
import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface BaseItem {

    ItemStack getItem();

    BaseInventory getInventory();

    void setInventory(BaseInventory inventory);
}
