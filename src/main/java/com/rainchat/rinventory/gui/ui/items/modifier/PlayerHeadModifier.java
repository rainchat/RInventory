package com.rainchat.rinventory.gui.ui.items.modifier;

import com.rainchat.rinventory.gui.ui.items.items.ItemModifier;
import com.rainchat.rinventory.messages.RChatUtil;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.general.MathUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class PlayerHeadModifier implements ItemModifier {
    private String playerOwner = "1";

    public PlayerHeadModifier() {
    }

    public String getName() {
        return "head";
    }

    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        original.setType(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) original.getItemMeta();
        skull.setOwner(playerOwner);
        original.setItemMeta(skull);
        return original;
    }

    public Object toObject() {
        return this.playerOwner;
    }

    public void loadFromObject(Object object) {
        this.playerOwner = String.valueOf(object);
    }

    public void loadFromItemStack(ItemStack itemStack) {
        this.playerOwner = String.valueOf(itemStack.getDurability());
    }

    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return (Boolean) MathUtil.getNumber(RChatUtil.translateRaw(this.playerOwner, uuid, replacementSource)).map((bigDecimal) -> {
            return bigDecimal.shortValue() == itemStack.getDurability();
        }).orElse(false);
    }

    public PlayerHeadModifier setHead(String playerOwner) {
        this.playerOwner = playerOwner;
        return this;
    }

}
