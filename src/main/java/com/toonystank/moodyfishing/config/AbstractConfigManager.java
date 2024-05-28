package com.toonystank.moodyfishing.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public abstract class AbstractConfigManager {

    public abstract void save() throws IOException;

    public abstract void reload() throws Exception;

    public abstract void set(String path, Object value) throws IOException;

    public abstract @Nullable Object get(String path);

    public abstract @Nullable String getString(String path);

    public abstract List<String> getStringList(String path);

    public abstract boolean getBoolean(String path);

    public abstract int getInt(String path);

    public abstract double getDouble(String path);

    public abstract Set<String> getConfigurationSection(String section, boolean deep) throws IOException;
}
