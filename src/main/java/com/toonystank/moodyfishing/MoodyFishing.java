package com.toonystank.moodyfishing;

import com.toonystank.moodyfishing.data.PlayerDataManager;
import com.toonystank.moodyfishing.items.CustomItemManager;
import com.toonystank.moodyfishing.listener.FishingListener;
import com.toonystank.moodyfishing.rewards.RewardManager;
import com.toonystank.moodyfishing.rewards.services.RewardService;
import com.toonystank.moodyfishing.utils.ChanceSystem;
import com.toonystank.moodyfishing.utils.MainConfig;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MoodyFishing extends JavaPlugin {

    @Getter
    private static MoodyFishing instance;
    @Getter
    private MainConfig mainConfig;
    @Getter
    private RewardManager rewardManager;
    @Getter
    private CustomItemManager customItemManager;
    @Getter
    private ChanceSystem chanceSystem;
    @Getter
    private BukkitAudiences adventure;
    @Getter
    private RewardService rewardService;


    @Override
    public void onEnable() {
        instance = this;
        chanceSystem = new ChanceSystem();
        adventure = BukkitAudiences.builder(this).build();
        try {
            mainConfig = new MainConfig(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rewardService = new RewardService(this);
        try {
            customItemManager = new CustomItemManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            rewardManager = new RewardManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FishingListener fishingListener = new FishingListener();
        this.getServer().getPluginManager().registerEvents(fishingListener,this);
        try {
            new PlayerDataManager(this).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPrefix() {
        return " ";
    }
}
