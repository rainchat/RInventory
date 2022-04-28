package com.rainchat.rinventory.gui.ui.items.modifier;

import com.rainchat.rinventory.gui.ui.items.items.ItemModifier;
import com.rainchat.rinventory.messages.RChatUtil;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.general.MathUtil;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class DurabilityModifier implements ItemModifier {
    private String durability = "1";

    public DurabilityModifier() {
    }

    public String getName() {
        return "durability";
    }

    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        MathUtil.getNumber(RChatUtil.translateRaw(this.durability, uuid, replacementSource)).ifPresent((bigDecimal) -> {
            original.setDurability(bigDecimal.shortValue());
        });
        return original;
    }

    public Object toObject() {
        return this.durability;
    }

    public void loadFromObject(Object object) {
        this.durability = String.valueOf(object);
    }

    public void loadFromItemStack(ItemStack itemStack) {
        this.durability = String.valueOf(itemStack.getDurability());
    }

    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return (Boolean) MathUtil.getNumber(RChatUtil.translateRaw(this.durability, uuid, replacementSource)).map((bigDecimal) -> {
            return bigDecimal.shortValue() == itemStack.getDurability();
        }).orElse(false);
    }

    public DurabilityModifier setDurability(String durability) {
        this.durability = durability;
        return this;
    }

    public DurabilityModifier setDurability(short durability) {
        this.durability = String.valueOf(durability);
        return this;
    }
}
