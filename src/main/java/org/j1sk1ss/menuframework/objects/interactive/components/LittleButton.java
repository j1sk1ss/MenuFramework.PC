package org.j1sk1ss.menuframework.objects.interactive.components;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import lombok.experimental.ExtensionMethod;

import java.util.List;
import java.util.function.Consumer;


@ExtensionMethod({Manager.class})
public class LittleButton extends Component {
    public LittleButton(LittleButton littleButton) {
        Position            = littleButton.Position;
        Name                = littleButton.Name;
        Lore                = littleButton.Lore;
        Action              = littleButton.Action;
        BodyMaterial        = littleButton.BodyMaterial;
        BodyCustomModelData = littleButton.BodyCustomModelData;
    }

    /**
     * Little button component
     * @param position Position of little button
     */
    public LittleButton(int position) {
        this.Position = position;

        this.Name     = "LButton";
        this.Lore     = "LButtonLore";
        this.Action   = null;
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     */
    public LittleButton(int position, String name, String lore) {
        this.Position = position;
        this.Name     = name;
        this.Lore     = lore;

        this.Action   = null;
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     * @param delegate Action
     */
    public LittleButton(int position, String name, String lore, Consumer<InventoryClickEvent> delegate) {
        this.Position = position;
        this.Name     = name;
        this.Lore     = lore;
        this.Action   = delegate;
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     * @param delegate Action
     * @param material Little button material
     */
    public LittleButton(int position, String name, String lore, Consumer<InventoryClickEvent> delegate, Material material) {
        this.Position = position;
        this.Name     = name;
        this.Lore     = lore;
        BodyMaterial  = material;
        this.Action   = delegate;
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     * @param delegate Action
     * @param material Little button material
     * @param model Little button model data
     */
    public LittleButton(int position, String name, String lore, Consumer<InventoryClickEvent> delegate, Material material, int model) {
        this.Position       = position;
        this.Name           = name;
        this.Lore           = lore;
        BodyMaterial        = material;
        BodyCustomModelData = model;
        this.Action         = delegate;
    }

    private final int Position;
    private final Consumer<InventoryClickEvent> Action;

    @Override
    public void place(Inventory inventory) {
        var item = new Item(Name, Lore, BodyMaterial, 1, BodyCustomModelData);
        var meta = item.getItemMeta();

        PersistentDataContainer.copyTo(meta.getPersistentDataContainer(), true);
        item.setItemMeta(meta);

        inventory.setItem(Position, item);
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        var item = new Item(Name, String.join(" ", lore), BodyMaterial, 1, BodyCustomModelData);
        var meta = item.getItemMeta();

        PersistentDataContainer.copyTo(meta.getPersistentDataContainer(), true);
        item.setItemMeta(meta);

        inventory.setItem(Position, item);
    }

    @Override
    public List<Integer> getCoordinates() {
        return List.of(Position);
    }

    @Override
    public Component deepCopy() {
        return new LittleButton(this);
    }

    @Override
    public void action(InventoryClickEvent event) {
        if (Action != null) Action.accept(event);
    }
}
