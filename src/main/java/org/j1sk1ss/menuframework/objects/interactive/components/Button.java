package org.j1sk1ss.menuframework.objects.interactive.components;


import lombok.experimental.ExtensionMethod;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ButtonClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Button extends Component {
    public Button(int firstSlot, int secondSlot, String name, String lore, Consumer<InventoryClickEvent> delegate, Material material) {
        FirstSlot  = firstSlot;
        SecondSlot = secondSlot;
        Name       = name;
        Lore       = lore;
        Action     = delegate;
        Material   = material;
    }

    public Button(int firstSlot, int secondSlot, String name, String lore, Consumer<InventoryClickEvent> delegate) {
        FirstSlot  = firstSlot;
        SecondSlot = secondSlot;
        Name       = name;
        Lore       = lore;
        Action     = delegate;

        Material   = org.bukkit.Material.PAPER;
    }

    public Button(int firstSlot, int secondSlot, String name, String lore) {
        FirstSlot  = firstSlot;
        SecondSlot = secondSlot;
        Name       = name;
        Lore       = lore;

        Action     = null;
        Material   = org.bukkit.Material.PAPER;
    }

    public Button(int firstSlot, int secondSlot, String name) {
        FirstSlot  = firstSlot;
        SecondSlot = secondSlot;
        Name       = name;

        Lore       = "";
        Action     = null;
        Material   = org.bukkit.Material.PAPER;
    }

    private final int FirstSlot;
    private final int SecondSlot;
    private final Material Material;
    private final Consumer<InventoryClickEvent> Action;


    @Override
    public void place(Inventory inventory) {
        for (var coordinate : getCoordinates())
            inventory.setItem(coordinate, new Item(Name, Lore, Material, 1, MenuFramework.Config.getInt("buttons_data_model")));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var coordinate : getCoordinates())
            inventory.setItem(coordinate, new Item(Name, String.join(", ", lore), Material, 1, MenuFramework.Config.getInt("buttons_data_model")));
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
        var clickEvent = new ButtonClickEvent(event.getSlot(), this, (Player)event.getWhoClicked(), event);
        Bukkit.getPluginManager().callEvent(clickEvent);

        if (Action != null) Action.accept(event);
    }
}