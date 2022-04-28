package com.rainchat.rinventory.gui.ui.requirement;


import com.rainchat.rinventory.gui.inventory.SimpleInventory;

import java.util.UUID;

public interface Requirement {

    /**
     * Called when checking a unique id
     *
     * @param uuid the unique id
     *
     * @return true if the unique id meets the requirement, otherwise false
     */
    boolean check(UUID uuid);

    /**
     * Called when taking the requirements from unique id
     *
     * @param uuid the unique id
     */
    void take(UUID uuid);

    /**
     * Set the value
     *
     * @param value the value
     */
    void setValue(Object value);

    /**
     * Get the name of the requirement
     *
     * @return the name
     */
    String getName();

    /**
     * Set the menu involved in
     *
     * @param menu the menu
     */
    void setMenu(SimpleInventory menu);

    /**
     * Get the menu containing the element
     *
     * @return the menu
     */
    SimpleInventory getMenu();

}

