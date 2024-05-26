package com.toonystank.moodyfishing.data;

import com.toonystank.moodyfishing.data.playerdata.FishingPlayer;
import com.toonystank.moodyfishing.utils.ConfigManger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;

public class PlayerDataManager extends ConfigManger {

    private final Plugin plugin;
    public static Map<UUID, FishingPlayer> playerUuidMap = new HashMap<>();
    public static Map<String, UUID> playerNameMap = new HashMap<>();
    public static PlayerDataManager staticInstance;

    public PlayerDataManager(Plugin plugin) throws IOException {
        super("playerdata.yml","data",false,false);
        this.plugin = plugin;
        staticInstance = this;
    }

    public void firstLoad() {
        Arrays.stream(plugin.getServer().getOfflinePlayers()).forEach(offlinePlayer -> {
            FishingPlayer player = new FishingPlayer(offlinePlayer, false);
            addPlayer(player, false);
        });
    }
    public void load() throws IOException {
        firstLoad();
    }

    public static boolean addPlayer(FishingPlayer offlinePlayer, boolean bypassCheck) {
        if (!bypassCheck && (playerUuidMap.containsKey(offlinePlayer.getUuid()) && playerNameMap.containsKey(offlinePlayer.getName().toLowerCase()))) {
            return false;
        }
        playerUuidMap.put(offlinePlayer.getUuid(), offlinePlayer);
        String name = offlinePlayer.getName();
        if (name == null) {
            OfflinePlayer bukkitOfflinePlayer = Bukkit.getOfflinePlayer(offlinePlayer.getUuid());
            if (bukkitOfflinePlayer.getName() != null) {
                name = bukkitOfflinePlayer.getName();
            }else {
                name = "someone-(" + offlinePlayer.getUuid() + ")";
            }
        }
        playerNameMap.put(name.toLowerCase(), offlinePlayer.getUuid());
        return true;
    }

    public @Nullable static FishingPlayer getPlayer(String name) {
        if (name == null) return null;
        FishingPlayer player = null;
        if (playerNameMap.containsKey(name.toLowerCase())) {
            player = playerUuidMap.get(playerNameMap.get(name.toLowerCase()));
            if (player == null || player.getUuid() == null || player.getName() == null) {
                Player onlinePlayer = Bukkit.getPlayer(name);
                if (onlinePlayer != null) {
                    FishingPlayer newPlayer = new FishingPlayer(onlinePlayer,false);
                    addPlayer(newPlayer,true);
                    player = newPlayer;
                }
            }
        }
        else {
            Player onlinePlayer = Bukkit.getPlayer(name);
            if (onlinePlayer != null) {
                FishingPlayer newPlayer = new FishingPlayer(onlinePlayer,false);
                addPlayer(newPlayer,true);
                player = newPlayer;
            }
        }
        return player;
    }
    public static FishingPlayer getPlayer(UUID uuid) {
        if (uuid == null) return null;
        FishingPlayer player = null;
        if (playerUuidMap.containsKey(uuid)) {
            player =playerUuidMap.get(uuid);
            if (player == null || player.getUuid() == null || player.getName() == null) {
                Player onlinePlayer = Bukkit.getPlayer(uuid);
                if (onlinePlayer != null) {
                    FishingPlayer newPlayer = new FishingPlayer(onlinePlayer, false);
                    addPlayer(newPlayer,true);
                    player = newPlayer;
                }
            }
        }
        else {
            Player onlinePlayer = Bukkit.getPlayer(uuid);
            if (onlinePlayer != null) {
                FishingPlayer newPlayer = new FishingPlayer(onlinePlayer,false);
                addPlayer(newPlayer,true);
                player = newPlayer;
            }
        }

        return player;
    }
    public static List<FishingPlayer> getPlayers() {
        return playerUuidMap.values().stream().toList();
    }
}
