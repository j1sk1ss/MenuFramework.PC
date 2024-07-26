package org.j1sk1ss.menuframework.objects;

import lombok.Getter;

import org.bukkit.inventory.Inventory;

import org.j1sk1ss.menuframework.common.LocalizationManager;
import org.j1sk1ss.menuframework.objects.interactive.components.Panel;

import java.util.ArrayList;
import java.util.List;


public class MenuWindow {
    public MenuWindow() {
        Panels     = new ArrayList<>();
        Name       = "Menu";
        LocManager = null;
    }

    public MenuWindow(Panel panel) {
        panel.setParent(this);
        Panels = List.of(panel);
        Name   = "Menu";
        LocManager = null;
    }

    public MenuWindow(List<Panel> panels) {
        Panels = panels;
        registerChildren();

        Name = "Menu";
        LocManager = null;
    }

    public MenuWindow(List<Panel> panels, String name) {
        Panels = panels;
        registerChildren();

        Name = name;
        LocManager = null;
    }

    public MenuWindow(List<Panel> panels, String name, LocalizationManager localizationManager) {
        Panels = panels;
        registerChildren();

        Name = name;
        LocManager = localizationManager;
    }

    @Getter private final String Name;
    private final List<Panel> Panels;
    @Getter private final LocalizationManager LocManager;

    private void registerChildren() {
        for (var p : Panels) {
            p.setParent(this);
            for (var c : p.getComponents())
                c.setParent(this);

            p.registerAsHandler();
        }
    }

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
     * Get panel by name
     * @param name Panel name
     * @param language Language of localization
     * @return Panel (translated)
     */
    public Panel getPanel(String name, String language) {
        for (var panel : Panels)
            if (panel.getName().contains(name)) {
                if (LocManager == null) return panel;
                return (Panel) LocManager.translate(panel, language);
            }

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

    /**
     * Add panel to menu
     * @param panel Panel
     */
    public void addPanel(Panel panel) {
        Panels.add(panel);
    }

    /**
     * Remove panel from menu
     * @param panel Panel
     */
    public void deletePanel(Panel panel) {
        Panels.remove(panel);
    }
}
