package com.rainchat.rinventory.gui.ui.requirement;


import com.rainchat.rinventory.gui.inventory.SimpleInventory;

import java.util.UUID;

public abstract class BaseRequirement<V> implements Requirement {

    private final String name;
    protected Object value;
    private SimpleInventory menu;

    protected BaseRequirement(String name) {
        this.name = name;
    }

    /**
     * Called when getting the final values
     *
     * @param uuid the unique id
     *
     * @return the final value
     */
    public abstract V getParsedValue(UUID uuid);

    @Override
    public SimpleInventory getMenu() {
        return menu;
    }

    @Override
    public void setMenu(SimpleInventory menu) {
        this.menu = menu;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }
}
