package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Bar extends Component {
    public Bar(List<Integer> coordinates, Direction direction) {
        Coordinates = coordinates;
        Direction   = direction;

        Options     = new ArrayList<>();
        Action      = null;
        Name        = "Bar";
        Lore        = "Bar lore";
    }

    public Bar(List<Integer> coordinates, Direction direction, List<String> options) {
        Coordinates = coordinates;
        Direction   = direction;
        Options     = options;

        Action      = null;
        Name        = "Bar";
        Lore        = "Bar lore";
    }

    public Bar(List<Integer> coordinates, Direction direction, List<String> options, Consumer<InventoryClickEvent> delegate) {
        Coordinates = coordinates;
        Direction   = direction;
        Action      = delegate;
        Options     = options;

        Name        = "Bar";
        Lore        = "Bar lore";
    }

    public Bar(List<Integer> coordinates, Direction direction, String name, String lore, List<String> options, Consumer<InventoryClickEvent> delegate) {
        Coordinates = coordinates;
        Direction   = direction;
        Action      = delegate;
        Options     = options;
        Name        = name;
        Lore        = lore;
    }

    private final List<Integer> Coordinates;
    private final List<String> Options;
    private final Direction Direction;
    private final Consumer<InventoryClickEvent> Action;

    @Override
    public void place(Inventory inventory) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), Material.RED_WOOL, 1, MenuFramework.Config.getInt("bar_data_model.unloaded")));
            else inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), Material.GREEN_WOOL, 1, MenuFramework.Config.getInt("bar_data_model.loaded")));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), Material.RED_WOOL, 1, MenuFramework.Config.getInt("bar_data_model.unloaded")));
            else inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), Material.GREEN_WOOL, 1, MenuFramework.Config.getInt("bar_data_model.loaded")));
    }

    public void setValue(Inventory inventory, int downBorder, int upperBorder) {
        for (var cord : Coordinates) setUnloaded(inventory, cord);
        switch (Direction) {

            // Example: 5 idx -> 10 idx
            case Down -> {
                var iterator = 0;
                for (var pos = 0; pos < inventory.getSize(); pos++) {
                    if (!Coordinates.contains(pos)) continue;
                    if (iterator >= downBorder && iterator <= upperBorder)
                        setLoaded(inventory, pos);

                    iterator++;
                }
            }

            // Example: 10 idx -> 5 idx
            case Up -> {
                var iterator = Coordinates.size();
                for (var pos = inventory.getSize(); pos >= 0; pos--) {
                    if (!Coordinates.contains(pos)) continue;
                    if (iterator >= upperBorder && iterator <= downBorder)
                        setLoaded(inventory, pos);

                    iterator--;
                }
            }

            // Example: 10 idx -> 5 idx
            case Left -> {
                var iterator = Coordinates.size();
                var rows = inventory.getSize() / 9;

                for (var row = 0; row < rows; row++) {
                    for (var column = 8; column >= 0; column--) {
                        var pos = row * 9 + column;
                        if (!Coordinates.contains(pos)) continue;
                        if (iterator >= upperBorder && iterator <= downBorder)
                            setLoaded(inventory, pos);

                        iterator--;
                    }
                }
            }

            // Example: 5 idx -> 10 idx
            case Right -> {
                var rows = inventory.getSize() / 9;
                var iterator = 0;

                for (var row = 0; row < rows; row++) {
                    for (var column = 0; column < 9; column++) {
                        var pos = row * 9 + column;
                        if (!Coordinates.contains(pos)) continue;
                        if (iterator >= downBorder && iterator <= upperBorder)
                            setLoaded(inventory, pos);

                        iterator++;
                    }
                }
            }
        }
    }

    private void setLoaded(Inventory inventory, int pos) {
        var item = inventory.getItem(pos);
        if (item == null) return;

        item.setModelData(MenuFramework.Config.getInt("bar_data_model.loaded"));
        item.setMaterial(Material.GREEN_WOOL);

        inventory.setItem(pos, item);
    }

    private void setUnloaded(Inventory inventory, int pos) {
        var item = inventory.getItem(pos);
        if (item == null) return;

        item.setModelData(MenuFramework.Config.getInt("bar_data_model.unloaded"));
        item.setMaterial(Material.RED_WOOL);

        inventory.setItem(pos, item);
    }

    @Override
    public void displace(Inventory inventory) {
        for (var coordinate = 0; coordinate < Coordinates.size(); coordinate++)
            if (inventory.getItem(coordinate) != null)
                if (Objects.requireNonNull(inventory.getItem(coordinate)).getName().equals(Name))
                    inventory.setItem(coordinate, null);
    }

    @Override
    public boolean isClicked(int click) {
        return Coordinates.contains(click);
    }

    @Override
    public List<Integer> getCoordinates() {
        return Coordinates;
    }

    @Override
    public void action(InventoryClickEvent event) {
        if (Action != null) Action.accept(event);
    }
}
