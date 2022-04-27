package com.rainchat.rinventory.gui.ui.items.button;

import com.rainchat.rinventory.gui.ui.inventory.ArgsMenu;
import com.rainchat.rinventory.messages.RChatUtil;

import java.util.UUID;

public abstract class BaseAction implements Action {

    private final String string;
    private ArgsMenu menu;

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

    public ArgsMenu getMenu() {
        return menu;
    }

    @Override
    public void setMenu(ArgsMenu menu) {
        this.menu = menu;
    }
}
