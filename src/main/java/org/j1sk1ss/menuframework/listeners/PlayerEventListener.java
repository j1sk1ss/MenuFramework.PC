package org.j1sk1ss.menuframework.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.j1sk1ss.menuframework.MenuFramework;


public class PlayerEventListener implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerEnter(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var packUrl = MenuFramework.Config.getString("resource_pack");

        if (packUrl == null) return;
        if (!packUrl.equals("none")) {
            System.out.println("[MenuFramework] Loaded resource pack: " + packUrl);
            player.setResourcePack(packUrl);
        }
    }
}