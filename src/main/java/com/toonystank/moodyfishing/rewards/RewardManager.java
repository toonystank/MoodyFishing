package com.toonystank.moodyfishing.rewards;

import com.toonystank.moodyfishing.MoodyFishing;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RewardManager {

    private final Map<String,Reward> rewardMap = new HashMap<>();

    public RewardManager() throws IOException {
        List<String> rewardFiles = MoodyFishing.getInstance().getMainConfig().getStringList("RewardFiles");
        for (String rewardFile : rewardFiles) {
            Reward reward = new Reward(MoodyFishing.getInstance(),rewardFile);
            rewardMap.put(reward.getRewardDataSection().getRegionSpecificRegion(),reward);
        }

    }

    public Reward getReward(String regionID) {
        return rewardMap.get(regionID);
    }


}
