package org.j1sk1ss.menuframework.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.interactive.components.Panel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class InventoryClickHandler implements Listener {
    public InventoryClickHandler() {
        handlers = new ConcurrentHashMap<>();
    }

    private final ConcurrentHashMap<String, Component> handlers;

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Player player) {
            final String[] container = new String[1];
            container[0] = event.getView().getTitle();

            if (!player.equals(event.getWhoClicked())) return;

            var stopFlag = new AtomicBoolean(false);
            handlers.keySet().forEach(
                key -> {
                    if (stopFlag.get()) return;

                    var handler = handlers.get(key);
                    if (handler instanceof Panel panel) {
                        var translator = handler.getParent().getLocManager();
                        if (translator != null) {
                            container[0] = translator.getSourceName(
                                container[0].replace(panel.getUi(), "")
                            );
                        }
                    }

                    var keyWords = key.split("\\s+");
                    var containsAllWords = true;
                    for (var word : keyWords) {
                        if (!container[0].contains(word)) {
                            containsAllWords = false;
                            break;
                        }
                    }

                    if (containsAllWords && !stopFlag.get()) {
                        handler.click(event, handler.getParent());
                        event.setCancelled(true);
                        stopFlag.set(true);
                    }
                }
            );
        }
    }

    public void addHandler(Component components, String environment) {
        handlers.put(environment, components);
    }

    public void removeHandler(String environment) {
        handlers.remove(environment);
    }
}
