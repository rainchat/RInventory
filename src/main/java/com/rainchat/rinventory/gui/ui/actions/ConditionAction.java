package com.rainchat.rinventory.gui.ui.actions;

import com.rainchat.rinventory.gui.ui.click.BaseClick;
import com.rainchat.rinventory.gui.ui.items.button.BaseAction;
import com.rainchat.rinventory.scheduler.RScheduler;

import java.util.UUID;

public class ConditionAction extends BaseAction {

    public ConditionAction(String string) {
        super(string);
    }

    @Override
    public void addToTask(UUID uuid, RScheduler rScheduler, BaseClick baseClick) {
        rScheduler.setCancel(true);
    }
}
