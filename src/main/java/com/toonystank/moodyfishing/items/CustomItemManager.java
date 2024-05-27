package com.toonystank.moodyfishing.items;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.utils.ConfigManger;
import com.toonystank.moodyfishing.utils.MessageUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;
import java.util.*;

@Getter
public class CustomItemManager extends ConfigManger {

    private final List<CustomItem> customItemList = new ArrayList<>();

    public CustomItemManager() throws Exception {
        super("Items.yml",false,true);
        MoodyFishing.getInstance().getServer().getScheduler().runTaskLater(MoodyFishing.getInstance(),() -> {
            try {
                loadItems();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        },60L);
    }
    public void reload() throws Exception {
        customItemList.clear();
        super.reload();
        loadItems();

    }

    public CustomItem getItem(String name) {
        for (CustomItem customItem : customItemList) {
            if (customItem.id().equalsIgnoreCase(name)) return customItem;
        }
        return null;
    }

    public void loadItems() throws Exception {
        Set<String> items = getConfigurationSection("items", false, true);
        for (String item : items) {
            String itemPath = "items." + item;
            Material material = Material.getMaterial(getString(itemPath + ".material"));

            if (material == null) {
                throw new IllegalArgumentException("Material in item id " + item + " is not a valid minecraft item");
            }

            int modelData = getInt(itemPath + ".model_data");
            String itemName = getString(itemPath + ".Item_Name");
            List<String> lore = getStringList(itemPath + ".lore");
            boolean hideAttributes = getBoolean(itemPath + ".hide_attributes");
            boolean unbreakable = getBoolean(itemPath + ".unbreakable");

            Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap<>();
            List<String> enchantments = getStringList(itemPath + ".enchantment");

            for (String enchantmentList : enchantments) {
                String[] enchantmentAndLevel = enchantmentList.contains(":") ? enchantmentList.split(":") : new String[]{enchantmentList, "1"};
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentAndLevel[0]));

                if (enchantment == null) {
                    throw new IllegalArgumentException("Enchantment " + enchantmentAndLevel[0] + " in item id " + item + " is not a valid minecraft enchantment");
                }

                enchantmentIntegerMap.put(enchantment, Integer.parseInt(enchantmentAndLevel[1]));
            }

            CustomItem customItem = new CustomItem(item, material, modelData, itemName, lore, hideAttributes, unbreakable, enchantmentIntegerMap);
            customItemList.add(customItem);
        }
    }

}
