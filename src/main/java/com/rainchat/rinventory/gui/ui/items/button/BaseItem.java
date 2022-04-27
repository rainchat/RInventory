package com.rainchat.rinventory.gui.ui.items.button;

import com.rainchat.rinventory.gui.ui.inventory.BaseInventory;
import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import org.bukkit.inventory.ItemStack;

public interface BaseItem {

    ItemStack getItem();

    SimpleInventory getInventory();

    void setInventory(SimpleInventory inventory);

    String getName();

    void setName(String name);
}
