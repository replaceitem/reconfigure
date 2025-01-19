package net.replaceitem.reconfigure.config;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.serialization.*;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PropertyHolder<T> implements ConfigTabImpl.TabItem, SerializationTarget.SerializationProperty<T> {
    private final PropertyImpl<T> property;
    
    @Nullable private final ConfigWidgetFactory<T> configWidgetFactory;
    
    private final TypeAdapter<T,?> typeAdapter;

    public PropertyHolder(PropertyImpl<T> property, TypeAdapter<T,?> typeAdapter, @Nullable ConfigWidgetFactory<T> configWidgetFactory) {
        this.property = property;
        this.typeAdapter = typeAdapter;
        this.configWidgetFactory = configWidgetFactory;
    }

    public PropertyImpl<T> getProperty() {
        return property;
    }

    @Override
    public TypeAdapter<T, ?> getTypeAdapter() {
        return typeAdapter;
    }

    public void set(T value) {
        this.property.set(value);
    }
    
    public void setIfValid(T value) {
        this.property.setIfValid(value);
    }
    
    @Override
    public void setOrDefault(T value) {
        this.property.setOrDefault(value);
    }

    @Override
    public T get() {
        return this.property.get();
    }

    @Override
    public Identifier getId() {
        return property.getId();
    }

    public PropertyHolder<T> withWidgetFactory(ConfigWidgetFactory<T> configWidgetFactory) {
        return new PropertyHolder<>(this.property, this.typeAdapter, configWidgetFactory);
    }

    @Override
    public Optional<ConfigWidget> createWidget(ConfigWidgetList parent) {
        return Optional.ofNullable(this.configWidgetFactory)
                .map(factory -> factory.createWidget(parent,property));
    }
}
