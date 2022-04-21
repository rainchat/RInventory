package com.rainchat.rinventory.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class InvBuilder implements InventoryHolder {

    private String title = "";
    private InventoryType type = InventoryType.CHEST;
    private int rows = 6;

    public InvBuilder title(String title) {
        this.title = title;
        return this;
    }

    public InvBuilder type(InventoryType type) {
        this.type = type;
        return this;
    }

    public InvBuilder size(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public Inventory getInventory() {
        Inventory inv;
        if (type == InventoryType.CHEST) {
            inv = Bukkit.createInventory(this, rows, title);
        } else {
            inv = Bukkit.createInventory(this, type, title);
        }

        return inv;
    }
}