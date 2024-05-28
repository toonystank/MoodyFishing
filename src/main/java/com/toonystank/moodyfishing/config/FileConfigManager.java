package com.toonystank.moodyfishing.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class FileConfigManager extends AbstractConfigManager {

    private File file;
    private FileConfiguration config;
    private final Logger logger;
    private final boolean isInFolder;
    @Nullable
    private final String corePath = "";
    private boolean consoleLogger = true;

    public FileConfigManager(String fileName, boolean force, boolean copy, @Nullable Plugin plugin) throws IOException {
        this.logger = plugin != null ? plugin.getLogger() : Logger.getGlobal();
        process(fileName, force, copy, plugin);
        isInFolder = false;
    }

    private void process(String fileName, boolean force, boolean copy, @Nullable Plugin plugin) throws IOException {
        if (plugin == null) {
            this.file = new File(fileName);
        } else {
            this.file = new File(plugin.getDataFolder(), fileName);
        }
        if (copy) {
            try {
                copy(force, plugin);
            } catch (IllegalArgumentException e) {
                logger.warning(e.getMessage());
            }
        }
        if (!file.exists()) {
            if (file.createNewFile() && consoleLogger) {
                logger.info("Created new file: " + file.getName());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    private void copy(boolean force, @Nullable Plugin plugin) {
        if (!file.exists()) {
            this.saveResource(file.getName(), force, plugin);
        }
    }

    private void saveResource(String resourcePath, boolean replace, @Nullable Plugin plugin) {
        // Implementation similar to the original `saveResource` method
    }

    @Override
    public void save() throws IOException {
        config.save(file);
    }

    @Override
    public void reload() throws Exception {
        config = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void set(String path, Object value) throws IOException {
        config.set(path, value);
        save();
    }

    @Override
    public @Nullable Object get(String path) {
        return config.contains(path) ? config.get(path) : null;
    }

    @Override
    public @Nullable String getString(String path) {
        return config.getString(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return config.contains(path) ? config.getStringList(path) : new ArrayList<>();
    }

    @Override
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public int getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public Set<String> getConfigurationSection(String section, boolean deep) throws IOException {
        ConfigurationSection configSection = config.getConfigurationSection(section);
        if (configSection == null) {
            config.createSection(section);
            save();
        }
        return configSection != null ? configSection.getKeys(deep) : config.getConfigurationSection(section).getKeys(deep);
    }
}
