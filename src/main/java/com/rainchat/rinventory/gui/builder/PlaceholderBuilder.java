package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.placeholders.PlayerReplacements;
import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import com.rainchat.rinventory.utils.builder.Builder;
import com.rainchat.rinventory.messages.placeholder.InventoryPlaceholder;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderBuilder extends Builder<String, InventoryPlaceholder<?>> {

    public static final PlaceholderBuilder INSTANCE = new PlaceholderBuilder();

    private PlaceholderBuilder() {
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        register(s -> new PlayerReplacements(), "console");
    }

    /**
     * Build a list of actions
     *
     * @param menu   the menu involved in
     * @param object the object
     *
     * @return the list of actions
     */
    public List<PlaceholderSupply<?>> getReplacements(SimpleInventory menu, Object object) {
        return CollectionUtils.createStringListFromObject(object, true)
                .stream()
                .map(string -> {

                    String[] split = string.split(":", 2);
                    String name = split[0];
                    String value = split.length > 1 ? split[1] : "";

                    InventoryPlaceholder<?> action = build(name.trim(), value.trim()).orElseGet(PlayerReplacements::new);
                    action.setMenu(menu);
                    return action;
                })
                .collect(Collectors.toList());
    }
}