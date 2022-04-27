package com.rainchat.rinventory.gui.ui.actions;


import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.items.button.BaseAction;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CloseMenuAction extends BaseAction {

  public CloseMenuAction(String string) {
    super(string);
  }

  @Override
  public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {
    Player player = Bukkit.getPlayer(uuid);
    if (getMenu() == null || player == null) {
      return;
    }
    rScheduler.run(() -> baseClick.getGui().close(player));
  }


}
