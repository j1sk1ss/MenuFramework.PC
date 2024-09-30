package org.j1sk1ss.menuframework.objects.interactive.components;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.objects.MenuWindow;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import lombok.experimental.ExtensionMethod;

import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;

import java.util.List;
import java.util.function.BiConsumer;


@ExtensionMethod({Manager.class})
public class LittleButton extends Component {
    public LittleButton(LittleButton littleButton) {
        super(littleButton);
    }

    /**
     * Little button component
     * @param position Position of little button
     */
    public LittleButton(Margin position) {
        super(position, "LittleButton", "LittleButtonLore", null);
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     */
    public LittleButton(Margin position, String name, String lore) {
        super(position, name, lore, null);
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     * @param delegate Action
     */
    public LittleButton(Margin position, String name, String lore, BiConsumer<InventoryClickEvent, MenuWindow> delegate) {
        super(position, name, lore, delegate);
    }

    /**
     * Little button component
     * @param position Position of little button
     * @param name Little button name
     * @param lore Little button lore
     * @param delegate Action
     * @param material Little button material
     */
    public LittleButton(Margin position, String name, String lore, BiConsumer<InventoryClickEvent, MenuWindow> delegate, Material material) {
        super(position, name, lore, delegate);
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
    public LittleButton(Margin position, String name, String lore, BiConsumer<InventoryClickEvent, MenuWindow> delegate, Material material, int model) {
        super(position, name, lore, delegate);
        setBodyMaterial(material);
        setBodyCustomModelData(model);
    }

    @Override
    public void place(Inventory inventory) {
        place(inventory, List.of(getLore()));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        genBodyItem();
        inventory.setItem(getCoordinates().getSlots().get(0), getBodyItem());
    }
}
