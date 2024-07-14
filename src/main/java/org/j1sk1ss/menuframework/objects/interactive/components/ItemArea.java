package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class ItemArea extends Component {
    public ItemArea(List<Integer> coordinates, List<ItemStack> items, Consumer<InventoryClickEvent> delegate) {
        Coordinates = coordinates;
        Items       = items;
        Action      = delegate;
    }

    public ItemArea(int firstSlot, int secondSlot, List<ItemStack> items, Consumer<InventoryClickEvent> delegate) {
        Coordinates = positions2coordinates(firstSlot, secondSlot);
        Items       = items;
        Action      = delegate;
    }

    private final Consumer<InventoryClickEvent> Action;
    private final List<ItemStack> Items;
    private final List<Integer> Coordinates;

    @Override
    public void place(Inventory inventory) {
        for (int i = 0; i < Math.min(getCoordinates().size(), Items.size()); i++)
            inventory.setItem(i, Items.get(i));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (int i = 0; i < Math.min(getCoordinates().size(), Items.size()); i++) {
            var item = Items.get(i);
            item.setLore(lore.get(i));
            inventory.setItem(i, item);
        }
    }

    @Override
    public List<Integer> getCoordinates() {
        return Coordinates;
    }

    @Override
    public void action(InventoryClickEvent event) {
        if (Action != null) Action.accept(event);
    }

    private List<Integer> positions2coordinates(int first, int second) {
        var list = new ArrayList<Integer>();
        var secondCoordinate = second - first;

        var height = (secondCoordinate / 9) + 1;
        var weight = (secondCoordinate % 9) + 1;
        for (var i = first / 9; i < first / 9 + height; i++)
            for (var j = first % 9; j < first % 9 + weight; j++)
                list.add(9 * i + j);

        return list;
    }
}
