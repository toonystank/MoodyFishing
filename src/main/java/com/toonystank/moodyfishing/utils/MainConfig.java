package com.toonystank.moodyfishing.utils;

import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MainConfig extends ConfigManger{

    private List<String> rewards = new ArrayList<>();

    private boolean debugMode;
    private static final List<String> fileNames = List.of("example_reward.yml","example_reward_2.yml", "example_reward_3.yml");
    public MainConfig() throws IOException {
        super("config.yml",false,true);
        load();
    }

    public void load() throws IOException {
        debugMode = getBoolean("debug_mode");
        rewards = getStringList("RewardFiles");
        for (String reward : rewards) {
            if (fileNames.contains(reward)) {
                new ConfigManger(reward,"rewards",false,true);
            }
        }
    }
    public void reload() throws Exception {
        rewards.clear();
        super.reload();
        load();
    }

}
