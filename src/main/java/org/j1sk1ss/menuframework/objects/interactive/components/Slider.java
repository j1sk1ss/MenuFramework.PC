package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.item.Item;
import org.j1sk1ss.menuframework.objects.item.ItemManager;

import java.util.ArrayList;
import java.util.List;


@ExtensionMethod({ItemManager.class})
public class Slider extends Component {
    /**
     * Slider component
     * @param slider Base of slider
     * @param inventory Inventory where placed slider
     */
    public Slider(Slider slider, Inventory inventory) {
        Coordinates = new ArrayList<>(slider.Coordinates);
        Options = new ArrayList<>(slider.Options);
        Name = slider.Name;
        Lore = slider.Lore;
        Parameter = 0;
    }

    /**
     * Slider component
     * @param coordinates Coordinates of slider
     * @param options List of options
     */
    public Slider(List<Integer> coordinates, List<String> options, String lore, String name) {
        Coordinates = coordinates;
        Options = options;
        Name = name;
        Lore = lore;
        Parameter = 0;
    }

    /**
     * Deep copy of slider
     * @param slider Slider that will be copied
     */
    public Slider(Slider slider) {
        Coordinates = new ArrayList<>(slider.Coordinates);
        Options = new ArrayList<>(slider.Options);
        Name = slider.getName();
        Lore = slider.Lore;
        Parameter = 0;
    }

    private final int Parameter;
    private final List<Integer> Coordinates;
    private final List<String> Options;
    private final String Lore;
    private final String Name;

    @Override
    public void place(Inventory inventory) {
        for (var integer : Coordinates)
            if (integer != Parameter) inventory.setItem(integer, new Item(Name, Lore, Material.PAPER, 1, MenuFramework.Config.getInt("slider_data_models.default")));
            else inventory.setItem(integer, new Item(Name, Lore, Material.GLASS, 1, MenuFramework.Config.getInt("slider_data_models.chosen")));
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
    public String getName() {
        return Name;
    }

    @Override
    public String getLoreLines() {
        return Lore;
    }

    @Override
    public List<Integer> getCoordinates() {
        return Coordinates;
    }

    @Override
    public void action(InventoryClickEvent event) {
        var inventory = event.getInventory();
        for (var integer : Coordinates)
            if (integer != event.getSlot()) inventory.setItem(integer, new Item(Name, Lore, Material.PAPER, 1, MenuFramework.Config.getInt("slider_data_models.default")));
            else inventory.setItem(integer, new Item(Name, Lore, Material.GLASS, 1, MenuFramework.Config.getInt("slider_data_models.chosen")));
    }

    /**
     * Get coordinate of chose
     * @return Coordinate of chose
     */
    public String getChose() {
        for (var i = 0; i < Coordinates.size(); i++) 
            if (Coordinates.get(i) == Parameter) return Options.get(i);

        return "none";
    }
}
