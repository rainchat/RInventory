package com.rainchat.rinventory.gui.placeholders;

import com.rainchat.rinventory.messages.placeholder.InventoryPlaceholder;
import org.bukkit.entity.Player;

public class TargetReplacements extends InventoryPlaceholder<String> {

    private Player player;

    public TargetReplacements(Player player) {
        super("player_");
        this.player = player;
    }


    @Override
    public Class<String> forClass() {
        return String.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {

        return switch (base) {
            case "name" -> player.getName();
            case "B" -> "1";
            case "C" -> "3";
            case "D" -> "4";
            default -> " ";
        };

    }

}
