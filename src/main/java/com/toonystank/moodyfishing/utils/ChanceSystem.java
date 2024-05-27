package com.toonystank.moodyfishing.utils;

import com.toonystank.moodyfishing.rewards.data.RewardSection;

import java.util.*;

public class ChanceSystem {

    private final TreeMap<Integer, List<ChanceDataHolder>> chanceMap;
    private final Random random;
    private int totalWeight;

    public ChanceSystem() {
        this.chanceMap = new TreeMap<>();
        this.random = new Random();
        this.totalWeight = 0;
    }

    /**
     * Adds a reward with its associated chance.
     *
     * @param chance     The chance of receiving the reward (0-100).
     * @param reward     The reward section.
     * @param regionID   The region ID associated with the reward.
     */
    public void addReward(int chance, RewardSection reward, String regionID) {
        if (chance < 0 || chance > 100) {
            throw new IllegalArgumentException("Chance must be between 0 and 100");
        }
        ChanceDataHolder dataHolder = new ChanceDataHolder(regionID, chance, reward);
        chanceMap.computeIfAbsent(chance, k -> new ArrayList<>()).add(dataHolder);
        totalWeight += chance;
    }

    /**
     * Attempts to retrieve a reward based on the stored chances.
     *
     * @param regionID The region ID to look up.
     * @return The reward section if one is found, otherwise null.
     */
    public RewardSection getReward(String regionID) {
        if (regionID == null || chanceMap.isEmpty()) {
            return null;
        }
        int randomValue = random.nextInt(totalWeight) + 1;
        int cumulativeChance = 0;

        for (Map.Entry<Integer, List<ChanceDataHolder>> entry : chanceMap.entrySet()) {
            cumulativeChance += entry.getKey();
            if (randomValue <= cumulativeChance) {
                List<ChanceDataHolder> dataHolders = entry.getValue();
                for (ChanceDataHolder dataHolder : dataHolders) {
                    if (dataHolder.regionID().equals(regionID)) {
                        return dataHolder.rewardSection();
                    }
                }
            }
        }
        return null; // No reward if no chances met
    }

    /**
     * Clears the chance map.
     */
    public void clearChances() {
        chanceMap.clear();
        totalWeight = 0;
    }

    private record ChanceDataHolder(String regionID, int chance, RewardSection rewardSection) {
    }
}
