package com.gmail.pieterjansegers1.rollplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


/**
 * Created by Chryomdar on 13/06/2017.
 */

public class Main extends JavaPlugin{

    final int RADIUS=20;

    @Override
    public void onEnable(){
        //Fired when the server enables the plugin
        this.createConfig();
        this.getCommand("roll").setExecutor(new CommandRoll(this));
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                final FileConfiguration config = this.getConfig();
                config.addDefault("roll-radius", RADIUS);
                config.options().copyDefaults(true);
                saveConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}


