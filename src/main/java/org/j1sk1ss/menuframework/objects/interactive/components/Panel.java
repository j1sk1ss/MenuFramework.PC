package org.j1sk1ss.menuframework.objects.interactive.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ComponentClickEvent;
import org.j1sk1ss.menuframework.objects.MenuSizes;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;


public class Panel extends Component {
    /**
     * Buttons panel
     * @param components Components of panel
     * @param name Panel name
     */
    public Panel(List<Component> components, String name) {
        Name       = name;
        Lore       = "Panel lore lines";
        Components = components;
        MenuSize   = MenuSizes.SixLines;

        MenuFramework.ClickHandler.addHandler(this, name);
    }

    /**
     * Buttons panel
     * @param components Components of panel
     * @param name Panel name
     * @param size Size of menu
     */
    public Panel(List<Component> components, String name, MenuSizes size) {
        Name       = name;
        Lore       = "Panel lore lines";
        Components = components;
        MenuSize   = size;

        MenuFramework.ClickHandler.addHandler(this, name);
    }

    private final List<Component> Components;
    private final MenuSizes MenuSize;

    /**
     * Get button what was clicked
     * @param click Slot of inventory what was clicked
     */
    public void click(int click) {
        for (var component : Components) {
            if (component.isClicked(click)) {
                var clickEvent = new ComponentClickEvent(component, null, click, null);
                Bukkit.getPluginManager().callEvent(clickEvent);

                if (!clickEvent.isCancelled())
                    component.action(null);
            }
        }
    }

    @Override
    public void click(InventoryClickEvent click) {
        for (var component : Components) {
            if (component.isClicked(click.getSlot())) {
                var clickEvent = new ComponentClickEvent(component, (Player)click.getWhoClicked(), click.getSlot(), click);
                Bukkit.getPluginManager().callEvent(clickEvent);

                if (!clickEvent.isCancelled())
                    component.action(click);
            }
        }
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
     * @param customLore If you need to use custom lore
     */
    public void place(Inventory inventory, List<List<String>> customLore, List<String> names) {
        for (var component = 0; component < Components.size(); component++) {
            if (customLore.get(component) == null) {
                Components.get(component).place(inventory);
                continue;
            }

            for (var value : Components)
                if (value.getName().equals(names.get(component)))
                    value.place(inventory, customLore.get(component));
        }
    }

    /**
     * Place panel to inventory with additionsl components
     * @param inventory Player inventory
     * @param newComponents Additional components
     */
    public void placeWith(Inventory inventory, List<Component> newComponents) {
        for (var component : Components) component.place(inventory);
        for (var component : newComponents) component.place(inventory);
    }

    @Override
    public void displace(Inventory inventory) {
        for (var component : Components) component.displace(inventory);
    }

    /**
     * Get slider from panel by name
     * @param name Name of slider
     * @return Slider
     */
    public Slider getSliders(String name) {
        for (var component : Components)
            if (component instanceof Slider slider)
                if (slider.getName().equals(name))
                    return slider;

        return null;
    }

    /**
     * Get sliders from panel
     * @return Sliders
     */
    public List<Slider> getSliders() {
        var sliders = new ArrayList<Slider>();
        for (var component : Components)
            if (component instanceof Slider slider) sliders.add(slider);

        return sliders;
    }

    /**
     * Get button from panel by name
     * @param name Name of button
     * @return Button
     */
    public Button getButtons(String name) {
        for (var component : Components)
            if (component instanceof Button button)
                if (button.getName().equals(name))
                    return button;

        return null;
    }

    /**
     * Get buttons from panel
     * @return Buttons
     */
    public List<Button> getButtons() {
        var buttons = new ArrayList<Button>();
        for (var component : Components)
            if (component instanceof Button button) buttons.add(button);

        return buttons;
    }

    /**
     * Get checkbox from panel by name
     * @param name Name of checkbox
     * @return Checkbox
     */
    public Checkbox getCheckBoxes(String name) {
        for (var component : Components)
            if (component instanceof Checkbox checkbox)
                if (checkbox.getName().equals(name))
                    return checkbox;

        return null;
    }

    /**
     * Get checkboxes from panel
     * @return Checkboxes
     */
    public List<Checkbox> getCheckBoxes() {
        var checkboxes = new ArrayList<Checkbox>();
        for (var component : Components)
            if (component instanceof Checkbox checkbox) checkboxes.add(checkbox);

        return checkboxes;
    }

    /**
     * Get bar from panel by name
     * @param name Name of checkbox
     * @return Bar
     */
    public Bar getBars(String name) {
        for (var component : Components)
            if (component instanceof Bar bar)
                if (bar.getName().equals(name))
                    return bar;

        return null;
    }

    /**
     * Get bars from panel
     * @return Bars
     */
    public List<Bar> getBars() {
        var bars = new ArrayList<Bar>();
        for (var component : Components)
            if (component instanceof Bar bar) bars.add(bar);

        return bars;
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

    @Override
    public List<Integer> getCoordinates() {
        return null;
    }

    /**
     * Open panel as inventory
     * @param player Player, who will see panel as inventory
     */
    public void getView(Player player) {
        var window = Bukkit.createInventory(player, MenuSize.size, net.kyori.adventure.text.Component.text(getName()));
        place(window);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with custom lore for components
     * @param player Player, who will see panel as inventory
     * @param lore Custom lore
     */
    public void getView(Player player, List<String> lore) {
        var window = Bukkit.createInventory(player, MenuSize.size, net.kyori.adventure.text.Component.text(getName()));
        place(window, lore);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with custom lore for components
     * @param player Player, who will see panel as inventory
     * @param customLore Custom lore
     * @param names Names of components, that will take custom lore
     */
    public void getView(Player player, List<List<String>> customLore, List<String> names) {
        var window = Bukkit.createInventory(player, MenuSize.size, net.kyori.adventure.text.Component.text(getName()));
        place(window, customLore, names);
        player.openInventory(window);
    }

    /**
     * Open panel as inventory with additional components
     * @param player Player, who will see panel as inventory
     * @param newComponents Additional components
     */
    public void getViewWith(Player player, List<Component> newComponents) {
        var window = Bukkit.createInventory(player, MenuSize.size, net.kyori.adventure.text.Component.text(getName()));
        placeWith(window, newComponents);
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
    public void getView(List<String> lore, Inventory inventory) {
        place(inventory, lore);
    }

    /**
     * Place components to inventory with custom lore
     * @param customLore custom lore
     * @param names Names of components, that will take custom lore
     * @param inventory Inventory
     */
    public void getView(List<List<String>> customLore, List<String> names, Inventory inventory) {
        place(inventory, customLore, names);
    }

    /**
     * Open panel as inventory with additional components
     * @param newComponents Additional components
     * @param inventory Inventory
     */
    public void getViewWith(List<Component> newComponents, Inventory inventory) {
        placeWith(inventory, newComponents);
    }
}