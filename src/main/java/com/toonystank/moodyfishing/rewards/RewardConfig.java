package com.toonystank.moodyfishing.rewards;

import com.toonystank.moodyfishing.utils.ConfigManger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RewardConfig extends ConfigManger {
    public RewardConfig(String fileName) throws IOException {
        super(fileName, "rewards",false,false);
        initDefault();
    }


    public void initDefault() throws IOException {
        // Setting region-specific settings
        getConfig().addDefault("data.Region_specific.enable", true);
        getConfig().addDefault("data.Region_specific.region", "<REGION_NAME_HERE>");
        setComments("data.Region_specific", Arrays.asList(
                "Enable or disable region-specific rewards",
                "Specify the region where the rewards can be obtained"
        ));

        // 20% chance reward
        getConfig().addDefault("reward.20.Name", "20% Chance Reward");
        getConfig().addDefault("reward.20.lore", Arrays.asList(
                "This is a rare reward",
                "Chance to get powerful items",
                "Good luck!"
        ));
        getConfig().addDefault("reward.20.rewards", Arrays.asList(
                "DIAMOND:20",
                "Reward:enchanted_bow:2",
                "STONE:100",
                "EMERALD:5",
                "GOLD_INGOT:10"
        ));
        getConfig().addDefault("reward.20.reward_command", Arrays.asList(
                "essentials:give {playername} stone 100",
                "essentials:give {playername} Diamond_pickaxe 1"
        ));
        getConfig().addDefault("reward.20.reward_message.enable", true);
        getConfig().addDefault("reward.20.reward_message.broadcast_message", Arrays.asList(
                "[TITLE] player {playername} got reward {reward}",
                "[SUBTITLE] reward {reward}",
                "[ACTION] reward action bar",
                "player {playername} got reward"
        ));
        getConfig().addDefault("reward.20.reward_message.message", Arrays.asList(
                "[TITLE] You got 20% reward",
                "[SUBTITLE] This is a sub title",
                "[ACTION] this is action bar message",
                "This is chat message"
        ));
        setComments("reward.20", Arrays.asList(
                "Settings for the 20% chance reward",
                "This reward contains valuable items"
        ));

        // 50% chance reward
        getConfig().addDefault("reward.50.Name", "50% Chance Reward");
        getConfig().addDefault("reward.50.lore", Arrays.asList(
                "A common reward",
                "Useful for everyday items"
        ));
        getConfig().addDefault("reward.50.rewards", Arrays.asList(
                "IRON_INGOT:50",
                "COAL:200",
                "LAPIS_LAZULI:10",
                "IRON_SWORD:1"
        ));
        getConfig().addDefault("reward.50.reward_command", Arrays.asList(
                "essentials:give {playername} iron_ingot 50",
                "essentials:give {playername} iron_sword 1"
        ));
        getConfig().addDefault("reward.50.reward_message.enable", true);
        getConfig().addDefault("reward.50.reward_message.broadcast_message", Arrays.asList(
                "[TITLE] player {playername} got a common reward",
                "[ACTION] common reward action bar"
        ));
        getConfig().addDefault("reward.50.reward_message.message", Arrays.asList(
                "[TITLE] You got a 50% reward",
                "[ACTION] Useful items received!"
        ));
        setComments("reward.50", Arrays.asList(
                "Settings for the 50% chance reward",
                "This reward contains common, useful items"
        ));

        // 10% chance reward
        getConfig().addDefault("reward.10.Name", "10% Chance Reward");
        getConfig().addDefault("reward.10.lore", Arrays.asList(
                "A very rare reward",
                "Contains valuable treasures",
                "Good luck, adventurer!"
        ));
        getConfig().addDefault("reward.10.rewards", Arrays.asList(
                "DIAMOND_BLOCK:1",
                "Reward:mystical_helmet:1",
                "EMERALD_BLOCK:2",
                "GOLDEN_APPLE:5",
                "NETHER_STAR:1"
        ));
        getConfig().addDefault("reward.10.reward_command", Arrays.asList(
                "essentials:give {playername} diamond_block 1",
                "essentials:give {playername} mystical_helmet 1"
        ));
        getConfig().addDefault("reward.10.reward_message.enable", true);
        getConfig().addDefault("reward.10.reward_message.broadcast_message", Arrays.asList(
                "[TITLE] player {playername} got a rare reward!",
                "[SUBTITLE] reward {reward}",
                "[ACTION] rare reward action bar",
                "player {playername} got a rare reward"
        ));
        getConfig().addDefault("reward.10.reward_message.message", Arrays.asList(
                "[TITLE] You got a 10% reward",
                "[SUBTITLE] Enchanted items!",
                "[ACTION] Rare items received!"
        ));
        setComments("reward.10", Arrays.asList(
                "Settings for the 10% chance reward",
                "This reward contains very rare, valuable items"
        ));

        // 70% chance reward
        getConfig().addDefault("reward.70.Name", "70% Chance Reward");
        getConfig().addDefault("reward.70.lore", Arrays.asList(
                "A common reward",
                "Still worth having",
                "Enjoy your items!"
        ));
        getConfig().addDefault("reward.70.rewards", Arrays.asList(
                "STONE:50",
                "COAL:30",
                "IRON_INGOT:10",
                "LAPIS_LAZULI:20",
                "WOOD:100"
        ));
        getConfig().addDefault("reward.70.reward_command", Arrays.asList(
                "essentials:give {playername} stone 50",
                "essentials:give {playername} coal 30"
        ));
        getConfig().addDefault("reward.70.reward_message.enable", true);
        getConfig().addDefault("reward.70.reward_message.broadcast_message", Arrays.asList(
                "[TITLE] player {playername} got a common reward!",
                "[SUBTITLE] reward {reward}",
                "[ACTION] common reward action bar"
        ));
        getConfig().addDefault("reward.70.reward_message.message", Arrays.asList(
                "[TITLE] You got a 70% reward",
                "[SUBTITLE] Common items!",
                "[ACTION] Enjoy your items!"
        ));
        setComments("reward.70", Arrays.asList(
                "Settings for the 70% chance reward",
                "This reward contains common items"
        ));

        // 5% chance reward
        getConfig().addDefault("reward.5.Name", "5% Chance Reward");
        getConfig().addDefault("reward.5.lore", Arrays.asList(
                "An extremely rare reward",
                "Contains legendary items",
                "Best of luck!"
        ));
        getConfig().addDefault("reward.5.rewards", Arrays.asList(
                "DIAMOND_SWORD:1",
                "Reward:best_diamond_sword:1",
                "NETHERITE_INGOT:3",
                "DRAGON_EGG:1",
                "TOTEM_OF_UNDYING:2"
        ));
        getConfig().addDefault("reward.5.reward_command", Arrays.asList(
                "essentials:give {playername} diamond_sword 1",
                "essentials:give {playername} best_diamond_sword 1"
        ));
        getConfig().addDefault("reward.5.reward_message.enable", true);
        getConfig().addDefault("reward.5.reward_message.broadcast_message", Arrays.asList(
                "[TITLE] player {playername} got an epic reward!",
                "[SUBTITLE] reward {reward}",
                "[ACTION] epic reward action bar",
                "player {playername} got an epic reward"
        ));
        getConfig().addDefault("reward.5.reward_message.message", Arrays.asList(
                "[TITLE] You got a 5% reward",
                "[SUBTITLE] Legendary items!",
                "[ACTION] Epic items received!"
        ));
        setComments("reward.5", Arrays.asList(
                "Settings for the 5% chance reward",
                "This reward contains extremely rare and legendary items"
        ));
        save();
    }

    public void setComments(@NotNull String path, @Nullable List<String> comments) {
        if (comments == null || comments.isEmpty()) return;
        getConfig().setComments(path, comments);
    }

}
