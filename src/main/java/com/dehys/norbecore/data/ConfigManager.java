package com.dehys.norbecore.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

    public long SAVEDATAPERIOD;
    public String PREFIX;
    private Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        reloadConfig();
    }

    public void reloadConfig() {
        refreshVariables();
    }

    private void refreshVariables() {
        SAVEDATAPERIOD = plugin.getConfig().getLong("savedata.period");
        PREFIX = plugin.getConfig().getString("prefix");
    }

    public String getString(String path) {
        return plugin.getConfig().getString(path);
    }

    public void set(String path, Object value) {
        plugin.getConfig().set(path, value);
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }


}