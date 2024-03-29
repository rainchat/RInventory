package com.rainchat.rinventory.gui.listener;

import com.rainchat.rinventory.gui.manager.InventoryManager;
import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.button.SimpleItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;


public class ClicksListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClick() == ClickType.UNKNOWN) {
            event.setCancelled(true);
            return;
        } else if (event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        } else if (!(event.getWhoClicked() instanceof Player)) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();
        InventoryManager.findByPlayer(player).ifPresent(hInventory -> {
            event.setCancelled(true);

            if (event.getClickedInventory().equals(hInventory.getInventory())) {
                SimpleItem  simpleItem = hInventory.getItem(event.getSlot());
                if (simpleItem == null) {
                    return;
                }
                simpleItem.getInventoryClickEvent().accept(new BaseClick(hInventory, simpleItem,event));
            }
        });
    }

}
