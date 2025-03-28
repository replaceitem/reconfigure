package net.replaceitem.reconfigure.api.widget;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.Property;

public interface WidgetBuilder<SELF extends WidgetBuilder<SELF, T>, T> {
    /**
     * Sets the display name of the widget as rendered in the config screen.
     * When not set, defaults to a translatable text with key {@code reconfigure.property.[namespace].[propertyname]}.
     * @param displayName The display name text
     * @return The builder for chaining
     */
    SELF displayName(Text displayName);

    /**
     * Sets the tooltip text for the widget. This is shown when hovering over
     * the property display name in the config widget.
     * @param tooltip The tooltip text
     * @return The builder for chaining
     */
    SELF tooltip(Text tooltip);

    /**
     * Enabled the tooltip for the widget. The text of the tooltip will default to
     * a translatable text with key {@code reconfigure.property.[namespace].[propertyname].tooltip}.
     * @return The builder for chaining
     */
    SELF tooltip();

    /**
     * Builds the widget and the property. The built widget and property is automatically added
     * to the config tab on which this builder was created.
     * @return The built property
     */
    Property<T> build();
}
