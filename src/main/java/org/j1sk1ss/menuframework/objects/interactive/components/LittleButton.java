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
        super(littleButton);
    }

    /**
     * Little button component
     * @param position Position of little button
     */
    public LittleButton(int position) {
        super(List.of(position), "LittleButton", "LittleButtonLore", null);
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     */
    public LittleButton(int position, String name, String lore) {
        super(List.of(position), name, lore, null);
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     * @param delegate Action
     */
    public LittleButton(int position, String name, String lore, Consumer<InventoryClickEvent> delegate) {
        super(List.of(position), name, lore, delegate);
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
        super(List.of(position), name, lore, delegate);
        setBodyMaterial(material);
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
        super(List.of(position), name, lore, delegate);
        setBodyMaterial(material);
        setBodyCustomModelData(model);
    }

    @Override
    public void place(Inventory inventory) {
        place(inventory, List.of(getLore()));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        var item = new Item(getName(), String.join(" ", lore), getBodyMaterial(), 1, getBodyCustomModelData());
        var meta = item.getItemMeta();

        PersistentDataContainer.copyTo(meta.getPersistentDataContainer(), true);
        item.setItemMeta(meta);

        inventory.setItem(getCoordinates().getFirst(), item);
    }

    @Override
    public Component deepCopy() {
        return new LittleButton(this);
    }
}
