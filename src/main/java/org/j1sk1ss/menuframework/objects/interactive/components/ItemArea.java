package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;


@ExtensionMethod({Manager.class})
public class ItemArea extends Component {
    public ItemArea(int firstSlot, int secondSlot, List<ItemStack> items) {
        FirstSlot = firstSlot;
        SecondSlot = secondSlot;
        Items = items;
    }

    private final int FirstSlot;
    private final int SecondSlot;
    private final List<ItemStack> Items;

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
        var list = new ArrayList<Integer>();
        var secondCoordinate = SecondSlot - FirstSlot;

        var height = (secondCoordinate / 9) + 1;
        var weight = (secondCoordinate % 9) + 1;
        for (var i = FirstSlot / 9; i < FirstSlot / 9 + height; i++)
            for (var j = FirstSlot % 9; j < FirstSlot % 9 + weight; j++)
                list.add(9 * i + j);

        return list;
    }
}
