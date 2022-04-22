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

            this.minArgs = Optional.ofNullable(settings.get(MIN_ARGS)).map(String::valueOf).flatMap(Validate::getNumber).map(BigDecimal::intValue).orElse(this.minArgs);
            this.defaultArgs = Optional.ofNullable(settings.get(DEFAULT_ARGS)).map(String::valueOf).map(s -> s.split(" ")).orElse(this.defaultArgs);

            Optional.ofNullable(settings.get(ARGS)).map(o -> CollectionUtils.createStringListFromObject(o, true)).ifPresent(list -> {
                this.registeredArgs = list.size();
                for (int i = 0; i < list.size(); i++) {
                    this.argToIndexMap.put(list.get(i), i);
                }
            });
            Optional.ofNullable(settings.get(MIN_ARGS_ACTION)).ifPresent(o -> this.minArgsAction.addAll(ActionBuilder.INSTANCE.getActions(this, o)));
        });

    }


}
