package org.j1sk1ss.menuframework.objects.interactive.components;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ComponentClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.item.Item;

import java.util.ArrayList;
import java.util.List;


public class Panel extends Component {
    /**
     * Buttons panel
     * @param components Components of panel
     */
    public Panel(List<Component> components, String name) {
        Name       = name;
        Components = components;

        MenuFramework.ClickHandler.addHandler(components, name);
    }

    private final String Name;
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

    /**
     * Place components body to new inventory
     * @param inventory Inventory where should be placed components
     * @param customLore If you need to use custom lore
     */
    public void place(Inventory inventory, List<String> customLore) {
        for (var component = 0; component < Components.size(); component++) {
            var coordinates = Components.get(component).getCoordinates();

            if (Components.get(component) instanceof Button button) {
                var text = button.getName();
                for (var coordinate : coordinates)
                    inventory.setItem(coordinate, new Item(text, customLore.get(component), Material.PAPER, 1, MenuFramework.Config.getInt("buttons_data_model")));
            
            } 
            else if (Components.get(component) instanceof Slider slider) 
                slider.place(inventory);
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

    @Override
    public boolean isClicked(int click) {
        for (var component : Components) if (component.isClicked(click)) return true;
        return false;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getLoreLines() {
        return "Panel lore lines";
    }

    @Override
    public List<Integer> getCoordinates() {
        return null;
    }
}
