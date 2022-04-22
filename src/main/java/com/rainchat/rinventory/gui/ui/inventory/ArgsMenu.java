package com.rainchat.rinventory.gui.ui.inventory;

import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.utils.storage.Config;
import org.apache.commons.lang.Validate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class ArgsMenu extends SimpleInventory {

    private String name;
    private Config config;

    public ArgsMenu(String name) {
        super("", 6);

        this.name = name;
    }

    public void setFromConfig(Config config) {
        this.config = config;

        config.getNormalizedValues(false).forEach((key, value) -> {
            if (!key.equalsIgnoreCase("menu-settings")) {
                return;
            }

            if (!(value instanceof Map)) {
                return;
            }

            Map<String, Object> settings = new CaseInsensitiveStringMap<>((Map<String, Object>) value);


        });

    }


}
