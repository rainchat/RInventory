package com.rainchat.rinventory.gui.ui.items.constructor.modifier;

import com.rainchat.rinventory.gui.ui.items.constructor.items.ItemModifier;
import com.rainchat.rinventory.messages.RChatUtil;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.general.MathUtil;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class AmountModifier implements ItemModifier {
    private String amount = "1";

    public AmountModifier() {
    }

    public String getName() {
        return "amount";
    }

    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        original.setAmount(MathUtil.getNumber(RChatUtil.translateRaw(this.amount, uuid, replacementSource)).get().intValue());
        return original;
    }

    public Object toObject() {
        return this.amount;
    }

    public void loadFromObject(Object object) {
        this.amount = String.valueOf(object);
    }

    public void loadFromItemStack(ItemStack itemStack) {
        this.amount = String.valueOf(itemStack.getAmount());
    }

    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return (Boolean) MathUtil.getNumber(RChatUtil.translateRaw(this.amount, uuid, replacementSource)).map((bigDecimal) -> {
            return bigDecimal.intValue() >= itemStack.getAmount();
        }).orElse(false);
    }

    public AmountModifier setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public AmountModifier setAmount(int amount) {
        this.amount = String.valueOf(amount);
        return this;
    }
}
