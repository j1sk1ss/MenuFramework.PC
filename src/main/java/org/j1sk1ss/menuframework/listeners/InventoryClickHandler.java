package org.j1sk1ss.menuframework.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.HashMap;
import java.util.List;


public class InventoryClickHandler implements Listener {
    private HashMap<String, List<Component>> handlers;

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Player currentPlayer) {
            String windowTitle = event.getView().getTitle();
            if (!currentPlayer.equals(event.getWhoClicked())) return;
            for (var key : handlers.keySet()) {
                if (windowTitle.contains(key)) {
                    for (var handler : handlers.get(key)) handler.action(event);
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    public void addHandler(List<Component> components, String environment) {
        handlers.put(environment, components);
    }

    public void removeHandler(String environment) {
        handlers.remove(environment);
    }
}
