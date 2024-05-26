package com.toonystank.moodyfishing.listener;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.items.CustomItem;
import com.toonystank.moodyfishing.rewards.Reward;
import com.toonystank.moodyfishing.rewards.data.RewardList;
import com.toonystank.moodyfishing.rewards.data.RewardSection;
import com.toonystank.moodyfishing.utils.MessageUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingListener implements Listener {


    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)
                || event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            World world = BukkitAdapter.adapt(event.getPlayer().getLocation().getWorld());
            RegionManager regionManager = container.get(world);
            if (regionManager == null) return;
            if (event.getCaught() == null) return;
            Location location = event.getCaught().getLocation();
            for (ProtectedRegion region : regionManager.getApplicableRegions(BlockVector3.at(location.x(), location.y(), location.z())).getRegions()) {

                Reward reward = MoodyFishing.getInstance().getRewardManager().getReward(region.getId());
                if (reward == null) return;
                Bukkit.getLogger().info("found reward for this location " + reward.getRewardDataSection().getRegionSpecificRegion());
                RewardSection rewardSection = MoodyFishing.getInstance().getChanceSystem().getReward(region.getId());
                if (rewardSection == null) {
                    Bukkit.getLogger().info("0 chance");
                    return;
                }
                Bukkit.getLogger().info("loading reward Section " + rewardSection.getId());
                for (RewardList rewardSectionReward : rewardSection.getRewards()) {
                    Bukkit.getLogger().info("looping rewards " + rewardSectionReward.getType() + " " + rewardSectionReward.getReward());
                    ItemStack item;
                    if (rewardSectionReward.getType().equalsIgnoreCase("Reward")) {
                        CustomItem customItem = MoodyFishing.getInstance().getCustomItemManager().getItem(rewardSectionReward.getReward());
                        if (customItem == null) return;
                        item = customItem.build();
                    } else {
                        Material material = Material.getMaterial(rewardSectionReward.getReward());
                        if (material == null) return;
                        item = new ItemStack(material, rewardSectionReward.getAmount());
                    }
                    event.getHook().setHookedEntity(null);
                    event.getPlayer().getInventory().addItem(item);
                    Bukkit.getLogger().info("gave player " + rewardSectionReward.getReward());
                }
                for (String rewardCommand : rewardSection.getRewardCommands()) {
                    rewardCommand = formatString(event.getPlayer(), rewardCommand, rewardSection.getName());
                    Bukkit.getLogger().info("running formatted command " + rewardCommand);
                    MoodyFishing.getInstance().getServer().dispatchCommand(event.getPlayer(), rewardCommand);
                }
                for (String s : rewardSection.getRewardMessage()) {
                    s = formatString(event.getPlayer(), s, rewardSection.getName());
                    Bukkit.getLogger().info("sending player message " + s);
                    event.getPlayer().sendMessage(s);
                }
                for (String s : rewardSection.getRewardBroadcastMessage()) {
                    s = formatString(event.getPlayer(), s, rewardSection.getName());
                    Bukkit.getLogger().info("broadcasting to server " + s);
                    MoodyFishing.getInstance().getServer().broadcast(Component.text(s));
                }
            }
        }
    }
    public String formatString(Player player, String message, String reward) {
        if (message.contains("{playername}")) {
            message = message.replace("{playername}",player.getName());
        }
        if (message.contains("{reward}")) {
            message = message.replace("{reward}",reward);
        }
        message = PlaceholderAPI.setPlaceholders(player,message);
        return message;
    }

}
