package com.rainchat.rinventory.gui.ui.actions;

import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.button.BaseAction;
import com.rainchat.rinventory.utils.general.MathUtil;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;

public class ChangePosAction extends BaseAction {
    /**
     * Create a new action
     *
     * @param string the action string
     */
    public ChangePosAction(String string) {
        super(string);
    }

    @Override
    public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {

        String finalValue = getReplacedString(uuid);
        if (!MathUtil.isInteger(finalValue)) {
            Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> player.sendMessage(ChatColor.RED + "Invalid number: " + finalValue));
            return;
        }

        rScheduler.run(() -> {
            baseClick.getGui().setItem(Integer.parseInt(finalValue), baseClick.getElement());
        });
    }
}
