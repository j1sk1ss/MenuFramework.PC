package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.menuframework.common.SlotsManager;
import org.j1sk1ss.menuframework.objects.interactive.Component;


public class ClickArea extends Component {
    public ClickArea(ClickArea clickArea) {
        super(clickArea);
    }

    public ClickArea(List<Integer> coordinates) {
        super(coordinates, "ClickArea", "ClickAreaLore", null);
    }

    public ClickArea(int firstCoordinate, int secondCoordinate) {
        super(SlotsManager.slots2list(firstCoordinate, secondCoordinate), "ClickArea", "ClickAreaLore", null);
    }

    public ClickArea(int firstCoordinate, int secondCoordinate, Consumer<InventoryClickEvent> delegate) {
        super(SlotsManager.slots2list(firstCoordinate, secondCoordinate), "ClickArea", "ClickAreaLore", delegate);
    }

    public ClickArea(List<Integer> coordinates, Consumer<InventoryClickEvent> delegate) {
        super(coordinates, "ClickArea", "ClickAreaLore", delegate);
    }

    public ClickArea(int firstCoordinate, int secondCoordinate, Consumer<InventoryClickEvent> delegate, String name, String lore) {
        super(SlotsManager.slots2list(firstCoordinate, secondCoordinate), name, lore, delegate);
    }

    public ClickArea(List<Integer> coordinates, Consumer<InventoryClickEvent> delegate, String name, String lore) {
        super(coordinates, name, lore, delegate);
    }

    @Override
    public void place(Inventory inventory) { return; }

    @Override
    public void place(Inventory inventory, List<String> lore) { return; }

    @Override
    public void displace(Inventory inventory) { return; }

    @Override
    public Component deepCopy() {
        return new ClickArea(this);
    }
}
