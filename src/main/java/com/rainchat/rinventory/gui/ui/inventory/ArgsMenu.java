package com.rainchat.rinventory.gui.ui.inventory;

import com.rainchat.rinventory.gui.builder.ButtonBuilder;
import com.rainchat.rinventory.gui.builder.PaginationBuilder;
import com.rainchat.rinventory.gui.ui.items.button.SelectionButton;
import com.rainchat.rinventory.gui.ui.pagination.FromConfigPagination;
import com.rainchat.rinventory.gui.ui.pagination.SimplePagination;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.storage.Config;
import com.rainchat.rinventory.utils.general.MathUtil;

import java.util.*;

import static org.bukkit.Bukkit.getName;

public class ArgsMenu extends SimpleInventory implements Cloneable {

    private String name;
    private final List<SelectionButton> selectionButtons = new ArrayList<>();
    private SelectionButton fillItem;
    private FromConfigPagination pagination;

    public ArgsMenu(String name) {
        super("", 6*9);
        this.name = name;
    }

    public void setFromConfig(Config config) {

        config.getNormalizedValues(false).forEach((key, value) -> {

            if (key.equalsIgnoreCase("menu-settings")) {

                if (!(value instanceof Map)) {
                    return;
                }
                Map<String, Object> settings = new CaseInsensitiveStringMap<>((Map<String, Object>) value);
                setupSettings(settings);

            } else if (key.equalsIgnoreCase("menu-items")) {

                if (!(value instanceof Map)) {
                    return;
                }
                Map<String, Object> items = new CaseInsensitiveStringMap<>((Map<String, Object>) value);

                for (Map.Entry<String,Object> o: items.entrySet()) {
                    if (!(o.getValue() instanceof Map)) {
                        return;
                    }
                    Map<String, Object> item = new CaseInsensitiveStringMap<>((Map<String, Object>) o.getValue());
                    SelectionButton button = ButtonBuilder.INSTANCE.getButton(getMenu(), "menu_" + getName() + "_button_" + o.getKey(), item);
                    selectionButtons.add(button);
                }

            } else if (key.equalsIgnoreCase("pagination-items")) {

                if (!(value instanceof Map)) {
                    return;
                }
                Map<String, Object> pageItems = new CaseInsensitiveStringMap<>((Map<String, Object>) value);
                pagination = PaginationBuilder.INSTANCE.getPagination(this, pageItems);

            }


        });

    }

    public ArgsMenu getMenu() {
        return this;
    }

    private void setupSettings(Map<String, Object> settings) {

        Optional.ofNullable(settings.get("title")).ifPresent(o -> {
            this.setTitle(String.valueOf(o));
        });

        Optional.ofNullable(settings.get("size")).ifPresent(o -> {
            this.setSize(Integer.parseInt(String.valueOf(o)));
        });

        Optional.ofNullable(settings.get("fill-items")).ifPresent(o -> {
            if (!(o instanceof Map)) {
                return;
            }
            Map<String, Object> item = new CaseInsensitiveStringMap<>((Map<String, Object>) o);
            fillItem = ButtonBuilder.INSTANCE.getButton(getMenu(), "menu_" + getName() + "_button_fill_item", item);
        });

    }

    @Override
    public SimpleInventory clone() {
        SimpleInventory simpleInventory = super.clone();
        for (SelectionButton button: selectionButtons){
            button.setInventory(simpleInventory);
            button.setup();
        }
        Optional.ofNullable(fillItem).ifPresent(selectionButton -> {
            selectionButton.setInventory(simpleInventory);
            simpleInventory.setFillItem(selectionButton);
        });
        Optional.ofNullable(pagination).ifPresent(selectionButton -> {
            pagination.setMenu(simpleInventory);
            simpleInventory.setSimplePagination(pagination);
        });


        return simpleInventory;
    }
}
