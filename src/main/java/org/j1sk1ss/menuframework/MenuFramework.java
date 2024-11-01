package org.j1sk1ss.menuframework;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import org.j1sk1ss.menuframework.listeners.InventoryClickHandler;
import org.j1sk1ss.menuframework.listeners.PlayerEventListener;

import java.io.File;
import java.io.IOException;


public final class MenuFramework extends JavaPlugin {
    public static MenuFramework instance = null;
    public static FileConfiguration Config = new YamlConfiguration();
    public static InventoryClickHandler ClickHandler = new InventoryClickHandler();

    @Override
    public void onEnable() {
        var file = new File(getDataFolder() + File.separator + "config.yml");
        if (file.exists()) saveDefaultConfig();
        else {
            try {
                if (!file.createNewFile()) System.err.println("Error creating config.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            CheckConfig();
            saveConfig();
            reloadConfig();
        }

        Config = JavaPlugin.getPlugin(MenuFramework.class).getConfig();
        System.out.print("Menu framework enabled!");

        Bukkit.getPluginManager().registerEvents(ClickHandler, this);
        System.out.print("Inventory handler registered!");

        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);
        System.out.print("Player handler registered!");
    }

    @Override
    public void onDisable() {
        System.out.print("Menu framework disabled");
    }

    public static MenuFramework getInstance() {
        if (instance == null)
            instance = new MenuFramework();

        return instance;
    }

    private void CheckConfig() {
        if(getConfig().get("Name") == null) {
            getConfig().set("Name", "Value");
            saveConfig();
            reloadConfig();
        }
    }
}
