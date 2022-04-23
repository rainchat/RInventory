package com.rainchat.rinventory.gui.ui.inventory;

import com.rainchat.rinventory.gui.manager.InventoryManager;
import com.rainchat.rinventory.gui.ui.items.EmptyItem;
import com.rainchat.rinventory.gui.ui.items.SimpleItem;
import com.rainchat.rinventory.utils.inventory.InvBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Objects;

public class SimpleInventory {

    private Inventory inventory;

    private String title;
    private int guiSize;

    private HashMap<Integer, SimpleItem> clickableItems;

    public SimpleInventory(String name, int size) {
        this.title = name;
        this.guiSize = size;
        this.clickableItems = new HashMap<>();
    }

    public void open(Player player) {
        SimpleInventory simpleInventory = InventoryManager.findByPlayer(Objects.requireNonNull(player, "player cannot be null!")).orElse(null);

        this.onOpen(this, player);

        for (var entryMap: clickableItems.entrySet()) {
            this.inventory = getInventory();
            this.inventory.setItem(entryMap.getKey(), entryMap.getValue().getItem());
        }

        setInventory(new InvBuilder().size(guiSize).title(title).getInventory());
        if (simpleInventory == null || !simpleInventory.equals(this)) {
            InventoryManager.getContent().put(player.getUniqueId(), this);
        }
        player.openInventory(this.inventory);
    }

    public void close(Player player) {
        InventoryManager.getContent().remove(player.getUniqueId());
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
        } else {
            this.clickableItems.put(slot, clickableItem);
        }
    }

    public SimpleItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public void onOpen(SimpleInventory simpleInventory, Player player) {

    }

    public void onClose(SimpleInventory simpleInventory, Player player) {

    }

    public Inventory getInventory() {
        return inventory;
    }


    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
