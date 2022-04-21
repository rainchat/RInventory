package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.utils.storage.Config;

import java.util.Map;
import java.util.Optional;

public class MenuBuilder {



    /**
     * Build the menu from the config
     *
     * @param name   the name of the menu
     * @param config the config
     *
     * @return the menu
     */
    public SimpleInventory getMenu(String name, Config config) {
        Map<String, Object> keys = new CaseInsensitiveStringMap<>(config.getNormalizedValues(true));
        SimpleInventory menu = Optional.ofNullable(keys.get("menu-settings.menu-type"))
                .map(String::valueOf)
                .flatMap(string -> build(string, name))
                .orElseGet(() -> build(MainConfig.DEFAULT_MENU_TYPE.getValue(), name).orElse(null));
        if (menu != null) {
            menu.setFromConfig(config);
        }
        return menu;
    }
}
