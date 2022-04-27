package com.rainchat.rinventory.gui.builder;



import com.rainchat.rinventory.gui.ui.actions.*;
import com.rainchat.rinventory.gui.ui.inventory.ArgsMenu;
import com.rainchat.rinventory.utils.builder.Builder;
import com.rainchat.rinventory.gui.ui.items.button.Action;
import com.rainchat.rinventory.utils.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ActionBuilder extends Builder<String, Action> {

    public static final ActionBuilder INSTANCE = new ActionBuilder();

    private ActionBuilder() {
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        register(ConsoleAction::new, "console");
        register(OpAction::new, "op");
        register(PlayerAction::new, "player");
        register(DelayAction::new, "delay");
        register(TellAction::new, "tell");
        register(BroadcastAction::new, "broadcast");
        register(ChangePosAction::new, "change-pos");
        register(ConditionAction::new, "condition");
        register(CloseMenuAction::new, "close-menu", "closemenu");
    }

    /**
     * Build a list of actions
     *
     * @param menu   the menu involved in
     * @param object the object
     *
     * @return the list of actions
     */
    public List<Action> getActions(ArgsMenu menu, Object object) {
        return CollectionUtils.createStringListFromObject(object, true)
                .stream()
                .map(string -> {
                    System.out.println(string);
                    String[] split = string.split(":", 2);
                    String name = split[0];
                    String value = split.length > 1 ? split[1] : "";

                    Action action = build(name.trim(), value.trim()).orElseGet(() -> new PlayerAction(string.trim()));
                    action.setMenu(menu);
                    return action;
                })
                .collect(Collectors.toList());
    }
}