package com.rainchat.rinventory.gui.placeholders;

import com.rainchat.rinventory.messages.placeholder.InventoryPlaceholder;

public class PlayerReplacements extends InventoryPlaceholder<String> {


    public PlayerReplacements() {
        super("customp_");
    }


    @Override
    public Class<String> forClass() {
        return String.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {

        return switch (base) {
            case "test" -> "22";
            case "B" -> "1";
            case "C" -> "3";
            case "D" -> "4";
            default -> " ";
        };

    }

}
