package com.toonystank.moodyfishing.rewards.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class RewardDataSection {

    private boolean regionSpecificEnabled;
    private String regionSpecificRegion;
    private boolean regionSpecificPermissionMode;
    private boolean regionSpecificRemoveVanillaLoot;
}
