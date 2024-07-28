package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;


public class Icon extends Component {
    public Icon(Icon icon) {
        super(icon);
    }

    /**
     * Icon component
     * @param position Icon position
     */
    public Icon(Margin position) {
        super(position, "Icon", "IconLore", null);
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     */
    public Icon(Margin position, String name, String lore) {
        super(position, name, lore, null);
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     * @param material Icon material
     */
    public Icon(Margin position, String name, String lore, Material material) {
        super(position, name, lore, null);
        setBodyMaterial(material);
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     * @param material Icon material
     * @param dataModel Icon data model
     */
    public Icon(Margin position, String name, String lore, Material material, int dataModel) {
        super(position, name, lore, null);
        setBodyMaterial(material);
        setBodyCustomModelData(dataModel);
    }

    @Override
    public void place(Inventory inventory) {
        place(inventory, List.of(Lore));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        inventory.setItem(getCoordinates().getSlots().getFirst(), getBodyItem());
    }
}