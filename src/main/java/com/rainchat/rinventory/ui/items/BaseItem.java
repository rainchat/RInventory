package com.rainchat.rinventory.ui.items;

import com.rainchat.rinventory.ui.inventory.BaseInventory;
import org.bukkit.inventory.ItemStack;

public interface BaseItem {



    ItemStack getItem();

    BaseInventory getInventory();

}
