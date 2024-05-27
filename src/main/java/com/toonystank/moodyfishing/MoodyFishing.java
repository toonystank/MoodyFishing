package com.toonystank.moodyfishing;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.toonystank.moodyfishing.commands.MainCommand;
import com.toonystank.moodyfishing.data.PlayerDataManager;
import com.toonystank.moodyfishing.items.CustomItemManager;
import com.toonystank.moodyfishing.listener.FishingListener;
import com.toonystank.moodyfishing.rewards.RewardManager;
import com.toonystank.moodyfishing.rewards.services.RewardService;
import com.toonystank.moodyfishing.utils.*;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MoodyFishing extends JavaPlugin {

    @Getter
    private static MoodyFishing instance;
    @Getter
    private WorldGuard worldGuard;
    private MainCommand mainCommand;
    private final Map<String, World> worlds = new HashMap<>();
    @Getter
    private MainConfig mainConfig;
    @Getter
    private Language language;
    @Getter
    private RewardManager rewardManager;
    @Getter
    private CustomItemManager customItemManager;
    @Getter
    private ChanceSystem chanceSystem;
    @Getter
    private RewardService rewardService;


    @Override
    public void onEnable() {
        instance = this;
        boolean isOnPaper;
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isOnPaper = true;
        } catch (ClassNotFoundException e) {
            isOnPaper = false;
        }
        chanceSystem = new ChanceSystem();
        worldGuard = WorldGuard.getInstance();
        mainCommand = new MainCommand();
        if (isOnPaper) {
            MessageUtils.toConsole("Paper detected, using PaperCommandManager");
            new PaperCommandManager(this).registerCommand(mainCommand, true);

        } else {
            MessageUtils.toConsole("Paper not detected, using BukkitCommandManager");
            new BukkitCommandManager(this).registerCommand(mainCommand, true);
        }
        // Initialize worlds
        for (org.bukkit.World bukkitWorld : this.getServer().getWorlds()) {
            worlds.put(bukkitWorld.getName(), BukkitAdapter.adapt(bukkitWorld));
        }
        try {
            mainConfig = new MainConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            language = new Language();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        SmallLetterConvertor.isEnabled(language.isEnableAutoTextConversionToTinyText());
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

        FishingListener fishingListener = new FishingListener(rewardService,this);
        this.getServer().getPluginManager().registerEvents(fishingListener,this);
        try {
            new PlayerDataManager(this).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessageUtils.toConsole(LogoManager.getRandomLogo());
    }

    public String getPrefix() {
        return language.getPrefix();
    }

    public World getWorld(String worldName) {
        return worlds.get(worldName);
    }

    public RegionManager getRegionManager(String worldName) {
        World world = getWorld(worldName);
        return worldGuard.getPlatform().getRegionContainer().get(world);
    }
}
