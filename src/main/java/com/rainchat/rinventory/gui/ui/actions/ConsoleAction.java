package com.rainchat.rinventory.gui.ui.actions;


import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.button.BaseAction;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.Bukkit;

import java.util.UUID;

public class ConsoleAction extends BaseAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public ConsoleAction(String string) {
    super(string);
  }

  @Override
  public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {
    rScheduler.run(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getReplacedString(uuid)));
  }
}
