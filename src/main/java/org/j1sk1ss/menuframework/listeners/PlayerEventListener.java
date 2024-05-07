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
        if (player == null) return;

        var packUrl = MenuFramework.Config.getString("resource_pack");
        if (!packUrl.equals("none")) player.setResourcePack(packUrl);
    }
}