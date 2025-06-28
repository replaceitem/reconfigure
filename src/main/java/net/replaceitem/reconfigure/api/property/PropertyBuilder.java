package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.Property;
import net.replaceitem.reconfigure.api.Validator;
import net.replaceitem.reconfigure.api.widget.CustomWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;

import java.util.function.Function;

public interface PropertyBuilder<SELF extends PropertyBuilder<SELF, T>, T> {
    /**
     * Sets the default value for the property. This value is used as the initial value of the property
     * and when deserialization without a value present.
     * @param defaultValue The default value for the property
     * @return The builder for chaining
     */
    SELF defaultValue(T defaultValue);

    /**
     * Adds a validator to the property.
     * Validators are used to verify whether a value is allowed to be set for a property.
     * @see Validator
     * @param validator The validator to add
     * @return The builder for chaining
     */
    SELF addValidator(Validator<T> validator);

    /**
     * Finishes building the property and starts building a custom widget for it.
     * A function must be supplied, which returns a widget factory for your custom widget.
     * The function provides some base information which is configured in the returned custom widget builder.
     * This data is to be used in your custom widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * 
     * @param widgetFactorySupplier The supplier to construct a widget instance
     * @return The custom widget builder
     */
    CustomWidgetBuilder<T> asCustomWidget(Function<BaseSettings, ConfigWidgetFactory<T>> widgetFactorySupplier);

    /**
     * Finishes building the property and directly returns it, without creating a widget for it.
     * @return The build Property
     */
    Property<T> buildWithoutWidget();
}
