package com.toonystank.moodyfishing.items;

import com.toonystank.moodyfishing.items.builder.ItemBuilder;
import com.toonystank.moodyfishing.utils.MessageUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public record CustomItem(String id, Material material, int modelData, String itemName, List<String> lore,
                         boolean hideAttributes, boolean unbreakable, Map<Enchantment, Integer> enchantmentMap) {

    public ItemStack build() {
        ItemBuilder builder = ItemBuilder
                .from(material)
                .name(MessageUtils.format(itemName))
                .lore(MessageUtils.format(lore))
                .unbreakable(unbreakable)
                .model(modelData);
        if (hideAttributes) {
            builder.flags(ItemFlag.HIDE_ATTRIBUTES);
        }
        enchantmentMap.forEach(builder::enchant);
        return builder.build();
    }
}
