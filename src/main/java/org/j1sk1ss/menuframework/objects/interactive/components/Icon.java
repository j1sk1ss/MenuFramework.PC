package org.j1sk1ss.menuframework.objects.interactive.components;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.menuframework.objects.interactive.Component;


public class Icon extends Component {
    /**
     * Icon component
     * @param position Icon position
     */
    public Icon(int position) {
        Position = position;

        Name  = "Icon";
        Lore  = "IconLore";
        Image = new Item(Name, Lore, BodyMaterial, 1, BodyCustomModelData);
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     */
    public Icon(int position, String name, String lore) {
        Position = position;
        Name     = name;
        Lore     = lore;

        Image = new Item(Name, Lore, BodyMaterial, 1, BodyCustomModelData);
    }

    /**
     * Icon component
     * @param position Icon position
     * @param name Icon name
     * @param lore Icon lore
     * @param material Icon material
     */
    public Icon(int position, String name, String lore, Material material) {
        Position = position;
        Name     = name;
        Lore     = lore;

        Image = new Item(Name, Lore, material, 1, BodyCustomModelData);
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
        Position = position;
        Name     = name;
        Lore     = lore;
        Image    = new Item(Name, Lore, material, 1, dataModel);
    }

    private final int Position;
    private final ItemStack Image;

    @Override
    public void place(Inventory inventory) {
        inventory.setItem(Position, Image);
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        var item = new Item(Name, String.join(", ", lore), Image.getType(), 1, Image.getItemMeta().getCustomModelData());
        inventory.setItem(Position, item);
    }

    @Override
    public List<Integer> getCoordinates() {
        return List.of(Position);
    }
}