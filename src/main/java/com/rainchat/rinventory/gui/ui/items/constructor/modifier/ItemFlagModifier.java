package com.rainchat.rinventory.gui.ui.items.constructor.modifier;

import com.rainchat.rinventory.gui.ui.items.constructor.items.ItemMetaModifier;
import com.rainchat.rinventory.messages.RChatUtil;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.collections.CollectionUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemFlagModifier extends ItemMetaModifier {
    private List<String> flagList = Collections.emptyList();

    @Override
    public String getName() {
        return "flag";
    }

    private Set<ItemFlag> getParsed(UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        Set<ItemFlag> flags = new HashSet<>();
        flagList.forEach(string -> {
            string = RChatUtil.translateRaw(string, uuid, replacementSource);
            try {
                flags.add(ItemFlag.valueOf(string.trim().toUpperCase().replace(" ", "_")));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid flag replace {input}".replace("{input}", string));
            }
        });
        return flags;
    }

    @Override
    public ItemMeta modifyMeta(ItemMeta meta, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        for (ItemFlag flag : getParsed(uuid, replacementSource)) {
            meta.addItemFlags(flag);
        }
        return meta;
    }

    @Override
    public void loadFromItemMeta(ItemMeta meta) {
        this.flagList = meta.getItemFlags().stream().map(ItemFlag::name).collect(Collectors.toList());
    }

    @Override
    public boolean canLoadFromItemMeta(ItemMeta meta) {
        return true;
    }

    @Override
    public boolean compareWithItemMeta(ItemMeta meta, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        Set<ItemFlag> list1 = getParsed(uuid, replacementSource);
        Set<ItemFlag> list2 = meta.getItemFlags();
        return list1.size() == list2.size() && list1.containsAll(list2);
    }

    @Override
    public Object toObject() {
        return flagList;
    }

    @Override
    public void loadFromObject(Object object) {
        this.flagList = CollectionUtils.createStringListFromObject(object, true);
    }
}
