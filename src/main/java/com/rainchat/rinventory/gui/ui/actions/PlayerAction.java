package com.rainchat.rinventory.gui.ui.actions;

import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.button.CommandAction;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class PlayerAction extends CommandAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public PlayerAction(String string) {
    super(string);
  }

  @Override
  public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {
    String command = getFinalCommand(getReplacedString(uuid));
    Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> rScheduler.run(() -> player.chat(command)));
  }
}
