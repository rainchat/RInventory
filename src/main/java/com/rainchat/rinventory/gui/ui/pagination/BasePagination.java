package com.rainchat.rinventory.gui.ui.pagination;


import com.rainchat.rinventory.gui.ui.inventory.SimpleInventory;
import org.bukkit.entity.Player;

public interface BasePagination {

    void setMenu(SimpleInventory menu);

    SimpleInventory getMenu();

    Player getPlayer();

    void setPlayer(Player player);

    void setupItems();

}
