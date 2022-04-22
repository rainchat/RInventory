package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.ui.inventory.ArgsMenu;
import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import com.rainchat.rinventory.utils.builder.Builder;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.utils.storage.Config;

import java.util.Map;
import java.util.Optional;

public class MenuBuilder extends Builder<String, ArgsMenu> {

    /**
     * The instance of the menu builder
     */
    public static final MenuBuilder INSTANCE = new MenuBuilder();

    private MenuBuilder() {
        registerDefaultMenus();
    }

    private void registerDefaultMenus() {
        register(s -> new ArgsMenu(), "args");
        register(s -> new ArgsMenu(), "page");

    }

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
        ArgsMenu menu = Optional.ofNullable(keys.get("menu-settings.menu-type"))
                .map(String::valueOf)
                .flatMap(string -> build(string, name))
                .orElseGet(() -> build("args", name).orElse(null));
        if (menu != null) {
            menu.setFromConfig(config);
        }
        return menu;
    }
}
