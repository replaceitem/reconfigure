package net.replaceitem.reconfigure.config;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

public class Property<T> extends Binding<T> implements ConfigTab.TabItem {
    private final Identifier id;
    private final T defaultValue;
    private final ConfigWidgetFactory<T> configWidgetFactory;

    protected Property(Identifier id, T defaultValue, ConfigWidgetFactory<T> configWidgetFactory) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.configWidgetFactory = configWidgetFactory;
        this.value = defaultValue;
    }

    public void reset() {
        this.set(defaultValue);
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public ConfigWidget createWidget(ConfigWidgetList configWidgetList) {
        return configWidgetFactory.createWidget(configWidgetList, this);
    }


}
