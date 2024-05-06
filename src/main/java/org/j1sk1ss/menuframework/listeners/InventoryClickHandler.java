package org.j1sk1ss.menuframework.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.components.Panel;

import java.util.HashMap;


public class InventoryClickHandler implements Listener {
    public InventoryClickHandler() {
        handlers = new HashMap<>();
    }

    private final HashMap<String, Panel> handlers;

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Player player) {
            var windowTitle = event.getView().getTitle();
            if (!player.equals(event.getWhoClicked())) return;

            for (var key : handlers.keySet()) {
                var keyWords = key.split("\\s+");
                var containsAllWords = true;
                for (var word : keyWords) {
                    if (!windowTitle.contains(word)) {
                        containsAllWords = false;
                        break;
                    }
                }

                if (containsAllWords) {
                    handlers.get(key).click(event);
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    public void addHandler(Panel components, String environment) {
        handlers.put(environment, components);
    }

    public void removeHandler(String environment) {
        handlers.remove(environment);
    }
}
