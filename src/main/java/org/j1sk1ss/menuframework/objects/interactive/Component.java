package org.j1sk1ss.menuframework.objects.interactive;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public abstract class Component {
    public void click(int slot) {
        if (isClicked(slot)) action(null);
    }

    public void click(InventoryClickEvent click) {
        if (isClicked(click.getSlot())) action(click);
    }

    public abstract void place(Inventory inventory);
    public abstract void displace(Inventory inventory);
    public abstract boolean isClicked(int click);

    public abstract String getName();
    public abstract String getLoreLines();
    public abstract List<Integer> getCoordinates();

    public void action(InventoryClickEvent event) {
        return;
    }
}
