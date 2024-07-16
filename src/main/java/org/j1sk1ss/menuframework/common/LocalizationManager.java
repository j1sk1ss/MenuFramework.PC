package org.j1sk1ss.menuframework.common;

import org.cordell.com.cordelldb.manager.Manager;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.interactive.components.Panel;

import java.io.IOException;
import java.util.ArrayList;


public class LocalizationManager {
    public LocalizationManager(String localization) {
        LocManager = new Manager(localization);
    }

    public LocalizationManager(Manager manager) {
        LocManager = manager;
    }

    private final Manager LocManager;

    /**
     * Translate component
     * @param component Component for translate
     * @return Translated component (deep copy)
     */
    public Component translate(Component component, String language) {
        var translatedComponent = component.deepCopy();
        try {
            var localKey = LocManager.getString(language + "_" + component.getName());
            if (localKey == null) return component;

            var translation = localKey.split("/");
            if (!translation[0].equals("-")) translatedComponent.setName(translation[0]);
            if (!translation[1].equals("-")) translatedComponent.setLore(translation[1]);

            if (component instanceof Panel panel) {
                var translatedPanel = ((Panel)translatedComponent);
                translatedPanel.setComponents(new ArrayList<>());
                for (var cmp : panel.getComponents())
                    translatedPanel.addComponent(translate(cmp, language));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return translatedComponent;
    }
}