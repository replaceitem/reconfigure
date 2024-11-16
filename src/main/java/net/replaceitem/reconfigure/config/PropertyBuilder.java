package net.replaceitem.reconfigure.config;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;

import java.util.function.Consumer;

public abstract class PropertyBuilder <SELF extends PropertyBuilder<SELF, T>, T> {
    protected final Consumer<Property<T>> propertyConsumer;
    protected T defaultValue;
    protected Identifier id;
    protected ConfigWidgetFactory<T> widgetFactory;

    protected PropertyBuilder(Consumer<Property<T>> propertyConsumer, Identifier id) {
        this.propertyConsumer = propertyConsumer;
        this.id = id;
    }

    public SELF defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return self();
    }
    
    public Property<T> asCustomWidget(ConfigWidgetFactory<T> widgetFactory) {
        this.widgetFactory = widgetFactory;
        return build();
    }


    /**
     * This is run before calling {@link #buildImpl()}.
     * Can be used to fill in missing default values.
     */
    protected void preBuild() {
        if(defaultValue == null) throw new IllegalStateException("defaultValue not set");
    }

    /**
     * This is run after {@link #buildImpl()} to add the created Property to the tab.
     */
    protected void postBuild(Property<T> property) {
        this.propertyConsumer.accept(property);
    }

    protected final Property<T> build() {
        this.preBuild();
        Property<T> property = this.buildImpl();
        this.postBuild(property);
        return property;
    }

    protected Property<T> buildImpl() {
        return new Property<>(id, defaultValue, widgetFactory);
    }

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
