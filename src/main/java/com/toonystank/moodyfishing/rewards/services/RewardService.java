package com.toonystank.moodyfishing.rewards.services;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.items.CustomItem;
import com.toonystank.moodyfishing.rewards.data.RewardList;
import com.toonystank.moodyfishing.rewards.data.RewardSection;
import com.toonystank.moodyfishing.utils.MessageUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

public class RewardService {

    private final MoodyFishing plugin;

    public RewardService(MoodyFishing plugin) {
        this.plugin = plugin;
    }

    public void giveRewards(PlayerFishEvent event, RewardSection rewardSection) {
        Player player = event.getPlayer();
        for (RewardList rewardSectionReward : rewardSection.getRewards()) {
            ItemStack item = createItem(rewardSectionReward);
            if (item != null) {
                event.getHook().setHookedEntity(null);
                player.getInventory().addItem(item);
                Bukkit.getLogger().info("Gave player " + rewardSectionReward.getReward());
            }
        }
        executeCommands(player, rewardSection);
        sendMessages(player, rewardSection);
    }

    private ItemStack createItem(RewardList rewardSectionReward) {
        if (rewardSectionReward.getType().equalsIgnoreCase("Reward")) {
            CustomItem customItem = plugin.getCustomItemManager().getItem(rewardSectionReward.getReward());
            if (customItem != null) {
                return customItem.build();
            }
        } else {
            Material material = Material.getMaterial(rewardSectionReward.getReward());
            if (material != null) {
                return new ItemStack(material, rewardSectionReward.getAmount());
            }
        }
        return null;
    }

    private void executeCommands(Player player, RewardSection rewardSection) {
        for (String rewardCommand : rewardSection.getRewardCommands()) {
            rewardCommand = formatString(player, rewardCommand, rewardSection.getName());
            Bukkit.getLogger().info("Running formatted command " + rewardCommand);
            plugin.getServer().dispatchCommand(player, rewardCommand);
        }
    }

    private void sendMessages(Player player, RewardSection rewardSection) {
        for (String message : rewardSection.getRewardMessage()) {
            message = formatString(player, message, rewardSection.getName());
            sendFormattedMessage(player, message);
        }
        for (String message : rewardSection.getRewardBroadcastMessage()) {
            message = formatString(player, message, rewardSection.getName());
            sendFormattedBroadcast(message);
        }
    }

    private void sendFormattedMessage(Player player, String message) {
        if (message.startsWith("[TITLE]")) {
            String titleText = message.substring(7).trim();
            Title title = Title.title(MessageUtils.format(titleText), Component.empty(), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1)));
            player.showTitle(title);
        } else if (message.startsWith("[SUBTITLE]")) {
            String subtitleText = message.substring(10).trim();
            Title title = Title.title(Component.empty(), MessageUtils.format(subtitleText), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1)));
            player.showTitle(title);
        } else if (message.startsWith("[ACTION]")) {
            String actionText = message.substring(8).trim();
            player.sendActionBar(MessageUtils.format(actionText));
        } else {
            player.sendMessage(MessageUtils.format(message));
        }
    }

    private void sendFormattedBroadcast(String message) {
        if (message.startsWith("[TITLE]")) {
            String titleText = message.substring(7).trim();
            Title title = Title.title(MessageUtils.format(titleText), Component.empty(), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1)));
            plugin.getServer().showTitle(title);
        } else if (message.startsWith("[SUBTITLE]")) {
            String subtitleText = message.substring(10).trim();
            Title title = Title.title(Component.empty(), MessageUtils.format(subtitleText), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1)));
            plugin.getServer().showTitle(title);
        } else if (message.startsWith("[ACTION]")) {
            String actionText = message.substring(8).trim();
            plugin.getServer().sendActionBar(MessageUtils.format(actionText));
        } else {
            plugin.getServer().broadcast(MessageUtils.format(message));
        }
    }

    private String formatString(Player player, String message, String reward) {
        if (message.contains("{playername}")) {
            message = message.replace("{playername}", player.getName());
        }
        if (message.contains("{reward}")) {
            message = message.replace("{reward}", reward);
        }
        return PlaceholderAPI.setPlaceholders(player, message);
    }
} 