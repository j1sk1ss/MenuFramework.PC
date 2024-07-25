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
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Bar extends Component {
    public Bar(Bar bar) {
        super(bar);

        Direction = bar.Direction;
        Options   = new ArrayList<>(bar.Options);
        LoadedDataModel  = bar.LoadedDataModel;
        LoadedMaterial   = bar.LoadedMaterial;
        DefaultDataModel = bar.DefaultDataModel;
        DefaultMaterial  = bar.DefaultMaterial;
    }

    /**
     * Bar component
     * @param coordinates Coordinates of bar
     * @param direction direction of filling
     */
    public Bar(Margin coordinates, Direction direction) {
        super(coordinates, "Bar", "BarLore", null);

        Direction = direction;
        Options   = new ArrayList<>();

        LoadedDataModel  = MenuFramework.Config.getInt("bar_data.loaded.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("bar_data.default.data", 17000);
        LoadedMaterial   = Material.matchMaterial(MenuFramework.Config.getString("bar_data.loaded.material", "GREEN_WOOL"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("bar_data.default.material", "RED_WOOL"));
    }

    /**
     * Bar component
     * @param coordinates Coordinates of bar
     * @param direction direction of filling
     * @param options Lore of items in bar
     */
    public Bar(Margin coordinates, Direction direction, List<String> options) {
        super(coordinates, "Bar", "BarLore", null);

        Direction = direction;
        Options   = options;

        LoadedDataModel  = MenuFramework.Config.getInt("bar_data.loaded.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("bar_data.default.data", 17000);
        LoadedMaterial   = Material.matchMaterial(MenuFramework.Config.getString("bar_data.loaded.material", "GREEN_WOOL"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("bar_data.default.material", "RED_WOOL"));
    }

    /**
     * Bar component
     * @param coordinates Coordinates of bar
     * @param direction direction of filling
     * @param options Lore of items in bar
     * @param delegate Action
     */
    public Bar(Margin coordinates, Direction direction, List<String> options, Consumer<InventoryClickEvent> delegate) {
        super(coordinates, "Bar", "BarLore", delegate);

        Direction = direction;
        Options   = options;

        LoadedDataModel  = MenuFramework.Config.getInt("bar_data.loaded.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("bar_data.default.data", 17000);
        LoadedMaterial   = Material.matchMaterial(MenuFramework.Config.getString("bar_data.loaded.material", "GREEN_WOOL"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("bar_data.default.material", "RED_WOOL"));
    }

    /**
     * Bar component
     * @param coordinates Coordinates of bar
     * @param direction direction of filling
     * @param name Name of Bar
     * @param lore Lore of Bar items
     * @param options Lore of items in bar
     * @param delegate Action
     */
    public Bar(Margin coordinates, Direction direction, String name, String lore, List<String> options, Consumer<InventoryClickEvent> delegate) {
        super(coordinates, name, lore, delegate);

        Direction = direction;
        Options   = options;

        LoadedDataModel  = MenuFramework.Config.getInt("bar_data.loaded.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("bar_data.default.data", 17000);
        LoadedMaterial   = Material.matchMaterial(MenuFramework.Config.getString("bar_data.loaded.material", "GREEN_WOOL"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("bar_data.default.material", "RED_WOOL"));
    }

    /**
     * Bar component
     * @param coordinates Coordinates of bar
     * @param direction direction of filling
     * @param name Name of Bar
     * @param lore Lore of Bar items
     * @param options Lore of items in bar
     * @param delegate Action
     * @param ldm Loaded option data model
     * @param ddm Default option data model
     * @param lm Loaded option material
     * @param dm Default option material
     */
    public Bar(Margin coordinates, Direction direction, String name, String lore,
               List<String> options, Consumer<InventoryClickEvent> delegate,
               int ldm, int ddm, Material lm, Material dm) {
        super(coordinates, name, lore, delegate);

        Direction        = direction;
        Options          = options;
        LoadedDataModel  = ldm;
        DefaultDataModel = ddm;
        LoadedMaterial   = lm;
        DefaultMaterial  = dm;
    }

    private final List<String> Options;
    private final Direction Direction;
    private final int LoadedDataModel;
    private final Material LoadedMaterial;
    private final int DefaultDataModel;
    private final Material DefaultMaterial;

    @Override
    public void place(Inventory inventory) {
        place(inventory, Options);
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        var slots = getCoordinates().toSlots();
        for (var i = 0; i < slots.size(); i++)
            if (slots.get(i) != 0) {
                inventory.setItem(
                    slots.get(i), new Item(getName(), lore.get(i), DefaultMaterial, 1, DefaultDataModel)
                );
            }
            else {
                inventory.setItem(
                    slots.get(i), new Item(getName(), lore.get(i), LoadedMaterial, 1, LoadedDataModel)
                );
            }
    }

    public void setValue(Inventory inventory, int downBorder, int upperBorder) {
        var slots = getCoordinates().toSlots();
        for (var cord : slots) setUnloaded(inventory, cord);
        switch (Direction) {
            // Example: 5 idx -> 10 idx
            case Down -> {
                var iterator = 0;
                for (var pos = 0; pos < inventory.getSize(); pos++) {
                    if (!slots.contains(pos)) continue;
                    if (iterator >= downBorder && iterator <= upperBorder)
                        setLoaded(inventory, pos);

                    iterator++;
                }
            }

            // Example: 10 idx -> 5 idx
            case Up -> {
                var iterator = slots.size();
                for (var pos = inventory.getSize(); pos >= 0; pos--) {
                    if (!slots.contains(pos)) continue;
                    if (iterator >= upperBorder && iterator <= downBorder)
                        setLoaded(inventory, pos);

                    iterator--;
                }
            }

            // Example: 10 idx -> 5 idx
            case Left -> {
                var iterator = slots.size();
                var rows = inventory.getSize() / 9;

                for (var row = 0; row < rows; row++) {
                    for (var column = 8; column >= 0; column--) {
                        var pos = row * 9 + column;
                        if (!slots.contains(pos)) continue;
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
                        if (!slots.contains(pos)) continue;
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

        item.setModelData(LoadedDataModel);
        item.setMaterial(LoadedMaterial);

        inventory.setItem(pos, item);
    }

    private void setUnloaded(Inventory inventory, int pos) {
        var item = inventory.getItem(pos);
        if (item == null) return;

        item.setModelData(DefaultDataModel);
        item.setMaterial(DefaultMaterial);

        inventory.setItem(pos, item);
    }

    @Override
    public Component deepCopy() {
        return new Bar(this);
    }
}
