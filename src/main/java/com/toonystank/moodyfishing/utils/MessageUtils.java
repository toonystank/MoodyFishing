package com.toonystank.moodyfishing.utils;

import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.data.playerdata.FishingPlayer;
import de.themoep.minedown.adventure.MineDown;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MessageUtils {

    public static void sendMessage(List<FishingPlayer> sender, String message) {
        if (sender.isEmpty()) return;

        Set<FishingPlayer> playersSentMessage = new HashSet<>(); // Create set to track sent players

        for (FishingPlayer FishingPlayer : sender) {
            if (FishingPlayer == null || FishingPlayer.getOnlinePlayer() == null || playersSentMessage.contains(FishingPlayer)) {
                continue; // Skip null players or players already sent message
            }

            sendMessage(FishingPlayer.getOnlinePlayer(), message);
            playersSentMessage.add(FishingPlayer); // Mark player as sent
        }
    }

    public static void sendMessage(FishingPlayer sender, String message) {
        if (!sender.getPlayer().isOnline()) return;
        sendMessage(sender.getOnlinePlayer(),message);
    }
    public static void sendMessage(List<FishingPlayer> sender, String message,boolean titleMessage) {
        if (sender.isEmpty()) return;

        Set<FishingPlayer> playersSentMessage = new HashSet<>(); // Create set to track sent players

        for (FishingPlayer FishingPlayer : sender) {
            if (FishingPlayer == null || FishingPlayer.getOnlinePlayer() == null || playersSentMessage.contains(FishingPlayer)) {
                continue; // Skip null players or players already sent message
            }

            sendMessage(FishingPlayer.getOnlinePlayer(), message, titleMessage);
            playersSentMessage.add(FishingPlayer); // Mark player as sent
        }
    }
    public static void sendMessage(FishingPlayer sender, String message,boolean titleMessage) {
        if (!sender.getPlayer().isOnline()) return;
        sendMessage(sender.getOnlinePlayer(),message,titleMessage);
    }
    public static void sendMessage(Player sender, String message,boolean titleMessage) {
        sendMessage((CommandSender) sender, message,titleMessage);
    }
    public static void sendMessage(CommandSender sender, String message,boolean titleMessage) {
        if (!titleMessage) {
            sendMessage(sender,message);
            return;
        }
        if (!(sender instanceof Player player)) {
            sendMessage(sender,message);
            return;
        }
        Title title = Title.title(format(message),Component.empty());
        player.showTitle(title);
    }
    public static void sendMessage(Player sender, String message) {
        sendMessage((CommandSender) sender, message);
    }
    public static void sendMessage(CommandSender sender, String message) {
        Component component = new MineDown(message).toComponent();
        component = component.decoration(TextDecoration.ITALIC, false);
        MoodyFishing.getInstance().getAdventure().sender(sender).sendMessage(component);
    }
    public static void sendMessage(FishingPlayer sender, Component message) {
        if (!sender.getPlayer().isOnline()) return;
        sendMessage(sender.getOnlinePlayer(),message);
    }
    public static void sendMessage(Player sender, Component message) {
        sendMessage((CommandSender) sender, message);
    }
    public static void sendMessage(CommandSender sender, Component message) {
        message = message.decoration(TextDecoration.ITALIC, false);
        MoodyFishing.getInstance().getAdventure().sender(sender).sendMessage(message);
    }
    public static @NotNull Component format(String message) {
        Component component = new MineDown(message).toComponent();
        component = component.decoration(TextDecoration.ITALIC, false);
        return component;
    }

    public static @NotNull List<Component> format(List<String> stringsToBeFormatted) {
        return stringsToBeFormatted.stream().map(MessageUtils::format).toList();
    }
    public static @NotNull Component format(String message,boolean smallFont) {
        if (smallFont) message = SmallLetterConvertor.convert(message);
        Component component = new MineDown(message).toComponent();
        component = component.decoration(TextDecoration.ITALIC, false);
        return component;
    }
    public static void toConsole(List<String> list, boolean string) {
        list.forEach(message -> toConsole(message,false));
    }
    public static void toConsole(List<Component> list) {
        list.forEach(component -> toConsole(component, false));
    }
    public static void toConsole(String message, boolean debug) {
        message = "&a[MoodyFishing] &r" + message;
        Component component = new MineDown(message).toComponent();
        toConsole(component, debug);
    }
    public static void toConsole(Component component, boolean debug) {
        component = component.decoration(TextDecoration.ITALIC,false);
        MessageUtils.toConsole("sending Message to console ",true);
        MoodyFishing.getInstance().getAdventure().sender(MoodyFishing.getInstance().getServer().getConsoleSender()).sendMessage(component);
    }
    public static void error(String message) {
        message = message + ". Server version: " + MoodyFishing.getInstance().getServer().getVersion() + ". Plugin version: " + MoodyFishing.getInstance().getDescription().getVersion() + ". Please report this error to the plugin developer.";
        Component component = new MineDown(message).toComponent();
        error(component);
    }
    public static void error(Component component) {
        try {
            component = component.decoration(TextDecoration.ITALIC, false);
            component = component.color(TextColor.fromHexString("#CF203E"));
            MoodyFishing.getInstance().getAdventure().sender(MoodyFishing.getInstance().getServer().getConsoleSender()).sendMessage(component);
        } catch (NullPointerException ignored) {
            error("an error occurred while sending a message");
        }
    }
    public static void debug(String message) {
        message = message + ". Server version: " + MoodyFishing.getInstance().getServer().getVersion() + ". Plugin version: " + MoodyFishing.getInstance().getDescription().getVersion() + ". To stop receiving this messages please update your config.yml";
        Component component = new MineDown(message).toComponent();
        debug(component);
    }
    public static void debug(Component component) {
        try {
            component = component.decoration(TextDecoration.ITALIC, false);
            MoodyFishing.getInstance().getAdventure().sender(MoodyFishing.getInstance().getServer().getConsoleSender()).sendMessage(component);
        } catch (NullPointerException ignored) {
            error("an error occurred while sending a message");
        }
    }
    public static void warning(String message) {
        Component component = new MineDown(message).toComponent();
        warning(component);
    }
    public static void warning(Component component) {
        component = component.decoration(TextDecoration.ITALIC,false);
        component = component.color(TextColor.fromHexString("#FFC107"));
        MoodyFishing.getInstance().getAdventure().sender(MoodyFishing.getInstance().getServer().getConsoleSender()).sendMessage(component);
    }
    public static String replaceGrayWithWhite(String inputString) {
        if (inputString.contains("&7")) inputString = inputString.replace("&7", "&f");
        return inputString;
    }
}
