package org.j1sk1ss.menuframework.objects.interactive;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public abstract class Component {
    /**
     * Click interaction
     * @param slot Slot in inventory
     */
    public void click(int slot) {
        if (isClicked(slot)) action(null);
    }

    /**
     * Click interaction
     * @param click InventoryClickEvent
     */
    public void click(InventoryClickEvent click) {
        if (isClicked(click.getSlot())) action(click);
    }

    public abstract void place(Inventory inventory);
    public abstract void displace(Inventory inventory);
    public abstract boolean isClicked(int click);

    public abstract String getName();
    public abstract String getLoreLines();
    public abstract List<Integer> getCoordinates();

    /**
     * Action for button
     * @param event InventoryClickEvent
     */
    public void action(InventoryClickEvent event) {
        return;
    }
}
