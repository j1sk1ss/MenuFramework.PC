package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.objects.MenuWindow;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;

import java.util.List;
import java.util.function.BiConsumer;


@ExtensionMethod({Manager.class})
public class ItemArea extends Component {
    public ItemArea(ItemArea itemArea) {
        super(itemArea);
        Items = itemArea.Items;
    }

    public ItemArea(Margin coordinates, List<ItemStack> items, BiConsumer<InventoryClickEvent, MenuWindow> delegate) {
        super(coordinates, "ItemArea", "ItemAreaLore", delegate);
        Items = items;
    }

    private final List<ItemStack> Items;

    @Override
    public void place(Inventory inventory) {
        for (int i = 0; i < Math.min(getCoordinates().getSlots().size(), Items.size()); i++)
            inventory.setItem(i, Items.get(i));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (int i = 0; i < Math.min(getCoordinates().getSlots().size(), Items.size()); i++) {
            var item = Items.get(i);
            item.setLore(lore.get(i));
            inventory.setItem(i, item);
        }
    }
}
