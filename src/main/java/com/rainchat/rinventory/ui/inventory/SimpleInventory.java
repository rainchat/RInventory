package com.rainchat.rinventory.ui.inventory;

import com.rainchat.rinventory.manager.InventoryManager;
import com.rainchat.rinventory.ui.items.BaseItem;
import com.rainchat.rinventory.ui.items.EmptyItem;
import com.rainchat.rinventory.ui.items.SimpleItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Objects;

public class SimpleInventory implements InventoryHolder, Listener {

    private Inventory inventory;

    private String title;
    private int guiSize;

    private HashMap<Integer, SimpleItem> clickableItems;

    public SimpleInventory(Plugin plugin, String name, int size) {
        this.inventory = Bukkit.createInventory(this, size * 9, name);
        this.clickableItems = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void open(Player player) {
        SimpleInventory hInventory = InventoryManager.findByPlayer(Objects.requireNonNull(player, "player cannot be null!")).orElse(null);

        this.onOpen(this, player);

        if (hInventory == null || !hInventory.equals(this)) {
            player.openInventory(this.inventory);
            InventoryManager.getContent().put(player.getUniqueId(), this);
        }

        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public void guiFill(SimpleItem clickableItem) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void setItem(int slot, SimpleItem clickableItem) {
        if (clickableItem.getItem() == null) {
            this.clickableItems.put(slot, new EmptyItem());
            this.inventory.setItem(slot, clickableItem.getItem());
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.inventory.setItem(slot, clickableItem.getItem());
    }

    public SimpleItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public void onOpen(SimpleInventory hInventory, Player player) {

    }

    public void onClose(SimpleInventory hInventory, Player player) {

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
