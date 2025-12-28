package com.mobpickup;

import org.bukkit.plugin.java.JavaPlugin;
import com.mobpickup.managers.PluginManager;
import com.mobpickup.listeners.PlayerListener;

// Import your command class
import com.mobpickup.commands.mobbucketCommand;

public class PickupMobs extends JavaPlugin {

    @Override
    public void onEnable() {

        // Initialize managers
        PluginManager.getInstance().initialize();

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Register the command
        if (getCommand("mobbucket") != null) {
            getCommand("mobbucket").setExecutor(new mobbucketCommand(this));
        }

        getLogger().info("PickupMobs has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PickupMobs has been disabled!");
    }

}
