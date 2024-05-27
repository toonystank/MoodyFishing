package com.toonystank.moodyfishing.rewards;

import com.toonystank.moodyfishing.utils.ConfigManger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RewardConfig extends ConfigManger {
    public RewardConfig(String fileName) throws IOException {
        super(fileName, "rewards",false,false);
    }

}
