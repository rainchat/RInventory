package com.rainchat.rinventory.gui.ui.pagination;

import com.rainchat.rinventory.gui.builder.ButtonBuilder;
import com.rainchat.rinventory.gui.builder.ItemBuilder;
import com.rainchat.rinventory.gui.placeholders.TargetReplacements;
import com.rainchat.rinventory.gui.ui.button.SelectionButton;
import com.rainchat.rinventory.gui.ui.button.SimpleItem;
import com.rainchat.rinventory.gui.ui.items.modifier.AmountModifier;
import com.rainchat.rinventory.gui.ui.items.modifier.PlayerHeadModifier;
import com.rainchat.rinventory.utils.collections.CaseInsensitiveStringMap;
import com.rainchat.rinventory.utils.general.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.bukkit.Bukkit.getName;

public class FromConfigPagination extends SimplePagination {

    private SelectionButton pageItem;
    private List<SimpleItem> clickableItems = new ArrayList<>();

    public void setFromSection(Map<String, Object> section) {
        Optional.ofNullable(section.get("pagination-slots")).ifPresent(o -> {
            String[] strings = String.valueOf(o).split(",");
            List<Integer> slots = new ArrayList<>();
            for (String s : strings) {
                if (MathUtil.isInteger(s)) {
                    slots.add(Integer.parseInt(s));
                }
            }
            setItemSlots(slots);
        });

        Optional.ofNullable(section.get("page")).ifPresent(o -> {
            if (!(o instanceof Map)) {
                return;
            }
            Map<String, Object> item = new CaseInsensitiveStringMap<>((Map<String, Object>) o);
            pageItem = ButtonBuilder.INSTANCE.getButton(getMenu(), "menu_" + getName() + "_button_fill_item", item);
        });

    }

    @Override
    public void setupItems() {
        clickableItems.clear();

        int i = 1;
        for (Player player: Bukkit.getOnlinePlayers()) {
            SelectionButton clickItem = pageItem.clone();
            clickItem.setInventory(getMenu());

            ItemBuilder itemBuilder = new ItemBuilder();
            pageItem.getItemBuilder().getItemModifiers().forEach(itemBuilder::addItemModifier);
            itemBuilder.setMenu(clickItem);
            clickItem.setItemBuilder(itemBuilder);
            itemBuilder.addItemModifier(new AmountModifier().setAmount(i));
            itemBuilder.addItemModifier(new PlayerHeadModifier().setHead(player.getName()));
            clickItem.addReplacer(new TargetReplacements(player));
            //ClickableItem item = ClickableItem.empty(clickItem.build(new PlayerReplacements(player)));
            clickableItems.add(clickItem);
            i++;
        }

        setItems(clickableItems);
    }

}
