package org.dreamndelight.playerstatistics.lib.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.dreamndelight.playerstatistics.lib.data.*;
import org.dreamndelight.playerstatistics.lib.listeners.*;

import java.util.Timer;

public class PlayerStatistics extends JavaPlugin {

    private static PlayerStatistics instance;
    private UserData userData;
    private ConfigManager configManager;
    private StatisticsManager statisticsManager;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        if (!SQL.connect()) return;
        SQL.setupTables();
        userData = UserData.retrieveData();
        statisticsManager = new StatisticsManager();
        setupListeners();
        registerProvider();
        Timer timer = new Timer();
        timer.schedule(new StatisticsTimer(), getConfigManager().SAVEDATAPERIOD * 1000, getConfigManager().SAVEDATAPERIOD * 1000);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        userData.saveData();
        statisticsManager.saveStatistics();
    }


    public static PlayerStatistics get() {
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public StatisticsManager getStatisticsManager() {
        return statisticsManager;
    }

    private void setupListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new DropListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new ItemBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ItemCraftListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
        getServer().getPluginManager().registerEvents(new FishListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
    }

    private void registerProvider() {
        Bukkit.getServer().getServicesManager().register(PlayerStatistics.class, this, this, ServicePriority.Highest);
    }
}
