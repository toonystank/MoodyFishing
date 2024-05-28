package com.toonystank.moodyfishing.utils;

import com.toonystank.moodyfishing.rewards.data.RewardSection;

import java.util.*;

public class ChanceSystem {
    private NavigableMap<Integer, List<ChanceDataHolder>> chanceMap;
    private Random random;
    private int totalWeight;

    public ChanceSystem() {
        this.chanceMap = new TreeMap<>();
        this.random = new Random();
        this.totalWeight = 0;
    }

    public void reload() {
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
    public synchronized void addReward(int chance, RewardSection reward, String regionID) {
        if (chance < 0 || chance > 100) {
            throw new IllegalArgumentException("Chance must be between 0 and 100");
        }
        ChanceDataHolder dataHolder = new ChanceDataHolder(regionID, chance, reward);
        chanceMap.computeIfAbsent(totalWeight, k -> new ArrayList<>()).add(dataHolder);
        totalWeight += chance;
    }

    /**
     * Attempts to retrieve a reward based on the stored chances.
     *
     * @param regionID The region ID to look up.
     * @return The reward section if one is found, otherwise null.
     */
    public synchronized RewardSection getReward(String regionID) {
        if (regionID == null || chanceMap.isEmpty()) {
            return null;
        }
        int randomValue = random.nextInt(101);  // Generate a value between 0 and 100 (inclusive)
        int cumulativeChance = 0;

        for (Map.Entry<Integer, List<ChanceDataHolder>> entry : chanceMap.entrySet()) {
            for (ChanceDataHolder dataHolder : entry.getValue()) {
                if (dataHolder.regionID().equals(regionID)) {
                    cumulativeChance += dataHolder.chance();
                    MessageUtils.toConsole("chance system is running " +randomValue + " cumlative chance " + cumulativeChance + " and item chance " + dataHolder.chance );
                    if (randomValue < cumulativeChance) {
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
    public synchronized void clearChances() {
        chanceMap.clear();
        totalWeight = 0;
    }

    private static record ChanceDataHolder(String regionID, int chance, RewardSection rewardSection) {
    }
}