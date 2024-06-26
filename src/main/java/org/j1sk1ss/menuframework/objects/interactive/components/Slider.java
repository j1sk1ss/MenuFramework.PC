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
import org.j1sk1ss.menuframework.events.SliderClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Slider extends Component {
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

        ChosenDataModel  = MenuFramework.Config.getInt("slider_data.chosen.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("slider_data.default.data", 17001);
        ChosenMaterial   = Material.matchMaterial(MenuFramework.Config.getString("slider_data.chosen.material", "GLASS"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("slider_data.default.material", "PURPLE_WOOL"));
    }
    
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

        ChosenDataModel  = MenuFramework.Config.getInt("slider_data.chosen.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("slider_data.default.data", 17001);
        ChosenMaterial   = Material.matchMaterial(MenuFramework.Config.getString("slider_data.chosen.material", "GLASS"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("slider_data.default.material", "PURPLE_WOOL"));
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

        ChosenDataModel  = MenuFramework.Config.getInt("slider_data.chosen.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("slider_data.default.data", 17001);
        ChosenMaterial   = Material.matchMaterial(MenuFramework.Config.getString("slider_data.chosen.material", "GLASS"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("slider_data.default.material", "PURPLE_WOOL"));
    }

    private final List<Integer> Coordinates;
    private final List<String> Options;
    private final Consumer<InventoryClickEvent> Action;
    
    private final int ChosenDataModel;
    private final Material ChosenMaterial;
    private final int DefaultDataModel;
    private final Material DefaultMaterial;

    @Override
    public void place(Inventory inventory) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), DefaultMaterial, 1, DefaultDataModel));
            else inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), ChosenMaterial, 1, ChosenDataModel));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != 0) inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), DefaultMaterial, 1, DefaultDataModel));
            else inventory.setItem(Coordinates.get(i), new Item(Name, lore.get(i), ChosenMaterial, 1, ChosenDataModel));
    }

    @Override
    public void displace(Inventory inventory) {
        for (var coordinate = 0; coordinate < Coordinates.size(); coordinate++)
            if (inventory.getItem(coordinate) != null)
                if (Objects.requireNonNull(inventory.getItem(coordinate)).getName().equals(Name))
                    inventory.setItem(coordinate, null);
    }

    @Override
    public List<Integer> getCoordinates() {
        return Coordinates;
    }

    @Override
    public void action(InventoryClickEvent event) {
        var clickEvent = new SliderClickEvent(event.getSlot(), this, (Player)event.getWhoClicked(), event);
        Bukkit.getPluginManager().callEvent(clickEvent);

        if (Action != null) Action.accept(event);

        var inventory = event.getInventory();
        for (var i = 0; i < Coordinates.size(); i++)
            if (Coordinates.get(i) != event.getSlot()) inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), DefaultMaterial, 1, DefaultDataModel));
            else inventory.setItem(Coordinates.get(i), new Item(Name, Options.get(i), ChosenMaterial, 1, ChosenDataModel));
    }

    /**
     * Get coordinate of chose
     * @return Coordinate of chose
     */
    public String getChose(InventoryClickEvent inventory) {
        for (var i = 0; i < Coordinates.size(); i++)
            if (inventory.getInventory().getItem(Coordinates.get(i)) != null)
                if (inventory.getInventory().getItem(Coordinates.get(i)).getType().equals(ChosenMaterial)) return Options.get(i);

        return "none";
    }
}
