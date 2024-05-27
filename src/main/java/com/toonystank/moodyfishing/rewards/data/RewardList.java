package com.toonystank.moodyfishing.rewards.data;

import com.toonystank.moodyfishing.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RewardList {

    private final String type;
    private final String reward;
    private final int amount;

    public RewardList(String type,String reward,int amount) {
        this.type = type;
        this.reward = reward;
        this.amount = amount;

        MessageUtils.toConsole("loading rewardList " + type + " " + reward + " " + amount,true);

    }
}
