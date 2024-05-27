package com.toonystank.moodyfishing.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.toonystank.moodyfishing.MoodyFishing;
import com.toonystank.moodyfishing.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.awt.*;

@CommandAlias("moodyfishing|mf")
public class MainCommand extends BaseCommand {


    public MainCommand() {

    }

    @Subcommand("reload")
    @Description("Reloads MoodyFishing plugin")
    public void onReload(CommandSender commandSender) {
        commandSender.sendMessage(MoodyFishing.getInstance().getPrefix() + " &aReloading plugin...");
        try {
            MoodyFishing.getInstance().getMainConfig().reload();
            MoodyFishing.getInstance().getLanguage().reload();
            MoodyFishing.getInstance().getRewardManager().reload();
            MoodyFishing.getInstance().getCustomItemManager().reload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MessageUtils.sendMessage(commandSender,MoodyFishing.getInstance().getPrefix() + " &aReloaded plugin with &f" + MoodyFishing.getInstance().getRewardManager().getRewardMap().size() + " &aReward Files and &f" + MoodyFishing.getInstance().getCustomItemManager().getCustomItemList().size() + " &aCustom items ");
    }
}
