package com.rainchat.rinventory;

import com.rainchat.rinventory.gui.manager.InventoryManager;
import com.rainchat.rinventory.utils.scheduler.RScheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RCore {

    private static JavaPlugin INSTANCE;

    /**
     * Gets instance.
     *
     * @return Instance.
     */
    public static JavaPlugin getInstance() {
        return RCore.INSTANCE;
    }

    public static void initialize( JavaPlugin plugin) {
        if (RCore.INSTANCE != null) return;
        RCore.INSTANCE = Objects.requireNonNull(plugin, "plugin cannot be null!");

        InventoryManager.initialize(plugin);

    }

    public static RScheduler asyncScheduler() {
        return new RScheduler(RCore.INSTANCE, true);
    }

    public static RScheduler syncScheduler() {
        return new RScheduler(RCore.INSTANCE, false);
    }
}
