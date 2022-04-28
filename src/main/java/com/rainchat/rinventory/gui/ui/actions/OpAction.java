package com.rainchat.rinventory.gui.ui.actions;


import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.button.CommandAction;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class OpAction extends CommandAction {
  /**
   * Create a new action
   *
   * @param string the action string
   */
  public OpAction(String string) {
    super(string);
  }

  @Override
  public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {
    String replacedString = getFinalCommand(getReplacedString(uuid));
    Optional.ofNullable(Bukkit.getPlayer(uuid))
      .ifPresent(player -> {
        if (player.isOp()) {
          rScheduler.run(() -> player.chat(replacedString));
        } else {
          rScheduler.run(() -> {
            try {
              player.setOp(true);
              player.chat(replacedString);
            } finally {
              player.setOp(false);
            }
          });
        }
      });
  }
}
