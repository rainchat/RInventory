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
            System.out.println(key);
            if (!(value instanceof Map)) {
                return;
            }
            Map<String, Object> test = new CaseInsensitiveStringMap<>((Map<String, Object>) value);
            for (Object o: test.values()) {
                System.out.println("" + o);
            }

            if (key.equalsIgnoreCase("menu-settings")) {

                if (!(value instanceof Map)) {
                    return;
                }
                Map<String, Object> settings = new CaseInsensitiveStringMap<>((Map<String, Object>) value);


            } else if (key.equalsIgnoreCase("menu-items")) {

                if (!(value instanceof Map)) {
                    return;
                }
                Map<String, Object> items = new CaseInsensitiveStringMap<>((Map<String, Object>) value);


            } else if (key.equalsIgnoreCase("pagination-items")) {

                if (!(value instanceof Map)) {
                    return;
                }
                Map<String, Object> pageItems = new CaseInsensitiveStringMap<>((Map<String, Object>) value);

            }


        });

    }


}
