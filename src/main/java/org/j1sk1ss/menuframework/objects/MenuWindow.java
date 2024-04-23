package org.j1sk1ss.menuframework.objects;

import org.bukkit.inventory.Inventory;
import org.j1sk1ss.menuframework.objects.interactive.components.Panel;

import java.util.ArrayList;
import java.util.List;


public class MenuWindow {
    public MenuWindow() {
        Panels = new ArrayList<>();
    }

    public MenuWindow(List<Panel> panels) {
        Panels = panels;
    }

    private List<Panel> Panels;

    /**
     * Get panel by name
     * @param name Panel name
     * @return Panel
     */
    public Panel getPanel(String name) {
        for (var panel : Panels)
            if (panel.getName().contains(name))
                return panel;

        return null;
    }

    /**
     * Place panel in inventory
     * @param name Panel name
     * @param inventory Inventory
     */
    public void placePanel(String name, Inventory inventory) {
        getPanel(name).place(inventory);
    }

    /**
     * Displace panel in inventory
     * @param name Panel name
     * @param inventory Inventory
     */
    public void displacePanel(String name, Inventory inventory) {
        getPanel(name).displace(inventory);
    }

    public void addPanel(Panel panel) {
        Panels.add(panel);
    }

    public void deletePanel(Panel panel) {
        Panels.remove(panel);
    }
}
