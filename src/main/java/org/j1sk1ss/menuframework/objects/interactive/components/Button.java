package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.experimental.ExtensionMethod;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.common.SlotsManager;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;

import java.util.List;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class Button extends Component {
    public Button(Button button) {
        super(button);
    }

    public Button(Margin margin, String name) {
        super(margin, name, "ButtonLore", null);
    }

    public Button(Margin margin, String name, String lore) {
        super(margin, name, lore, null);
    }

    public Button(Margin margin, String name, String lore, Consumer<InventoryClickEvent> delegate) {
        super(margin, name, lore, delegate);
    }

    public Button(Margin margin, String name, String lore, Consumer<InventoryClickEvent> delegate, Material material) {
        super(margin, name, lore, delegate);
        setBodyMaterial(material);
    }

    public Button(Margin margin, String name, String lore, Consumer<InventoryClickEvent> delegate, Material material, int model) {
        super(margin, name, lore, delegate);
        setBodyMaterial(material);
        setBodyCustomModelData(model);
    }

    @Override
    public void place(Inventory inventory) {
        place(inventory, List.of(Lore));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        var item = new Item(getName(), String.join(", ", lore), getBodyMaterial(), 1, getBodyCustomModelData());
        var meta = item.getItemMeta();

        PersistentDataContainer.copyTo(meta.getPersistentDataContainer(), true);
        item.setItemMeta(meta);

        for (var coordinate : getCoordinates().toSlots())
            inventory.setItem(coordinate, item);
    }

    @Override
    public Component deepCopy() {
        return new Button(this);
    }
}