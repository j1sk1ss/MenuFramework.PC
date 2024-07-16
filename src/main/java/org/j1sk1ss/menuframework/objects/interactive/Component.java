package org.j1sk1ss.menuframework.objects.interactive;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.j1sk1ss.itemmanager.ItemManager;
import org.j1sk1ss.itemmanager.manager.Manager;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ComponentClickEvent;
import org.j1sk1ss.menuframework.objects.MenuWindow;

import java.util.List;
import java.util.Objects;


@ExtensionMethod({Manager.class})
public abstract class Component {
    public Component() {
        BodyCustomModelData = MenuFramework.Config.getInt("default.default_data", 7000);
        BodyMaterial = Material.matchMaterial(MenuFramework.Config.getString("default.default_material", "PAPER"));
        PersistentDataContainer = new ItemStack(BodyMaterial != null ? BodyMaterial : null)
            .getItemMeta().getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
    }

    @Setter @Getter private MenuWindow Parent;
    @Setter @Getter protected String Lore;
    @Setter @Getter protected String Name;
    @Setter @Getter protected PersistentDataContainer PersistentDataContainer;

    protected int BodyCustomModelData;
    protected Material BodyMaterial;

    /**
     * Click interaction
     * @param slot Slot in inventory
     */
    public void click(int slot) {
        if (isClicked(slot)) action(null);
    }

    /**
     * Click interaction
     * @param click InventoryClickEvent
     */
    public void click(InventoryClickEvent click) {
        if (isClicked(click.getSlot())) action(click);
    }

    /**
     * Get lore from component
     * @return Lore
     */
    public String getLoreLines() {
        return Lore;
    }

    /**
     * Action for button
     * @param event InventoryClickEvent
     */
    public void action(InventoryClickEvent event) {
        return;
    }

    /**
     * Set double to PersistentData container
     * @param value value
     * @param key key in container
     */
    public void setDouble2Container(double value, String key) {
        var containerKey = new NamespacedKey(ItemManager.getPlugin(ItemManager.class), key);
        getPersistentDataContainer().set(containerKey, PersistentDataType.DOUBLE, value);
    }

    /**
     * Set int to PersistentData container
     * @param value value
     * @param key key in container
     */
    public void setInteger2Container(int value, String key) {
        var containerKey = new NamespacedKey(ItemManager.getPlugin(ItemManager.class), key);
        getPersistentDataContainer().set(containerKey, PersistentDataType.INTEGER, value);
    }

    /**
     * Get double from container
     * @param key key
     * @return double from container
     */
    public double getDoubleFromContainer(String key) {
        var containerKey = new NamespacedKey(ItemManager.getPlugin(ItemManager.class), key);
        var value = getPersistentDataContainer().get(containerKey, PersistentDataType.DOUBLE);
        if (value == null) return -1;

        return value;
    }

    /**
     * Get int from container
     * @param key key
     * @return int from container
     */
    public int getIntegerFromContainer(String key) {
        var containerKey = new NamespacedKey(ItemManager.getPlugin(ItemManager.class), key);
        var value = getPersistentDataContainer().get(containerKey, PersistentDataType.INTEGER);
        if (value == null) return -1;

        return value;
    }

    /**
     * Delete key from component
     * @param key Key
     */
    public void deleteKeyFromContainer(String key) {
        var containerKey = new NamespacedKey(ItemManager.getPlugin(ItemManager.class), key);

        if (getPersistentDataContainer().has(containerKey))
            getPersistentDataContainer().remove(containerKey);
    }

    /**
     * Return true if clicked current components
     * @param click Click position
     * @return True or False
     */
    public boolean isClicked(int click) {
        return getCoordinates().contains(click);
    }

    /**
     * Deep copy object
     * @return New object
     */
    public abstract Component deepCopy();

    public abstract void place(Inventory inventory);

    public abstract void place(Inventory inventory, List<String> lore);

    public void displace(Inventory inventory) {
        for (var pos : getCoordinates())
            if (Objects.requireNonNull(inventory.getItem(pos)).getName().equals(Name))
                inventory.setItem(pos, null);
    }

    public abstract List<Integer> getCoordinates(); 
}
