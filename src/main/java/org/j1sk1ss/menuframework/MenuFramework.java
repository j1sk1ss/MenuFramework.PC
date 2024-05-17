package org.j1sk1ss.menuframework;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.j1sk1ss.menuframework.listeners.InventoryClickHandler;
import org.j1sk1ss.menuframework.listeners.PlayerEventListener;


public final class MenuFramework extends JavaPlugin {
    public static FileConfiguration Config;
    public static InventoryClickHandler ClickHandler;

    @Override
    public void onEnable() {
        Config = JavaPlugin.getPlugin(MenuFramework.class).getConfig();
        System.out.print("Menu framework enabled");

        ClickHandler = new InventoryClickHandler();
        Bukkit.getPluginManager().registerEvents(ClickHandler, this);
        System.out.print("Inventory handler registered");

        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);
        System.out.print("Player handler registered");
    }

    @Override
    public void onDisable() {
        System.out.print("Menu framework disabled");
    }
}
