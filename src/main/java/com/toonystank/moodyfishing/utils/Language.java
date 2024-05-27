package com.toonystank.moodyfishing.utils;

import lombok.Getter;

import java.io.IOException;

@Getter
public class Language extends ConfigManger{

    private boolean enableAutoTextConversionToTinyText;
    private String prefix;
    private String noPermissionCommand;
    private String noPermissionFishing;
    private String simulationFishCaught;
    private String simulationGiveReward;

    public Language() throws IOException {
        super("language.yml",false,true);
    }

    public void load() {
        this.enableAutoTextConversionToTinyText = getBoolean("enable_auto_text_conversion_to_tiny_text");
        this.prefix = getString("message.prefix");
        this.noPermissionCommand = getString("message.no_permission_command");
        this.noPermissionFishing = getString("message.no_permission_command");
        this.simulationFishCaught = getString("message.simulation.fish_caught");
        this.simulationGiveReward = getString("message.simulation.give_reward");
    }

    public void reload() throws Exception {
        super.reload();
        load();
    }
}
