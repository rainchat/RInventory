package com.rainchat.rinventory.gui.ui.actions;


import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.items.button.BaseAction;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BroadcastAction extends BaseAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public BroadcastAction(String string) {
    super(string);
  }

  @Override
  public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {
    rScheduler.run(() -> Bukkit.broadcastMessage((getReplacedString(uuid))));
  }
}
