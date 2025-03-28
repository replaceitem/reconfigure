package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

/**
 * A config widget factory models a function for creating a widget instance.
 * 
 * @param <T> The type of the property that is handled with this widget.
 */
@FunctionalInterface
public interface ConfigWidgetFactory<T> {
    /**
     * Creates a widget for the provided parent config widget list and the given property.
     * @param parent   The config widget list for providing it to the constructor of the config widget
     * @param property The property used for this widget
     * @return The created config widget
     */
    ConfigWidget createWidget(ConfigWidgetList parent, PropertyImpl<T> property);
}
