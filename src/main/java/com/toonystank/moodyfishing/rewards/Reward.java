package com.toonystank.moodyfishing.rewards;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.rewards.data.RewardDataSection;
import com.toonystank.moodyfishing.rewards.data.RewardList;
import com.toonystank.moodyfishing.rewards.data.RewardSection;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Reward {

    private final MoodyFishing plugin;
    private final RewardConfig rewardConfig;
    private RewardDataSection rewardDataSection;
    private final List<RewardSection> rewardSections = new ArrayList<>();

    public Reward(MoodyFishing plugin, String fileName) throws IOException {
        this.plugin = plugin;
        this.rewardConfig = new RewardConfig(fileName);
        load(); // Automatically load on creation
    }

    private void load() throws IOException {
        boolean regionSpecificEnabled = rewardConfig.getBoolean("data.Region_specific.enable");
        String rewardSpecificRegion = rewardConfig.getString("data.Region_specific.region");
        rewardDataSection = new RewardDataSection(regionSpecificEnabled, rewardSpecificRegion);

        for (String item : rewardConfig.getConfigurationSection("reward", false, true)) {
            loadRewardItem(item);
        }
    }

    private void loadRewardItem(String item) {
        String itemPath = "reward." + item;
        String name = rewardConfig.getString(itemPath + ".Name");
        Bukkit.getLogger().info(name);
        List<String> lore = rewardConfig.getStringList(itemPath + ".lore");
        Bukkit.getLogger().info(lore.toString());
        List<RewardList> rewardList = loadRewardList(itemPath);
        List<String> rewardCommands = rewardConfig.getStringList(itemPath + ".reward_command");
        boolean rewardMessageEnable = rewardConfig.getBoolean(itemPath + ".reward_message.enable");
        List<String> rewardBroadcastMessage = rewardConfig.getStringList(itemPath + ".reward_message.broadcast_message");
        List<String> rewardMessage = rewardConfig.getStringList(itemPath + ".reward_message.message");
        RewardSection rewardSection = new RewardSection(item, name, lore, rewardList, rewardCommands, rewardMessageEnable, rewardBroadcastMessage, rewardMessage);
        rewardSections.add(rewardSection);
        MoodyFishing.getInstance().getChanceSystem().addReward(Integer.parseInt(item),rewardSection,item);
    }

    private List<RewardList> loadRewardList(String itemPath) {
        List<RewardList> rewardList = new ArrayList<>();
        for (String reward : rewardConfig.getStringList(itemPath + ".rewards")) {
            Bukkit.getLogger().info("reward " + reward);
            rewardList.add(parseReward(reward));
        }
        return rewardList;
    }

    private RewardList parseReward(String reward) {
        String rewardType = null;
        if (reward.contains("Minecraft")) {
            rewardType = "Minecraft";
            reward = reward.replace("Minecraft:", "");
        }
        if (reward.contains("Reward")) {
            rewardType = "Reward";
            reward = reward.replace("Reward:", "");
        }
        Bukkit.getLogger().info("formatted reward " + reward);
        String[] rewardWithSize = reward.contains(":") ? reward.split(":") : new String[]{reward, "1"};
        String rewardName = rewardWithSize[0];
        int rewardSize = Integer.parseInt(rewardWithSize[1]);
        Bukkit.getLogger().info(rewardSize + " rewardSize");

        return new RewardList(rewardType, rewardName, rewardSize);
    }
}