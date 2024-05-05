package org.j1sk1ss.menuframework.objects.interactive.components;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.menuframework.MenuFramework;
import org.j1sk1ss.menuframework.events.ButtonClickEvent;
import org.j1sk1ss.menuframework.objects.interactive.Component;

import java.util.List;
import java.util.function.Consumer;


public class LittleButton extends Component {
    public LittleButton(int position) {
        this.Position = position;

        this.Name     = "LButton";
        this.Lore     = "LButtonLore";
        this.Material = org.bukkit.Material.PAPER;
        this.Action   = null;
    }

    public LittleButton(int position, String name, String lore) {
        this.Position = position;
        this.Name     = name;
        this.Lore     = lore;

        this.Material = org.bukkit.Material.PAPER;
        this.Action   = null;
    }

    public LittleButton(int position, String name, String lore, Consumer<InventoryClickEvent> delegate) {
        this.Position = position;
        this.Name     = name;
        this.Lore     = lore;
        this.Action   = delegate;

        this.Material = org.bukkit.Material.PAPER;
    }

    public LittleButton(int position, String name, String lore, Consumer<InventoryClickEvent> delegate, Material material) {
        this.Position = position;
        this.Name     = name;
        this.Lore     = lore;
        this.Material = material;
        this.Action   = delegate;
    }

    private final int Position;
    private final Material Material;
    private final Consumer<InventoryClickEvent> Action;

    @Override
    public void place(Inventory inventory) {
        inventory.setItem(Position, new Item(Name, Lore, Material, 1, MenuFramework.Config.getInt("little_button_data_model")));
    }

    @Override
    public void place(Inventory inventory, List<String> lore) {
        inventory.setItem(Position, new Item(Name, String.join(" ", lore), Material, 1, MenuFramework.Config.getInt("little_button_data_model")));
    }

    @Override
    public void displace(Inventory inventory) {
        inventory.setItem(Position, null);
    }

    @Override
    public boolean isClicked(int click) {
        return Position == click;
    }

    @Override
    public List<Integer> getCoordinates() {
        return List.of(Position);
    }

    @Override
    public void action(InventoryClickEvent event) {
        var clickEvent = new ButtonClickEvent(event.getSlot(), this, (Player)event.getWhoClicked(), event);
        Bukkit.getPluginManager().callEvent(clickEvent);

        if (Action != null) Action.accept(event);
    }
}
