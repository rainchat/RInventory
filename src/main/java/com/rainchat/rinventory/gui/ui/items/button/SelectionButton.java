package com.rainchat.rinventory.gui.ui.items.button;

import com.rainchat.rinventory.RCore;
import com.rainchat.rinventory.gui.builder.ActionBuilder;
import com.rainchat.rinventory.gui.builder.ItemModifierBuilder;
import com.rainchat.rinventory.gui.ui.inventory.ArgsMenu;
import com.rainchat.rinventory.gui.builder.ItemBuilder;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SelectionButton extends SimpleItem implements Cloneable{

    private int slot;
    private ItemBuilder itemBuilder = new ItemBuilder();
    private List<Action> actions = new ArrayList<>();

    @Override
    public ItemStack getItem() {
        setInventoryClickEvent(baseClick -> {
            RScheduler rScheduler = RCore.syncScheduler();
            actions.forEach(action -> {
                action.addToTask(baseClick.getWhoClicked().getUniqueId(), rScheduler, baseClick);
            });

        });

        return itemBuilder.build();
    }

    public void setFromSection(Map<String, Object> section) {

        itemBuilder.setMenu(this);

        Optional.ofNullable(section.get("slot")).ifPresent(o -> {
            slot = (Integer.parseInt(String.valueOf(o)));
        });

        if (section.get("item") instanceof Map) {
            ItemModifierBuilder.INSTANCE
                    .getItemModifiers(new CaseInsensitiveStringMap<>((Map<String, Object>) section.get("item")))
                    .forEach(itemBuilder::addItemModifier);
        }

        if (section.get("actions") != null) {
            actions = ActionBuilder.INSTANCE.getActions((ArgsMenu) getInventory(), section.get("actions"));
        }

    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public void setItemBuilder(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public void addReplacer(PlaceholderSupply<?> placeholderSupply) {
        itemBuilder.addStringReplacer(placeholderSupply);
    }

    public void setup() {
        getInventory().setItem(slot,this);
    }

    @Override
    public SelectionButton clone() {
        try {
            SelectionButton clone = (SelectionButton) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
