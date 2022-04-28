package com.rainchat.rinventory.gui.ui.click;

import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import com.rainchat.rinventory.gui.ui.button.SimpleItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BaseClick {

    private final SimpleInventory gui;
    private final SimpleItem element;
    private final InventoryClickEvent event;

    public BaseClick(SimpleInventory gui, SimpleItem element, InventoryClickEvent event) {
        this.gui = gui;

        this.element = element;
        this.event = event;
    }


    /**
     * Get the element that was clicked
     * @return  The clicked GuiElement
     */
    public SimpleItem getElement() {
        return element;
    }

    /**
     * Get the type of the click
     * @return  The type of the click
     */
    public ClickType getType() {
        return event.getClick();
    }

    /**
     * Get who clicked the element
     * @return  The player that clicked
     */
    public HumanEntity getWhoClicked() {
        return event.getWhoClicked();
    }

    /**
     * Get the event of the click
     * @return  The InventoryClickEvent associated with this Click
     */
    public InventoryClickEvent getEvent() {
        return event;
    }

    public SimpleInventory getGui() {
        return gui;
    }

}
