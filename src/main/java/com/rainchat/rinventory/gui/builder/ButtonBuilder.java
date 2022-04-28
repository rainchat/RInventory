package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.inventory.SimpleInventory;
import com.rainchat.rinventory.gui.ui.button.SelectionButton;
import com.rainchat.rinventory.utils.builder.Builder;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;

import java.util.Map;
import java.util.Optional;

public class ButtonBuilder extends Builder<SimpleInventory, SelectionButton> {

    public static final ButtonBuilder INSTANCE = new ButtonBuilder();

    private ButtonBuilder() {
        registerDefaultButtons();
    }

    private void registerDefaultButtons() {
        register(s -> new SelectionButton(), "simple");
    }

    public SelectionButton getButton(SimpleInventory menu, String name, Map<String, Object> section) {
        Map<String, Object> keys = new CaseInsensitiveStringMap<>(section);
        SelectionButton button = Optional.ofNullable(keys.get("type"))
                .map(String::valueOf)
                .flatMap(string -> build(string, menu))
                .orElseGet(() -> build("simple", menu).orElse(null));
        if (button != null) {
            button.setName(name);
            button.setInventory(menu);
            button.setFromSection(section);
        }
        return button;
    }
}
