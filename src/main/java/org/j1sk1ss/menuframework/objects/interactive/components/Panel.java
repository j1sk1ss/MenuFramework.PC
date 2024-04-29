package org.j1sk1ss.menuframework.objects.interactive.components;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ComponentClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.ArrayList;
import java.util.List;


public class Panel extends Component {
    /**
     * Buttons panel
     * @param components Components of panel
     */
    public Panel(List<Component> components, String name) {
        Name       = name;
        Lore       = "Panel lore lines";
        Components = components;

        MenuFramework.ClickHandler.addHandler(this, name);
    }

    private final List<Component> Components;

    /**
     * Get button what was clicked
     * @param click Slot of inventory what was clicked
     */
    public void click(int click) {
        for (var component : Components) {
            if (component.isClicked(click)) {
                var clickEvent = new ComponentClickEvent(component, null, click);
                Bukkit.getPluginManager().callEvent(clickEvent);

                if (!clickEvent.isCancelled())
                    component.action(null);
            }
        }
    }

    /**
     * Get button what was clicked
     * @param click Slot of inventory what was clicked
     */
    @Override
    public void click(InventoryClickEvent click) {
        for (var component : Components) {
            if (component.isClicked(click.getSlot())) {
                var clickEvent = new ComponentClickEvent(component, (Player)click.getWhoClicked(), click.getSlot());
                Bukkit.getPluginManager().callEvent(clickEvent);

                if (!clickEvent.isCancelled())
                    component.action(click);
            }
        }
    }

    /**
     * Place components body to new inventory
     * @param inventory Inventory where should be placed components
     */
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

            if (Components.get(component).getName().equals(names.get(component)))
                Components.get(component).place(inventory, customLore.get(component));
        }
    }

    /**
     * Displace components body to new inventory
     * @param inventory Inventory where should be displaced components
     */
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
}
