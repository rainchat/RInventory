package com.rainchat.rinventory.gui.ui.button;

import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import com.rainchat.rinventory.messages.RChatUtil;

import java.util.UUID;

public abstract class BaseAction implements Action {

    private final String string;
    private SimpleInventory menu;

    protected BaseAction(String string) {
        this.string = string;
    }

    /**
     * Get the replaced string
     *
     * @param uuid the unique id
     *
     * @return the replaced string
     */
    protected String getReplacedString(UUID uuid) {
        return RChatUtil.translateRaw(string);
    }

    public SimpleInventory getMenu() {
        return menu;
    }

    @Override
    public void setMenu(SimpleInventory menu) {
        this.menu = menu;
    }
}
