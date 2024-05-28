package com.toonystank.moodyfishing.rewards;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.rewards.data.RewardDataSection;
import com.toonystank.moodyfishing.rewards.data.RewardList;
import com.toonystank.moodyfishing.rewards.data.RewardSection;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Getter
public class Reward {

    private final MoodyFishing plugin;
    private final RewardConfig rewardConfig;
    private RewardDataSection rewardDataSection;
    private final List<RewardSection> rewardSections = new ArrayList<>();

    public Reward(MoodyFishing plugin, String fileName) throws IOException {
        this.plugin = plugin;
        this.rewardConfig = new RewardConfig(fileName);
        load();
    }

    public void reload() throws Exception {
        this.rewardConfig.reload();
        load();
    }

    private void load() throws IOException {
        boolean regionSpecificEnabled = rewardConfig.getBoolean("data.Region_specific.enable");
        String rewardSpecificRegion = rewardConfig.getString("data.Region_specific.region");
        boolean regionSpecificPermissionMode = rewardConfig.getBoolean("data.Region_specific.permission_mode");
        boolean regionSpecificRemoveVanillaLoot = rewardConfig.getBoolean("data.Region_specific.remove_vanilla_loot");

        rewardDataSection = new RewardDataSection(regionSpecificEnabled, rewardSpecificRegion, regionSpecificPermissionMode, regionSpecificRemoveVanillaLoot);

        rewardConfig.getConfigurationSection("reward", false, true).forEach(reward -> {
            try {
                loadRewardItem(reward, rewardSpecificRegion);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load reward: " + reward, e);
            }
        });
    }

    private void loadRewardItem(String item, String region) {
        String itemPath = "reward." + item;
        String name = rewardConfig.getString(itemPath + ".Name");
        List<String> lore = rewardConfig.getStringList(itemPath + ".lore");
        List<RewardList> rewardList = loadRewardList(itemPath);
        List<String> rewardCommands = rewardConfig.getStringList(itemPath + ".reward_command");
        boolean rewardMessageEnable = rewardConfig.getBoolean(itemPath + ".reward_message.enable");
        List<String> rewardBroadcastMessage = rewardConfig.getStringList(itemPath + ".reward_message.broadcast_message");
        List<String> rewardMessage = rewardConfig.getStringList(itemPath + ".reward_message.message");

        RewardSection rewardSection = new RewardSection(
                item, name, lore, rewardList, rewardCommands, rewardMessageEnable, rewardBroadcastMessage, rewardMessage
        );

        rewardSections.add(rewardSection);
        try {
            int chance = Integer.parseInt(item);  // This assumes that the 'item' is the chance value
            MoodyFishing.getInstance().getChanceSystem().addReward(chance, rewardSection, region);
        } catch (NumberFormatException e) {
            plugin.getLogger().log(Level.SEVERE, "Invalid chance value for reward: " + item, e);
        }
    }

    private List<RewardList> loadRewardList(String itemPath) {
        return rewardConfig.getStringList(itemPath + ".rewards").stream()
                .map(this::parseReward)
                .toList();
    }

    private RewardList parseReward(String reward) {
        String rewardType = reward.contains("Minecraft") ? "Minecraft" : "Reward";
        reward = reward.replace(rewardType + ":", "");
        String[] rewardWithSize = reward.contains(":") ? reward.split(":") : new String[]{reward, "1"};
        String rewardName = rewardWithSize[0];
        int rewardSize = Integer.parseInt(rewardWithSize[1]);
        return new RewardList(rewardType, rewardName, rewardSize);
    }
}
