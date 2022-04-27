package com.rainchat.rinventory.gui.ui.items.constructor.modifier;

import com.rainchat.rinventory.gui.ui.items.constructor.items.ItemMetaModifier;
import com.rainchat.rinventory.messages.RChatUtil;
import com.rainchat.rinventory.messages.placeholder.PlaceholderSupply;
import com.rainchat.rinventory.utils.collections.CollectionUtils;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class LoreModifier extends ItemMetaModifier {
    private final List<String> lore = new ArrayList();

    public LoreModifier() {
    }

    public String getName() {
        return "lore";
    }

    private List<String> getReplacedLore(UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        List<String> replacedLore = new ArrayList(this.lore);
        replacedLore.replaceAll((s) -> {
            return RChatUtil.translateRaw(s, uuid, replacementSource);
        });
        return replacedLore;
    }

    public ItemMeta modifyMeta(ItemMeta meta, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        meta.setLore(this.getReplacedLore(uuid, replacementSource));
        return meta;
    }

    public void loadFromItemMeta(ItemMeta meta) {
        this.setLore(meta.getLore());
    }

    public boolean canLoadFromItemMeta(ItemMeta meta) {
        return meta.hasLore();
    }

    public boolean compareWithItemMeta(ItemMeta meta, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return !meta.hasLore() && this.lore.isEmpty() || this.getReplacedLore(uuid, replacementSource).equals(meta.getLore());
    }

    public Object toObject() {
        return this.lore;
    }

    public void loadFromObject(Object object) {
        this.setLore((CollectionUtils.createStringListFromObject(object, false)));
    }

    public LoreModifier setLore(String... lore) {
        return this.setLore(Arrays.asList(lore));
    }

    public LoreModifier addLore(String lore) {
        this.lore.addAll(Arrays.asList(lore.split("\\n")));
        return this;
    }

    public LoreModifier setLore(Collection<String> lore) {
        this.clearLore();
        this.lore.addAll(CollectionUtils.splitAll("\\n", lore));
        return this;
    }

    public LoreModifier clearLore() {
        this.lore.clear();
        return this;
    }
}
