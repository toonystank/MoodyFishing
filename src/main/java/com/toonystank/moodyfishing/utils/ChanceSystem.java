package com.toonystank.moodyfishing.utils;

import com.toonystank.moodyfishing.rewards.data.RewardSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChanceSystem {

    private final Map<Integer, ChanceDataHolder> chanceMap;
    private final Random random;

    public ChanceSystem() {
        this.chanceMap = new HashMap<>();
        this.random = new Random();
    }

    // Add a reward with its chance
    public void addReward(int chance, RewardSection reward,String regionID) {
        if (chance < 0 || chance > 100) {
            throw new IllegalArgumentException("Chance must be between 0 and 100");
        }
        ChanceDataHolder dataHolder = new ChanceDataHolder(regionID,chance,reward);
        chanceMap.put(chance, dataHolder);

    }

    // Try to get a reward based on the chance map
    public RewardSection getReward(String id) {

        if (id == null) return null;
        for (Map.Entry<Integer, ChanceDataHolder> entry : chanceMap.entrySet()) {
            if (random.nextInt(100) < entry.getKey()) {

                return entry.getValue().rewardSection();
            }
        }
        return null; // No reward if no chances met
    }

    // Clear the chance map
    public void clearChances() {
        chanceMap.clear();
    }
}
