package com.rainchat.rinventory.gui.builder;

import com.rainchat.rinventory.gui.ui.button.SelectionButton;
import com.rainchat.rinventory.gui.ui.items.items.ItemModifier;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemBuilder implements Cloneable {
    private final List<ItemModifier> itemModifiers = new LinkedList<>();
    private List<PlaceholderSupply<?>> replacementSource = new ArrayList<>();
    private Material defaultMaterial = Material.STONE;
    private SelectionButton selectionButton;

    public ItemBuilder addItemModifier(ItemModifier modifier) {
        itemModifiers.add(modifier);
        return this;
    }

    public ItemBuilder removeItemModifier(String name) {
        itemModifiers.removeIf(itemModifier -> itemModifier.getName().equals(name));
        return this;
    }

    public List<ItemModifier> getItemModifiers() {
        return Collections.unmodifiableList(itemModifiers);
    }

    public Map<String, Object> serializeItemModifiers() {
        Map<String, Object> map = new HashMap<>();
        itemModifiers.forEach(itemModifier -> map.put(itemModifier.getName(), itemModifier.toObject()));
        return map;
    }

    public List<PlaceholderSupply<?>> getStringReplacerMap() {
        List<PlaceholderSupply<?>> list = new ArrayList<>(replacementSource);
        list.addAll(selectionButton.getInventory().getSupplyList());
        return list;
    }

    public ItemBuilder addStringReplacer(PlaceholderSupply<?> replacer) {
        this.replacementSource.add(replacer);
        return this;
    }

    public ItemStack build(UUID uuid) {
        ItemStack itemStack = new ItemStack(defaultMaterial);
        for (ItemModifier modifier : itemModifiers) {

            itemStack = modifier.modify(itemStack, uuid, this);
        }
        return itemStack;
    }

    public ItemStack build(Player player) {
        return build(player.getUniqueId());
    }

    public ItemStack build() {
        return build(UUID.randomUUID());
    }

    public void setMenu(SelectionButton menu) {
        this.selectionButton = menu;
    }

    public void setPlaceholder(Player player) {
        replacementSource = PlaceholderBuilder.INSTANCE.getReplacements(selectionButton.getInventory(), player);
    }

    public void setDefaultMaterial(Material material) {
        this.defaultMaterial = material;
    }

    @Override
    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();
            clone.getStringReplacerMap().clear();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}