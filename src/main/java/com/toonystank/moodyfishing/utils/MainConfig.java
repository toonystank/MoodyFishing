package com.toonystank.moodyfishing.utils;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.rewards.Reward;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainConfig extends ConfigManger{

    private final MoodyFishing plugin;

    private Set<Reward> rewards = new HashSet<>();
    public MainConfig(MoodyFishing plugin) throws IOException {
        super("config.yml",false,true);
        this.plugin = plugin;
    }

}
