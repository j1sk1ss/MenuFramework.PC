package org.j1sk1ss.menuframework.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.List;

public class InventoryClickHandler implements Listener {
    private List<Component> handlers;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        for (var component : handlers)
            component.action(event);
    }

    public void addHandler(Component component) {
        handlers.add(component);
    }

    public void removeHandler(Component component) {
        handlers.remove(component);
    }
}
