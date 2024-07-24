package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.menuframework.objects.interactive.Component;


public class Icon extends Component {
    public Icon(Icon icon) {
        super(icon);
        Image = icon.Image;
    }

    /**
     * Icon component
     * @param position Icon position
     */
    public Icon(int position) {
        super(List.of(position), "Icon", "IconLore", null);
        Image = new Item(getName(), getLore(), getBodyMaterial(), 1, getBodyCustomModelData());
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     */
    public Icon(int position, String name, String lore) {
        super(List.of(position), name, lore, null);
        Image = new Item(getName(), getLore(), getBodyMaterial(), 1, getBodyCustomModelData());
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     * @param material Icon material
     */
    public Icon(int position, String name, String lore, Material material) {
        super(List.of(position), name, lore, null);
        Image = new Item(getName(), getLore(), material, 1, getBodyCustomModelData());
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     * @param material Icon material
     * @param dataModel Icon data model
     */
    public Icon(int position, String name, String lore, Material material, int dataModel) {
        super(List.of(position), name, lore, null);
        Image = new Item(getName(), getLore(), material, 1, dataModel);
    }

    private final ItemStack Image;

    @Override
    public void place(Inventory inventory) {
        var item = new Item(Name, Lore, Image.getType(), 1, Image.getItemMeta().getCustomModelData());
        inventory.setItem(getCoordinates().getFirst(), item);
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        var item = new Item(Name, String.join(", ", lore), Image.getType(), 1, Image.getItemMeta().getCustomModelData());
        inventory.setItem(getCoordinates().getFirst(), item);
    }

    @Override
    public Component deepCopy() {
        return new Icon(this);
    }
}