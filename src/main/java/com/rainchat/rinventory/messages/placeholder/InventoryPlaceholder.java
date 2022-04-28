package com.rainchat.rinventory.messages.placeholder;

import com.rainchat.rinventory.gui.inventory.SimpleInventory;

public abstract class InventoryPlaceholder<T> extends CustomPlaceholder<T> {

    private SimpleInventory menu;

    public InventoryPlaceholder() {
        this("");
    }

    public InventoryPlaceholder(String prefix) {
        super(prefix);
    }

    public SimpleInventory getMenu() {
        return menu;
    }

    public void setMenu(SimpleInventory menu) {
        this.menu = menu;
    }

}