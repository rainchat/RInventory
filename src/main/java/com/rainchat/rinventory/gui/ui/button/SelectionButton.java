package com.rainchat.rinventory.gui.ui.button;

import com.rainchat.rinventory.RCore;
import com.rainchat.rinventory.gui.builder.ActionBuilder;
import com.rainchat.rinventory.gui.builder.ItemModifierBuilder;
import com.rainchat.rinventory.gui.builder.ItemBuilder;
import com.rainchat.rinventory.gui.builder.RequirementBuilder;
import com.rainchat.rinventory.gui.ui.requirement.Requirement;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.scheduler.RScheduler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SelectionButton extends SimpleItem implements Cloneable{

    private int slot;
    private ItemBuilder itemBuilder = new ItemBuilder();

    private final Map<ClickType, List<Action>> actionMap = new HashMap<>();
    private final Map<ClickType, List<Requirement>> requirementMap = new HashMap<>();


    @Override
    public ItemStack getItem() {

        setInventoryClickEvent(baseClick -> {
            RScheduler rScheduler = RCore.syncScheduler();

            if (isAccept(requirementMap.get(baseClick.getType()), baseClick.getWhoClicked().getUniqueId())) {
                actionMap.get(baseClick.getType()).forEach(action -> action.addToTask(baseClick.getWhoClicked().getUniqueId(), rScheduler, baseClick));
            }

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

        setActions(section.get("actions"));

    }

    private void setActions(Object o) {

        Map<String, ClickType> clickTypeMap = new HashMap<>();
        Arrays.stream(ClickType.values()).forEach(clickType -> clickTypeMap.put(clickType.toString(), clickType));
        if (o instanceof Map) {
            Map<String, Object> keys = new CaseInsensitiveStringMap<>((Map<String, Object>) o);
            List<Action> defaultActions = Optional.ofNullable(keys.get("default")).map(value -> ActionBuilder.INSTANCE.getActions(getInventory(), value)).orElse(Collections.emptyList());
            clickTypeMap.forEach((clickTypeName, clickType) -> actionMap.put(clickType, Optional.ofNullable(keys.get(clickTypeName)).map(obj -> ActionBuilder.INSTANCE.getActions(getInventory(), obj)).orElse(defaultActions)));

            List<Requirement> defaultRequirement = Optional.ofNullable(keys.get("default")).map(value -> RequirementBuilder.INSTANCE.getRequirement(getInventory(), value)).orElse(Collections.emptyList());
            clickTypeMap.forEach((clickTypeName, clickType) -> requirementMap.put(clickType, Optional.ofNullable(keys.get(clickTypeName)).map(obj -> RequirementBuilder.INSTANCE.getRequirement(getInventory(), obj)).orElse(defaultRequirement)));

        } else {
            clickTypeMap.values().forEach(advancedClickType -> actionMap.put(advancedClickType, ActionBuilder.INSTANCE.getActions(getInventory(), o)));
            clickTypeMap.values().forEach(advancedClickType -> requirementMap.put(advancedClickType, RequirementBuilder.INSTANCE.getRequirement(getInventory(), o)));
        }

    }

    public boolean isAccept(List<Requirement> requirements, UUID uuid) {
        for (Requirement requirement: requirements) {
            if (!requirement.check(uuid)) {
                return false;
            }
        }
        return true;
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
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (SelectionButton) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
