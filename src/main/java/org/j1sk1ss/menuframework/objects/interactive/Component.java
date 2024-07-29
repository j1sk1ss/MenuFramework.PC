package org.j1sk1ss.menuframework.objects.interactive;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.j1sk1ss.itemmanager.ItemManager;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;

import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.objects.MenuWindow;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;


@Setter
@Getter
@ExtensionMethod({Manager.class})
public abstract class Component {
    public Component(Component component) {
        Name                    = component.getName();
        Lore                    = component.getLore();
        Language                = component.getLanguage();
        Coordinates             = component.getCoordinates();
        BodyCustomModelData     = component.getBodyCustomModelData();
        BodyMaterial            = component.getBodyMaterial();
        PersistentDataContainer = component.getPersistentDataContainer();
        Parent                  = component.getParent();
        Action                  = component.getAction();
        BodyItem                = component.getBodyItem();
    }

    public Component(Margin margin) {
        Action              = null;
        Language            = "RU";
        Coordinates         = margin;
        BodyCustomModelData = MenuFramework.Config.getInt("default.default_data", 7000);
        BodyMaterial        = Material.matchMaterial(MenuFramework.Config.getString("default.default_material", "PAPER"));
        PersistentDataContainer = new ItemStack(Objects.requireNonNull(BodyMaterial)).getItemMeta().getPersistentDataContainer()
                .getAdapterContext().newPersistentDataContainer();
        genBodyItem();
    }

    public Component(Margin margin, String name, String lore, BiConsumer<InventoryClickEvent, MenuWindow> delegate) {
        Coordinates = margin;
        Name        = name;
        Lore        = lore;
        Action      = delegate;

        Language            = "RU";
        BodyCustomModelData = MenuFramework.Config.getInt("default.default_data", 7000);
        BodyMaterial        = Material.matchMaterial(MenuFramework.Config.getString("default.default_material", "PAPER"));
        PersistentDataContainer = new ItemStack(Objects.requireNonNull(BodyMaterial)).getItemMeta().getPersistentDataContainer()
                .getAdapterContext().newPersistentDataContainer();
        genBodyItem();
    }

    private MenuWindow Parent;
    protected ItemStack BodyItem;
    protected Margin Coordinates;
    protected String Lore;
    protected String Name;
    protected PersistentDataContainer PersistentDataContainer;
    protected String Language;
    protected int BodyCustomModelData;
    protected Material BodyMaterial;
    protected BiConsumer<InventoryClickEvent, MenuWindow> Action;

    /**
     * Generate body item
     */
    protected void genBodyItem() {
        BodyItem = new Item(Name, Lore, BodyMaterial, 1, BodyCustomModelData);
        var meta = BodyItem.getItemMeta();

        PersistentDataContainer.copyTo(meta.getPersistentDataContainer(), true);
        BodyItem.setItemMeta(meta);
    }

    /**
     * Click interaction
     * @param click InventoryClickEvent
     */
    public void click(InventoryClickEvent click, MenuWindow parent) {
        if (isClicked(click.getSlot())) action(click, parent);
    }

    /**
     * Action for button
     * @param event InventoryClickEvent
     */
    public void action(InventoryClickEvent event, MenuWindow parent) {
        if (Action != null) Action.accept(event, parent);
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
        return getCoordinates().getSlots().contains(click);
    }

    /**
     * Displace component
     * @param inventory Inventory
     */
    public void displace(Inventory inventory) {
        for (var pos : getCoordinates().getSlots())
            if (Objects.requireNonNull(inventory.getItem(pos)).getName().equals(Name))
                inventory.setItem(pos, null);
    }

    /**
     * Move component in inventory
     * @param event Event clicker
     * @param position New position
     */
    public void Move(InventoryClickEvent event, Margin position) {
        displace(event.getInventory());
        setCoordinates(position);
        place(event.getInventory());
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T deepCopy() {
        try {
            return (T) getClass().getConstructor(getClass()).newInstance(this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public abstract void place(Inventory inventory);

    public abstract void place(Inventory inventory, List<String> lore);
}
