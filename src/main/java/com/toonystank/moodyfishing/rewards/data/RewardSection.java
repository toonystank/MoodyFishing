package com.toonystank.moodyfishing.rewards.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor @Getter
public class RewardSection {

    private String id;
    private String name;
    private List<String> lore;
    private List<RewardList> rewards;
    private List<String> rewardCommands;
    private boolean RewardMessageEnabled;
    private List<String> rewardBroadcastMessage;
    private List<String> rewardMessage;
}
