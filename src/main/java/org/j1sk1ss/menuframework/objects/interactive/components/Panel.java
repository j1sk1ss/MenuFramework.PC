package org.j1sk1ss.menuframework.objects.interactive.components;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.common.CharSpacing;
import org.j1sk1ss.menuframework.events.ComponentClickEvent;
import org.j1sk1ss.menuframework.objects.MenuSizes;
import org.j1sk1ss.menuframework.objects.MenuWindow;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.nonInteractive.Margin;

import java.util.List;
import java.util.Map;


@Getter
public class Panel extends Component {
    public Panel(Panel panel) {
        super(panel);

        Ui         = panel.Ui;
        Components = panel.Components;
        MenuSize   = panel.MenuSize;
    }

    /**
     * Component panel
     * @param components Components in panel
     * @param name Panel name
     */
    public Panel(List<Component> components, String name) {
        super(new Margin(-1, -1), name, "PanelLore", null);

        Ui         = "";
        Components = components;
        MenuSize   = MenuSizes.SixLines;
    }

    /**
     * Component panel
     * @param components Components in panel
     * @param name Panel name
     * @param size Size of menu
     */
    public Panel(List<Component> components, String name, MenuSizes size) {
        super(new Margin(-1, -1), name, "PanelLore", null);

        Ui         = "";
        Components = components;
        MenuSize   = size;
    }

    /**
     * Component panel
     * @param components Components in panel
     * @param name Panel name
     * @param size Size of menu
     * @param ui UI symbols. Check this article how to do: <a href="https://www.spigotmc.org/threads/custom-inventory-uis-updated.635897/"> How to basic custom GUI </a>
     */
    public Panel(List<Component> components, String name, MenuSizes size, String ui) {
        super(new Margin(-1, -1), name, "PanelLore", null);

        Ui         = "§f" + CharSpacing.getNeg(8) + ui;
        Components = components;
        MenuSize   = size;
    }

    /**
     * Component panel
     * @param components Components in panel
     * @param name Panel name
     * @param size Size of menu
     * @param ui UI symbols. Check this article how to do: <a href="https://www.spigotmc.org/threads/custom-inventory-uis-updated.635897/"> How to basic custom GUI </a>
     * @param color Color symbols. Check this article how to do: <a href="https://minecraft.fandom.com/wiki/Formatting_codes"> Color codes minecraft </a>
     */
    public Panel(List<Component> components, String name, MenuSizes size, String ui, String color) {
        super(new Margin(-1, -1), name, "PanelLore", null);

        Ui         = color + CharSpacing.getNeg(8) + ui;
        Components = components;
        MenuSize   = size;
    }

    private final String Ui;
    @Setter private List<Component> Components;
    @Setter private MenuSizes MenuSize;

    /**
     * Get button what was clicked
     * @param click Slot of inventory what was clicked
     */
    public void click(int click) {
        Components.forEach(component -> {
            if (component.isClicked(click)) {
                var clickEvent = new ComponentClickEvent(component, null, click, null);
                Bukkit.getPluginManager().callEvent(clickEvent);
                if (!clickEvent.isCancelled()) component.action(null, null);
            }
        });
    }

    @Override
    public void click(InventoryClickEvent click, MenuWindow parent) {
        Components.forEach(component -> {
            if (component.isClicked(click.getSlot())) {
                var clickEvent = new ComponentClickEvent(component, (Player) click.getWhoClicked(), click.getSlot(), click);
                Bukkit.getPluginManager().callEvent(clickEvent);
                if (!clickEvent.isCancelled()) component.action(click, parent);
            }
        });
    }

    @Override
    public void place(Inventory inventory) {
        for (var component : Components) component.place(inventory);
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        for (var component : Components) component.place(inventory, lore);
    }

    /**
     * Place components body to new inventory
     * @param inventory Inventory where should be placed components
     * @param newLore If you need to use custom lore (name - lore)
     */
    public void place(Inventory inventory, Map<String, List<String>> newLore) {
        for (var component = 0; component < Components.size(); component++) {
            for (var value : Components) {
                if (getParent().getLocManager() == null) {
                    if (newLore.get(Components.get(component).getName()) == null) Components.get(component).place(inventory);
                    else  value.place(inventory, newLore.get(value.getName()));
                }
                else {
                    value.place(inventory, newLore.get(getParent().getLocManager().getSourceName(value.getName())));
                }
            }
        }
    }

    /**
     * Place panel to inventory with additionsl components
     * @param inventory Player inventory
     * @param newComponents Additional components
     */
    public void placeWith(Inventory inventory, List<Component> newComponents) {
        for (var component : Components) component.place(inventory);
        for (var component : newComponents) {
            if (getParent().getLocManager() != null) getParent().getLocManager().translate(component, Language).place(inventory);
            else component.place(inventory);
        }
    }

    @Override
    public void displace(Inventory inventory) {
        for (var component : Components) component.displace(inventory);
    }

