package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ComponentClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Bar extends Component {
    public Bar(Bar bar) {
        Coordinates = bar.Coordinates;
        Direction   = bar.Direction;
        Options = bar.Options;
        Action  = bar.Action;
        Name    = bar.Name;
        Lore    = bar.Lore;
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
    public Bar(List<Integer> coordinates, Direction direction) {
        Coordinates = coordinates;
        Direction   = direction;

        Options     = new ArrayList<>();
        Action      = null;
        Name        = "Bar";
        Lore        = "Bar lore";

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
    public Bar(List<Integer> coordinates, Direction direction, List<String> options) {
        Coordinates = coordinates;
        Direction   = direction;
        Options     = options;

        Action      = null;
        Name        = "Bar";
        Lore        = "Bar lore";

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
    public Bar(List<Integer> coordinates, Direction direction, List<String> options, Consumer<ComponentClickEvent> delegate) {
        Coordinates = coordinates;
        Direction   = direction;
        Action      = delegate;
        Options     = options;

        Name        = "Bar";
        Lore        = "Bar lore";

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
    public Bar(List<Integer> coordinates, Direction direction, String name, String lore, List<String> options, Consumer<ComponentClickEvent> delegate) {
        Coordinates = coordinates;
        Direction   = direction;
        Action      = delegate;
        Options     = options;
        Name        = name;
        Lore        = lore;

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
    public Bar(List<Integer> coordinates, Direction direction, String name, String lore,
               List<String> options, Consumer<ComponentClickEvent> delegate,
               int ldm, int ddm, Material lm, Material dm) {
        Coordinates      = coordinates;
        Direction        = direction;
        Action           = delegate;
        Options          = options;
        Name             = name;
        Lore             = lore;
        LoadedDataModel  = ldm;
        DefaultDataModel = ddm;
        LoadedMaterial   = lm;
        DefaultMaterial  = dm;
    }

    private final List<Integer> Coordinates;
    private final List<String> Options;
    private final Direction Direction;
    private final Consumer<ComponentClickEvent> Action;
    private final int LoadedDataModel;
    private final Material LoadedMaterial;
    private final int DefaultDataModel;
    private final Material DefaultMaterial;

    @Override
    public void place(Inventory inventory) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), DefaultMaterial, 1, DefaultDataModel));
            else inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), LoadedMaterial, 1, LoadedDataModel));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), DefaultMaterial, 1, DefaultDataModel));
            else inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), LoadedMaterial, 1, LoadedDataModel));
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
    public List<Integer> getCoordinates() {
        return Coordinates;
    }

    @Override
    public Component deepCopy() {
        return new Bar(this);
    }

    @Override
    public void action(ComponentClickEvent event) {
        if (Action != null) Action.accept(event);
    }
}
