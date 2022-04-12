package com.rainchat.rinventory.listener;

import com.rainchat.rinventory.manager.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ConnectListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        InventoryManager.findByPlayer(player).ifPresent(hInventory -> {

            hInventory.onClose(hInventory, player);
            InventoryManager.getContent().remove(player.getUniqueId());
            player.updateInventory();

        });
    }

}