    /**
     * Get slider from panel by name
     * @param name Name of slider
     * @param target Target class
     * @return Slider
     */
    public <T extends Component> T getComponent(String name, Class<T> target) {
        return Components.stream()
            .filter(component -> component.getName().equals(name) && target.isInstance(component))
            .map(target::cast)
            .findFirst()
            .orElse(null);
    }

    /**
     * Get slider from panel by name
     * @param name Name of slider
     * @return Slider
     */
    public Component getComponent(String name) {
        return Components.stream().filter(component -> component.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Resize panel
     * @param menuSize New menu size
     * @return Resized panel (Deep copy)
     */
    public Panel resize(MenuSizes menuSize) {
        var copyPanel = (Panel) deepCopy();
        copyPanel.setMenuSize(menuSize);
        return copyPanel;
    }

    /**
     * Resize panel
     * @param size New menu size in slots (Check it for division by 9)
     * @return Resized panel (Deep copy)
     */
    public Panel resize(int size) {
        var copyPanel = (Panel) deepCopy();
        copyPanel.setMenuSize(MenuSizes.fromInt(size));
        return copyPanel;
    }

    /**
     * Add component to panel
     * @param component Component for adding
     */
    public void addComponent(Component component) {
        Components.add(component);
    }

    /**
     * Delete component from panel
     * @param component Component to delete
     */
    public void deleteComponent(Component component) {
        Components.remove(component);
    }

    @Override
    public boolean isClicked(int click) {
        for (var component : Components) if (component.isClicked(click)) return true;
        return false;
    }

    /**
     * Open panel as inventory
     * @param player Player, who will see panel as inventory
     */
    public void getView(Player player) {
        var window = getWindow(player, getName(), MenuSize.size);
        place(window);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with custom lore for components
     * @param player Player, who will see panel as inventory
     * @param lore Custom lore
     */
    public void getView(Player player, List<String> lore) {
        var window = getWindow(player, getName(), MenuSize.size);
        place(window, lore);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with custom lore for components
     * @param player Player, who will see panel as inventory
     * @param newLore Custom lore (name - lore)
     */
    public void getView(Player player, Map<String, List<String>> newLore) { // TODO: Localization break
        var window = getWindow(player, getName(), MenuSize.size);
        place(window, newLore);
        player.openInventory(window);
    }

    /**
     * Open panel in inventory with different title
     * @param player Player owner
     * @param newTitle New title
     */
    public void getView(Player player, String newTitle) {
        var window = getWindow(player, newTitle, MenuSize.size);
        place(window);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with custom lore for components and different title
     * @param player Player, who will see panel as inventory
     * @param newTitle New title
     * @param lore Custom lore
     */
    public void getView(Player player, String newTitle, List<String> lore) {
        var window = getWindow(player, newTitle, MenuSize.size);
        place(window, lore);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with custom lore for components and different title
     * @param player Player, who will see panel as inventory
     * @param newTitle New title
     * @param newLore Custom lore (name - lore)
     */
    public void getView(Player player, String newTitle, Map<String, List<String>> newLore) {
        var window = getWindow(player, newTitle, MenuSize.size);
        place(window, newLore);
        player.openInventory(window);
    }

    /**
     * Place components to inventory
     * @param inventory Inventory
     */
    public void getView(Inventory inventory) {
        place(inventory);
    }

    /**
     * Place components to inventory with custom lore
     * @param lore custom lore
     * @param inventory Inventory
     */
    public void getView(Inventory inventory, List<String> lore) {
        place(inventory, lore);
    }

    /**
     * Place components to inventory with custom lore
     * @param newLore custom lore (name - lore)
     * @param inventory Inventory
     */
    public void getView(Inventory inventory, Map<String, List<String>> newLore) {
        place(inventory, newLore);
    }

    /**
     * Open panel as inventory with additional components
     * @param player Player, who will see panel as inventory
     * @param newComponents Additional components
     */
    public void getViewWith(Player player, List<Component> newComponents) {
        var window = getWindow(player, getName(), MenuSize.size);
        placeWith(window, newComponents);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with additional components
     * @param player Player, who will see panel as inventory
     * @param newTitle New title
     * @param newComponents Additional components
     */
    public void getViewWith(Player player, String newTitle, List<Component> newComponents) {
        var window = getWindow(player, newTitle, MenuSize.size);
        placeWith(window, newComponents);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with additional components
     * @param newComponents Additional components
     * @param inventory Inventory
     */
    public void getViewWith(List<Component> newComponents, Inventory inventory) {
        placeWith(inventory, newComponents);
    }

    private Inventory getWindow(Player player, String title, int size) {
        var componentTitle = net.kyori.adventure.text.Component.text(Ui + title);
        return Bukkit.createInventory(player, size, componentTitle);
    }

    public void registerAsHandler() {
        MenuFramework.ClickHandler.addHandler(this, getName());
    }
}
