package org.j1sk1ss.menuframework.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.jetbrains.annotations.NotNull;


public class ButtonClickEvent extends Event implements Cancellable {
    public ButtonClickEvent(int slot, Component component, Player player, InventoryClickEvent event) {
        this.slot = slot;
        this.clickedComponent = component;
        this.handlers = new HandlerList();
        this.player = player;
        this.inventoryClickEvent = event;
    }

    private final int slot;
    private final Component clickedComponent;
    private final Player player;
    private final HandlerList handlers;
    private final InventoryClickEvent inventoryClickEvent;

    private boolean isCancelled;


    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        isCancelled = arg0;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Component getClickedComponent() {
        return clickedComponent;
    }

    public int getClickedSlot() {
        return slot;
    }

    public InventoryClickEvent getInventoryClickEvent() { return inventoryClickEvent; }
}
