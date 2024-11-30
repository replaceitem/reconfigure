package net.replaceitem.reconfigure.config.widget;

import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

public class Widget<T> implements ConfigTabImpl.TabItem {
    private final PropertyImpl<T> property;
    private final ConfigWidgetFactory<T> configWidgetFactory;

    public Widget(PropertyImpl<T> property, ConfigWidgetFactory<T> configWidgetFactory) {
        this.property = property;
        this.configWidgetFactory = configWidgetFactory;
    }

    @Override
    public ConfigWidget createWidget(ConfigWidgetList configWidgetList) {
        return configWidgetFactory.createWidget(configWidgetList, property);
    }
}
