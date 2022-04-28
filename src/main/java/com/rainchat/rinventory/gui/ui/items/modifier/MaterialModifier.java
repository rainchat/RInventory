package com.rainchat.rinventory.gui.ui.items.modifier;

import com.rainchat.rinventory.gui.ui.items.items.ItemModifier;
import com.rainchat.rinventory.messages.RChatUtil;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialModifier implements ItemModifier {
    private String materialString;

    @Override
    public String getName() {
        return "material";
    }

    @Override
    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        Optional
                .ofNullable(Material.matchMaterial(RChatUtil.translateRaw(materialString, uuid, replacementSource)))
                .ifPresent(original::setType);
        return original;
    }

    @Override
    public Object toObject() {
        return this.materialString;
    }

    @Override
    public void loadFromObject(Object object) {
        this.materialString = String.valueOf(object);
    }

    @Override
    public void loadFromItemStack(ItemStack itemStack) {
        this.materialString = itemStack.getType().name();
    }

    @Override
    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return itemStack.getType().name().equalsIgnoreCase(RChatUtil.translateRaw(materialString, uuid, replacementSource));
    }

    /**
     * Set the material
     *
     * @param material the material
     * @return {@code this} for builder chain
     */
    public MaterialModifier setMaterial(Material material) {
        this.materialString = material.name();
        return this;
    }

    /**
     * Set the material
     *
     * @param material the material
     * @return {@code this} for builder chain
     */
    public MaterialModifier setMaterial(String material) {
        this.materialString = material;
        return this;
    }
}