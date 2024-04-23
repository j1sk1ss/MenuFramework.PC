package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.item.Item;
import org.j1sk1ss.menuframework.objects.item.ItemManager;

import lombok.experimental.ExtensionMethod;


@ExtensionMethod({ItemManager.class})
public class Checkbox extends Component {
    public Checkbox(int firstSlot, int secondSlot, String name, String lore, Consumer<InventoryClickEvent> delegate) {
        FirstSlot  = firstSlot;
        SecondSlot = secondSlot;
        Name       = name;
        Lore       = lore;
        Action     = delegate;
    }

    private final String Name;
    private final String Lore;
    private final int FirstSlot;
    private final int SecondSlot;
    private final Consumer<InventoryClickEvent> Action;

    @Override
    public void place(Inventory inventory) {
        for (var coordinate : getCoordinates())
            inventory.setItem(coordinate, new Item(Name, Lore, Material.RED_STAINED_GLASS, 1, MenuFramework.Config.getInt("checkbox_data_models.default")));
    }

    @Override
    public void displace(Inventory inventory) {
        for (var coordinate : getCoordinates())
            if (inventory.getItem(coordinate) != null)
                if (inventory.getItem(coordinate).getName().equals(Name))
                    inventory.setItem(coordinate, null);
    }

    @Override
    public boolean isClicked(int click) {
        return getCoordinates().contains(click);
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getLoreLines() {
        return Lore;
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
    
    @Override
    public void action(InventoryClickEvent event) {
        if (Action != null) Action.accept(event);
        var inventory = event.getInventory();

        if (inventory.getItem(event.getSlot()).getType().equals(Material.RED_STAINED_GLASS)) {
            for (var coordinate : getCoordinates()) 
                inventory.setItem(coordinate, new Item(Name, Lore, Material.RED_STAINED_GLASS, 1, MenuFramework.Config.getInt("checkbox_data_models.default")));
        }
        else {
            for (var coordinate : getCoordinates()) 
                inventory.setItem(coordinate, new Item(Name, Lore, Material.GREEN_STAINED_GLASS, 1, MenuFramework.Config.getInt("checkbox_data_models.chosen")));
        }
    }

    public boolean isChecked(InventoryClickEvent event) {
        return event.getInventory().getItem(FirstSlot).getType().equals(Material.GREEN_STAINED_GLASS);
    }
}
