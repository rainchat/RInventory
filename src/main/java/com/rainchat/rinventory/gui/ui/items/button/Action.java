package com.rainchat.rinventory.gui.ui.items.button;


import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.inventory.ArgsMenu;
import com.rainchat.rinventory.scheduler.RScheduler;

import java.util.UUID;

public interface Action {

    ArgsMenu getMenu();
    /**
     * Add the executable code to task
     *
     * @param uuid      the unique id
     * @param baseClick
     */
    void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick);

    /**
     * Set the menu involved in the action
     *
     * @param menu the menu
     */
    void setMenu(ArgsMenu menu);
}
