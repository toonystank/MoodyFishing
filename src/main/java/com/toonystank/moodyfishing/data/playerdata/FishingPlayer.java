package com.toonystank.moodyfishing.data.playerdata;


import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.data.PlayerDataManager;
import com.toonystank.moodyfishing.utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

@Getter
public class FishingPlayer {

    private final String name;
    private final UUID uuid;
    private final boolean hasPlayedBefore;
    @Setter
    private boolean savedToConfig;
    private final PlayerDataManager playerDataManager;
    public FishingPlayer(String name, UUID uuid, boolean hasPlayedBefore, boolean savedToConfig) {
        this.name = name;
        this.uuid = uuid;
        this.hasPlayedBefore = hasPlayedBefore;
        this.savedToConfig = savedToConfig;
        playerDataManager = PlayerDataManager.staticInstance;
    }
    public FishingPlayer(OfflinePlayer player, boolean savedToConfig) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.hasPlayedBefore = player.hasPlayedBefore();
        this.savedToConfig = savedToConfig;
        playerDataManager = PlayerDataManager.staticInstance;
    }

    public FishingPlayer(Player player, boolean savedToConfig) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.hasPlayedBefore = player.hasPlayedBefore();
        this.savedToConfig = savedToConfig;
        playerDataManager = PlayerDataManager.staticInstance;
    }

    public OfflinePlayer getPlayer() {
        OfflinePlayer offlinePlayer = MoodyFishing.getInstance().getServer().getPlayer(uuid);
        if (offlinePlayer == null) {
            offlinePlayer = MoodyFishing.getInstance().getServer().getOfflinePlayer(uuid);
        }
        return offlinePlayer;
    }
    public Player getOnlinePlayer() throws NullPointerException {
        Player player = MoodyFishing.getInstance().getServer().getPlayer(uuid);
        if (player == null) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getUniqueId().equals(uuid)) player = onlinePlayer;
            }
        }
        return player;
    }

    public boolean isOnline() {
        if (getOnlinePlayer() == null) return false;
        return getOnlinePlayer().isOnline();
    }
    public void sendMessage(String message) {
        MessageUtils.sendMessage(this,message);
    }
    public void sendMessage(String message,boolean title,boolean alwaysTrueNoUseCase) {
        MessageUtils.sendMessage(this,message,title);
    }
    public void sendMessage(String message,boolean includePrefix) {
        message = MoodyFishing.getInstance().getPrefix() + message;
        sendMessage(message);
    }
    public boolean isVanished() {
        return isVanished(this);
    }

    private boolean isVanished(FishingPlayer fishingPlayer) {
        Player player = fishingPlayer.getOnlinePlayer();
        if (player == null) return false;
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
    public boolean isAllowedFlight() {
        try {
            return this.getOnlinePlayer().getAllowFlight();
        }catch (IllegalArgumentException e) {
            return false;
        }
    }

}
