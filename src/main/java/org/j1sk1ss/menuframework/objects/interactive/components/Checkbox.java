package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;

import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.objects.MenuWindow;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import lombok.experimental.ExtensionMethod;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;


@ExtensionMethod({Manager.class})
public class Checkbox extends Component {
    public Checkbox(Checkbox checkbox) {
        super(checkbox);

        CheckedMaterial  = checkbox.CheckedMaterial;
        CheckedDataModel = checkbox.CheckedDataModel;
        DefaultDataModel = checkbox.DefaultDataModel;
        DefaultMaterial  = checkbox.DefaultMaterial;
    }

    public Checkbox(Margin margin, String name, String lore) {
        super(margin, name, lore, null);

        CheckedDataModel = MenuFramework.Config.getInt("checkbox_data.checked.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("checkbox_data.default.data", 17001);
        CheckedMaterial  = Material.matchMaterial(MenuFramework.Config.getString("checkbox_data.checked.material", "GREEN_STAINED_GLASS"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("checkbox_data.default.material", "RED_STAINED_GLASS"));
    }

    public Checkbox(Margin margin, String name, String lore, BiConsumer<InventoryClickEvent, MenuWindow> delegate) {
        super(margin, name, lore, delegate);

        CheckedDataModel = MenuFramework.Config.getInt("checkbox_data.checked.data", 17000);
        DefaultDataModel = MenuFramework.Config.getInt("checkbox_data.default.data", 17001);
        CheckedMaterial  = Material.matchMaterial(MenuFramework.Config.getString("checkbox_data.checked.material", "GREEN_STAINED_GLASS"));
        DefaultMaterial  = Material.matchMaterial(MenuFramework.Config.getString("checkbox_data.default.material", "RED_STAINED_GLASS"));
    }

    public Checkbox(Margin margin, String name,
                    String lore, BiConsumer<InventoryClickEvent, MenuWindow> delegate,
                    int cdm, int ddm, Material cm, Material dm) {
        super(margin, name, lore, delegate);

        CheckedDataModel = cdm;
        DefaultDataModel = ddm;
        CheckedMaterial  = cm;
        DefaultMaterial  = dm;
    }

    private final int CheckedDataModel;
    private final Material CheckedMaterial;
    private final int DefaultDataModel;
    private final Material DefaultMaterial;

    @Override
    public void place(Inventory inventory) {
        place(inventory, List.of(getLore()));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var coordinate : getCoordinates().getSlots())
            inventory.setItem(coordinate, new Item(getName(), String.join(" ", lore), DefaultMaterial, 1, DefaultDataModel));
    }
    
    @Override
    public void action(InventoryClickEvent event, MenuWindow parent) {
        if (Action != null) Action.accept(event, parent);
        var inventory = event.getInventory();

        if (Objects.requireNonNull(inventory.getItem(event.getSlot())).getType().equals(DefaultMaterial)) {
            for (var coordinate : getCoordinates().getSlots())
                inventory.setItem(coordinate, new Item(Name, Lore, CheckedMaterial, 1, CheckedDataModel));
        }
        else {
            for (var coordinate : getCoordinates().getSlots())
                inventory.setItem(coordinate, new Item(Name, Lore, DefaultMaterial, 1, DefaultDataModel));
        }
    }

    public boolean isChecked(InventoryClickEvent event) {
        return Objects.requireNonNull(
                event.getInventory().getItem(getCoordinates().getSlots().getFirst())
        ).getType().equals(CheckedMaterial);
    }
}
