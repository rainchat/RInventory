package com.rainchat.rinventory.gui.manager;

import com.rainchat.rinventory.gui.listener.ClicksListener;
import com.rainchat.rinventory.gui.listener.ConnectListener;
import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class InventoryManager {

    private static final Map<UUID, SimpleInventory> inventoryMap = new HashMap<>();

    public static void initialize(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new ClicksListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ConnectListener(), plugin);
    }

    public static Map<UUID, SimpleInventory> getContentSafe() {
        return new HashMap<>(inventoryMap);
    }

    public static Map<UUID, SimpleInventory> getContent() {
        return inventoryMap;
    }

    public static Optional<SimpleInventory> findByPlayer(Player player) {
        return findByUID(player.getUniqueId());
    }

    public static SimpleInventory getByPlayer(Player player) {
        return findByPlayer(player).orElseThrow(() -> new NullPointerException("this player doesn't have a inventory!"));
    }

    public static Optional<SimpleInventory> findByUID(UUID uid) {
        return Optional.ofNullable(inventoryMap.get(Objects.requireNonNull(uid, "UID cannot be null!")));
    }


}
