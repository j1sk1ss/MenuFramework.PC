package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.menuframework.objects.interactive.Component;


public class ClickArea extends Component {
    public ClickArea(List<Integer> coordinates) {
        Coordinates = coordinates;
        Action = null;
        Name = "ClickArea";
        Lore = "ClickAreaLore";
    }

    public ClickArea(int firstCoordinate, int secondCoordinate) {
        Coordinates = positions2coordinates(firstCoordinate, secondCoordinate);
        Action = null;
        Name = "ClickArea";
        Lore = "ClickAreaLore";
    }

    public ClickArea(int firstCoordinate, int secondCoordinate, Consumer<InventoryClickEvent> delegate) {
        Coordinates = positions2coordinates(firstCoordinate, secondCoordinate);
        Action = delegate;
        Name = "ClickArea";
        Lore = "ClickAreaLore";
    }

    public ClickArea(List<Integer> coordinates, Consumer<InventoryClickEvent> delegate) {
        Coordinates = coordinates;
        Action = delegate;
        Name = "ClickArea";
        Lore = "ClickAreaLore";
    }

    public ClickArea(int firstCoordinate, int secondCoordinate, Consumer<InventoryClickEvent> delegate, String name, String lore) {
        Coordinates = positions2coordinates(firstCoordinate, secondCoordinate);
        Action = delegate;
        Name = name;
        Lore = lore;
    }

    public ClickArea(List<Integer> coordinates, Consumer<InventoryClickEvent> delegate, String name, String lore) {
        Coordinates = coordinates;
        Action = delegate;
        Name = name;
        Lore = lore;
    }

    private final List<Integer> Coordinates;
    private final Consumer<InventoryClickEvent> Action;

    @Override
    public void place(Inventory inventory) { return; }

    @Override
    public void place(Inventory inventory, List<String> lore) { return; }

    @Override
    public void displace(Inventory inventory) { return; }

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
