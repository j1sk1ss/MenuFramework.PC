package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Slider extends Component {
    /**
     * Slider component
     * @param slider Base of slider
     * @param inventory Inventory where placed slider
     */
    public Slider(Slider slider, Inventory inventory) {
        Coordinates = new ArrayList<>(slider.Coordinates);
        Options     = new ArrayList<>(slider.Options);
        Name        = slider.Name;
        Lore        = slider.Lore;
        Action      = null;
    }

    /**
     * Slider component
     * @param coordinates Coordinates of slider
     * @param options List of options
     */
    public Slider(List<Integer> coordinates, List<String> options, String lore, String name, Consumer<InventoryClickEvent> delegate) {
        Coordinates = coordinates;
        Options     = options;
        Name        = name;
        Lore        = lore;
        Action      = delegate;
    }

    /**
     * Deep copy of slider
     * @param slider Slider that will be copied
     */
    public Slider(Slider slider) {
        Coordinates = new ArrayList<>(slider.Coordinates);
        Options     = new ArrayList<>(slider.Options);
        Name        = slider.getName();
        Lore        = slider.Lore;
        Action      = null;
    }

    private final List<Integer> Coordinates;
    private final List<String> Options;
    private final Consumer<InventoryClickEvent> Action;

    @Override
    public void place(Inventory inventory) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), Material.PURPLE_WOOL, 1, MenuFramework.Config.getInt("slider_data_models.default")));
            else inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), Material.GLASS, 1, MenuFramework.Config.getInt("slider_data_models.chosen")));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), Material.PURPLE_WOOL, 1, MenuFramework.Config.getInt("slider_data_models.default")));
            else inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), Material.GLASS, 1, MenuFramework.Config.getInt("slider_data_models.chosen")));
    }

    @Override
    public void displace(Inventory inventory) {
        for (var coordinate = 0; coordinate < Coordinates.size(); coordinate++)
            if (inventory.getItem(coordinate) != null)
                if (inventory.getItem(coordinate).getName().equals(Name))
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

        var inventory = event.getInventory();
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != event.getSlot()) inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), Material.PURPLE_WOOL, 1, MenuFramework.Config.getInt("slider_data_models.default")));
            else inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), Material.GLASS, 1, MenuFramework.Config.getInt("slider_data_models.chosen")));
    }

    /**
     * Get coordinate of chose
     * @return Coordinate of chose
     */
    public String getChose(InventoryClickEvent inventory) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (inventory.getInventory().getItem(Coordinates.get(i)) != null)
                if (inventory.getInventory().getItem(Coordinates.get(i)).getType().equals(Material.GLASS)) return Options.get(i);

        return "none";
    }
}
