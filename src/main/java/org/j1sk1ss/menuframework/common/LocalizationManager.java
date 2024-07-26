package org.j1sk1ss.menuframework.common;

import org.cordell.com.cordelldb.manager.Manager;
import org.j1sk1ss.menuframework.objects.interactive.Component;
import org.j1sk1ss.menuframework.objects.interactive.components.Panel;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Localization manager based on CordellDB
 * Main idea, that every MenuWindow will have own CDB for translation
 * For creation a new localization file you will need:
 *      - Every line of file - one component
 *      - Key and value in CordellDB separated by ":"
 *      - Key should have LNG_ prefix. Also key should copy name of component for localization
 *          - Example: EN_button1:ButtonName/ButtonLore
 *          - Example: EN_panel:InventoryName/-
 *          - Example: IT_button1:ButtonNome/ButtonStoria
 *          - Example: RU_button1:Кнопка/- (- means saving lore (Or name))
 *  Then just get panel with lang (from prefix), and show it for player:
 *      - Example: windowMenu.getPanel("panel1", "RU").getView(player);
 *  Notes:
 *      - Avoid localization for panels
 *      - Remember that MenuFramework don`t know player`s native language. You should detect this by yourself.
 */
public class LocalizationManager {
    public LocalizationManager(String localization) {
        try {
            LocManager = new Manager(localization);
        }
        catch (Exception e) {
            System.out.println("Unable access to data: " + e.getMessage());
            LocManager = null;
        }
    }

    public LocalizationManager(Manager manager) {
        LocManager = manager;
    }

    private Manager LocManager;

    /**
     * Translate line to language
     * @param line Line to translate
     * @param language Language
     * @return Translated line
     */
    public String translate(String line, String language) {
        try {
            if (LocManager == null) return line;

            var localKey = LocManager.getString(language + "_" + line);
            if (localKey == null) return line;

            return localKey;
        } catch (IOException e) {
            System.err.println("Error while trying to translate '" +line + "' to " + language
                    + "\nLOG: " + e);
        }

        return line;
    }

    /**
     * Translate component
     * @param component Component for translate
     * @return Translated component (deep copy)
     */
    public Component translate(Component component, String language) {
        try {
            if (LocManager == null) return component;

            var translatedComponent = component.deepCopy();
            if (component instanceof Panel panel) {
                var translatedPanel = ((Panel)translatedComponent);
                translatedPanel.setComponents(new ArrayList<>());
                for (var cmp : panel.getComponents())
                    translatedPanel.addComponent(translate(cmp, language));
            }

            var localKey = LocManager.getString(language + "_" + component.getName());
            if (localKey == null) return translatedComponent;

            var translation = localKey.split("/", 2);
            if (!translation[0].equals("-")) translatedComponent.setName(translation[0]);
            if (!translation[1].equals("-")) translatedComponent.setLore(translation[1]);

            translatedComponent.setLanguage(language);
            return translatedComponent;
        } catch (IOException e) {
            System.err.println("Error while trying to translate component " + component.getName() + " to " + language
            + "\nLOG: " + e);
        }

        return component;
    }
}
