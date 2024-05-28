package com.toonystank.moodyfishing.listener;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.rewards.Reward;
import com.toonystank.moodyfishing.rewards.data.RewardSection;
import com.toonystank.moodyfishing.rewards.services.RewardService;
import com.toonystank.moodyfishing.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Set;

public class FishingListener implements Listener {
    private final RewardService rewardService;
    private final MoodyFishing plugin;

    public FishingListener(RewardService rewardService, MoodyFishing plugin) {
        this.rewardService = rewardService;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        if (event.getCaught() == null) return;
        MessageUtils.toConsole("caught something " + event.getCaught().getName(),true);
        String worldName = event.getPlayer().getWorld().getName();
        RegionManager regionManager = plugin.getRegionManager(worldName);
        if (regionManager == null) return;
        MessageUtils.toConsole("RegionsManager is not null",true);
        Location location = event.getCaught().getLocation();
        Set<ProtectedRegion> regions = regionManager.getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ())).getRegions();
        MessageUtils.toConsole("getting regions of the location " + location.toString() + " from regions " + regions.size(),true);
        for (Reward reward : plugin.getRewardManager().getRewardMap().values()) {
            ProtectedRegion region = regionManager.getRegion(reward.getRewardDataSection().getRegionSpecificRegion());
            if (region == null) continue;
            MessageUtils.toConsole("looping rewards " + reward.getRewardConfig().fileName + " found region " + region.getId(),true);
            MessageUtils.toConsole("location check for " + reward.getRewardConfig().fileName + " " + region.contains(location.getBlockX(),location.getBlockY(),location.getBlockZ()),true);
            if (!region.contains(location.getBlockX(),location.getBlockY(),location.getBlockZ())) continue;
            if (reward.getRewardDataSection().isRegionSpecificPermissionMode()) {
                MessageUtils.toConsole("permission mode enabled",true);
                if (!event.getPlayer().hasPermission("MoodyFishing." + region.getId() + ".fish")) {
                    MessageUtils.sendMessage(event.getPlayer(), MoodyFishing.getInstance().getPrefix() + MoodyFishing.getInstance().getLanguage().getNoPermissionFishing());
                    continue;
                }
            }
            if (reward.getRewardDataSection().isRegionSpecificRemoveVanillaLoot()) event.getCaught().remove();
            MessageUtils.toConsole("getting rewardSection",true);
            RewardSection rewardSection = plugin.getChanceSystem().getReward(region.getId());
            MessageUtils.toConsole(rewardSection + " rewardSection",true);
            if (rewardSection == null) continue;
            rewardService.giveRewards(event, rewardSection);
        }
    }
}
