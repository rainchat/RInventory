package com.rainchat.rinventory.gui.inventory;

import com.rainchat.rinventory.gui.builder.PlaceholderBuilder;
import com.rainchat.rinventory.gui.manager.InventoryManager;
import com.rainchat.rinventory.gui.ui.button.EmptyItem;
import com.rainchat.rinventory.gui.ui.button.SimpleItem;
import com.rainchat.rinventory.gui.ui.pagination.SimplePagination;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.inventory.InvBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class SimpleInventory implements Cloneable {

    private Inventory inventory;

    private HashMap<String,String> parametrs = new HashMap<>();
    private List<PlaceholderSupply<?>> supplyList = new ArrayList<>();

    private String title;
    private int guiSize;

    private HashMap<Integer, SimpleItem> clickableItems;
    private SimpleItem fillItem;

    private SimplePagination simplePagination;

    public SimpleInventory(String name, int size) {
        this.title = name;
        this.guiSize = size;
        this.clickableItems = new HashMap<>();
    }

    public HashMap<Integer, SimpleItem> getClickableItems() {
        return clickableItems;
    }

    public SimpleItem getFillItem() {
        return fillItem;
    }

    public SimplePagination getSimplePagination() {
        return simplePagination;
    }

    public void setSimplePagination(SimplePagination simplePagination) {
        this.simplePagination = simplePagination;
    }

    public void open(Player player) {
        setSupplyList(PlaceholderBuilder.INSTANCE.getReplacements(this, player));
        SimpleInventory simpleInventory = InventoryManager.findByPlayer(Objects.requireNonNull(player, "player cannot be null!")).orElse(null);
        setInventory(new InvBuilder().size(guiSize).title(title).getInventory());

        Optional.ofNullable(simplePagination).ifPresent(selectionButton -> {
            simplePagination.setupItems();
        });
        Optional.ofNullable(fillItem).ifPresent(selectionButton -> {
            guiFill(fillItem);
        });
        updateInventory();

        for (var entryMap: clickableItems.entrySet()) {
            this.inventory.setItem(entryMap.getKey(), entryMap.getValue().getItem());
        }

        if (simpleInventory == null || !simpleInventory.equals(this)) {
            InventoryManager.getContent().put(player.getUniqueId(), this);
        }

        this.onOpen(this, player);

        player.openInventory(this.inventory);
    }

    public void close(Player player) {
        InventoryManager.getContent().remove(player.getUniqueId());
        player.closeInventory();
    }

    public void setFillItem(SimpleItem fillItem) {
        this.fillItem = fillItem;
    }

    public void guiFill(SimpleItem clickableItem) {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (simplePagination != null) {
                if (simplePagination.getItemSlots().contains(slot)) continue;
            }
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void setItem(int slot, SimpleItem clickableItem) {
        this.clickableItems.put(slot, clickableItem);
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

    public void setSize(int size) {
        this.guiSize = size;
    }

    public int getGuiSize() {
        return guiSize;
    }

    public HashMap<String, String> getParametrs() {
        return parametrs;
    }

    public void setParametrs(HashMap<String, String> parametrs) {
        this.parametrs = parametrs;
    }

    public List<PlaceholderSupply<?>> getSupplyList() {
        return supplyList;
    }

    public void setSupplyList(List<PlaceholderSupply<?>> supplyList) {
        this.supplyList = supplyList;
    }

    public void setClickableItems(HashMap<Integer, SimpleItem> clickableItems) {
        this.clickableItems = clickableItems;
    }

    public void setGuiSize(int guiSize) {
        this.guiSize = guiSize;
    }

    public boolean updateInventory() {
        int clickableItemSize = simplePagination.getPaginationItems().size();
        int itemSlotSize = simplePagination.getItemSlots().size();

        int first = simplePagination.getPage() * itemSlotSize;
        int last = (simplePagination.getPage() + 1) * itemSlotSize;
        if (clickableItemSize <= first) return false;
        if (first < 0) return false;

        int m = 0;
        for (; first < last; first++) {
            SimpleItem clickableItem = (clickableItemSize > first) ? simplePagination.getPaginationItems().get(first) : new EmptyItem();
            this.setItem(simplePagination.getItemSlots().get(m), clickableItem);
            m++;
        }
        return true;
    }

    @Override
    public SimpleInventory clone() {
        try {
            SimpleInventory clone = (SimpleInventory) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
