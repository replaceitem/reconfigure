package net.replaceitem.reconfigure.config.property.builder;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.Property;
import net.replaceitem.reconfigure.api.Validator;
import net.replaceitem.reconfigure.api.property.PropertyBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.PropertyHolder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.ValidatorList;
import net.replaceitem.reconfigure.config.serialization.TypeAdapter;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.config.widget.builder.CustomWidgetBuilderImpl;

import java.util.function.Function;

public abstract class PropertyBuilderImpl<SELF extends PropertyBuilder<SELF, T>, T> implements PropertyBuilder<SELF, T> {
    protected final PropertyBuildContext propertyBuildContext;
    protected T defaultValue;
    protected final Identifier id;
    protected final ValidatorList<T> validators = new ValidatorList<>();

    protected PropertyBuilderImpl(PropertyBuildContext propertyBuildContext, Identifier id, T defaultValue) {
        this.propertyBuildContext = propertyBuildContext;
        this.id = id;
        this.defaultValue = defaultValue;
    }

    @Override
    public SELF defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return self();
    }
    
    @Override
    public SELF addValidator(Validator<T> validator) {
        this.validators.add(validator);
        return self();
    }
    
    @Override
    public CustomWidgetBuilderImpl<T> asCustomWidget(Function<BaseSettings, ConfigWidgetFactory<T>> widgetFactorySupplier) {
        return new CustomWidgetBuilderImpl<>(propertyBuildContext, this, widgetFactorySupplier);
    }

    @Override
    public Property<T> buildWithoutWidget() {
        PropertyHolder<T> propertyHolder = this.build();
        this.propertyBuildContext.addProperty(propertyHolder);
        return propertyHolder.getProperty();
    }

    public Identifier getId() {
        return id;
    }

    protected abstract TypeAdapter<T, ?> getTypeAdapter();


    /**
     * This is run before calling {@link #buildImpl()}.
     * Can be used to fill in missing default values or validate values.
     */
    protected void preBuild() {
    }

    /**
     * This is run after {@link #buildImpl()} to add the created Property to the tab.
     */
    protected void postBuild(PropertyHolder<T> property) {
    }

    public final PropertyHolder<T> build() {
        this.preBuild();
        PropertyHolder<T> property = this.buildImpl();
        this.postBuild(property);
        return property;
    }

    protected PropertyHolder<T> buildImpl() {
        PropertyImpl<T> property = new PropertyImpl<>(id, defaultValue, validators);
        return new PropertyHolder<>(property, this.getTypeAdapter(), null);
    }

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
