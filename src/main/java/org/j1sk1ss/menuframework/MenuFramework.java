package org.j1sk1ss.menuframework;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MenuFramework extends JavaPlugin {
    public static FileConfiguration Config;

    @Override
    public void onEnable() {
        Config = JavaPlugin.getPlugin(MenuFramework.class).getConfig();
        System.out.print("Menu framework enabled");
    }

    @Override
    public void onDisable() {
        System.out.print("Menu framework disabled");
    }
}
