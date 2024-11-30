package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

@FunctionalInterface
public interface ConfigWidgetFactory<T> {
    ConfigWidget createWidget(ConfigWidgetList parent, PropertyImpl<T> property);
}
