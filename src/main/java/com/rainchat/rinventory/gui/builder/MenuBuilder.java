package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.inventory.ArgsMenu;
import com.rainchat.rinventory.utils.builder.Builder;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.storage.Config;

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
        register(ArgsMenu::new, "args");
        register(ArgsMenu::new, "page");
    }

    /**
     * Build the menu from the config
     *
     * @param name   the name of the menu
     * @param config the config
     *
     * @return the menu
     */
    public ArgsMenu getMenu(String name, Config config) {
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
