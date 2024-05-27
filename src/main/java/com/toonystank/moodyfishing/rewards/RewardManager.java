package com.toonystank.moodyfishing.rewards;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.utils.MessageUtils;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class RewardManager {

    private final Map<String,Reward> rewardMap = new HashMap<>();

    public RewardManager() throws IOException {
        load();
    }
    public void load() throws IOException {
        List<String> rewardFiles = MoodyFishing.getInstance().getMainConfig().getRewards();
        for (String rewardFile : rewardFiles) {
            MessageUtils.toConsole(rewardFile + " reward file",true);
            Reward reward = new Reward(MoodyFishing.getInstance(),rewardFile);
            MessageUtils.toConsole("reward " + reward + " reward data " + reward.getRewardDataSection().getRegionSpecificRegion(),true);
            rewardMap.put(reward.getRewardDataSection().getRegionSpecificRegion(),reward);
        }
    }

    public Reward getReward(String regionID) {
        return rewardMap.get(regionID);
    }

    public void reload() throws Exception {
        rewardMap.clear();
        load();
    }


}
