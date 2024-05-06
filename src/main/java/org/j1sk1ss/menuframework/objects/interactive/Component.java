package org.j1sk1ss.menuframework.objects.interactive;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.j1sk1ss.itemmanager.ItemManager;

import java.util.List;


public abstract class Component {
    public Component() {
        var item = new ItemStack(Material.PAPER);
        PersistentDataContainer = item.getItemMeta().getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
    }

    protected String Lore;
    protected String Name;
    protected PersistentDataContainer PersistentDataContainer;

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
     * Get name of component
     * @return Name
     */
    public String getName() {
        return Name;
    }

    /**
     * Get lore from component
     * @return Lore
     */
    public String getLoreLines() {
        return Lore;
    }

    /**
     * Get PDC from component
     * @return PDC
     */
    public PersistentDataContainer getPersistentDataContainer() {
        return PersistentDataContainer;
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

    public abstract void place(Inventory inventory);
    public abstract void place(Inventory inventory, List<String> lore);
    public abstract void displace(Inventory inventory);
    public abstract boolean isClicked(int click);
    public abstract List<Integer> getCoordinates(); 
}
