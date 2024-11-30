package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.property.PropertyBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.config.widget.builder.CustomWidgetBuilderImpl;

import java.util.function.Function;

public abstract class PropertyBuilderImpl<SELF extends PropertyBuilder<SELF, T>, T> implements PropertyBuilder<SELF, T> {
    protected final PropertyBuildContext propertyBuildContext;
    protected T defaultValue;
    protected Identifier id;

    protected PropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id) {
        this.propertyBuildContext = propertyBuildContext;
        this.id = id;
    }

    @Override
    public SELF defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return self();
    }
    
    @Override
    public CustomWidgetBuilderImpl<T> asCustomWidget(Function<BaseSettings, ConfigWidgetFactory<T>> widgetFactorySupplier) {
        return new CustomWidgetBuilderImpl<>(propertyBuildContext, this, widgetFactorySupplier);
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
    protected void postBuild(PropertyImpl<T> property) {
        this.propertyBuildContext.addProperty(property);
    }

    public final PropertyImpl<T> build() {
        this.preBuild();
        PropertyImpl<T> property = this.buildImpl();
        this.postBuild(property);
        return property;
    }

    protected PropertyImpl<T> buildImpl() {
        return new PropertyImpl<>(id, defaultValue);
    }

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
