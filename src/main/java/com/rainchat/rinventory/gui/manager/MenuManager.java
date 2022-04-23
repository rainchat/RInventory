package com.rainchat.rinventory.gui.manager;

import com.rainchat.rinventory.gui.builder.MenuBuilder;
import com.rainchat.rinventory.gui.ui.inventory.ArgsMenu;
import com.rainchat.rinventory.utils.storage.Config;
import com.rainchat.rinventory.utils.storage.YamlConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.security.Permissions;
import java.util.*;
import java.util.logging.Level;

public class MenuManager {

    private final Map<String, ArgsMenu> menuMap = new HashMap<>();
    private final JavaPlugin plugin;

    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Register the menu
     *
     * @param config the menu config
     */
    public void registerMenu(Config config) {
        String name = config.getName();
        if (menuMap.containsKey(name)) {
            plugin.getLogger().log(Level.WARNING, "\"{0}\" is already available in the menu manager. Ignored", name);
        } else {
            Optional.ofNullable(MenuBuilder.INSTANCE.getMenu(name, config)).ifPresent(menu -> menuMap.put(name, menu));
        }
    }

    /**
     * Clear all menus
     */
    public void clear() {
        menuMap.clear();
    }

    /**
     * Check if the menu exists
     *
     * @param name the menu name
     *
     * @return true if it exists, otherwise false
     */
    public boolean contains(String name) {
        return menuMap.containsKey(name);
    }

    /**
     * Open the menu for the player
     *
     * @param name   the menu name
     * @param player the player
     * @param args   the arguments from the open command
     */
    public void openMenu(String name, Player player, String... args) {
        menuMap.get(name).open(player);
    }

    /**
     * Get the name of all menus
     *
     * @return the list of the names
     */
    public Collection<String> getMenuNames() {
        return menuMap.keySet();
    }

    /**
     * Get the menu
     *
     * @param name the menu name
     *
     * @return the menu
     */
    public ArgsMenu getMenu(String name) {
        return menuMap.get(name);
    }

}
