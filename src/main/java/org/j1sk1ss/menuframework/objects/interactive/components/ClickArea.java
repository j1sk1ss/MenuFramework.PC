package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.menuframework.objects.MenuWindow;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;


public class ClickArea extends Component {
    public ClickArea(ClickArea clickArea) {
        super(clickArea);
    }

    public ClickArea(Margin coordinates) {
        super(coordinates, "ClickArea", "ClickAreaLore", null);
    }

    public ClickArea(Margin margin, BiConsumer<InventoryClickEvent, MenuWindow> delegate) {
        super(margin, "ClickArea", "ClickAreaLore", delegate);
    }

    public ClickArea(Margin margin, BiConsumer<InventoryClickEvent, MenuWindow> delegate, String name, String lore) {
        super(margin, name, lore, delegate);
    }

    @Override
    public void place(Inventory inventory) { return; }

    @Override
    public void place(Inventory inventory, List<String> lore) { return; }

    @Override
    public void displace(Inventory inventory) { return; }
}
