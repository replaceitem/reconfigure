package net.replaceitem.reconfigure.api.widget;

import net.minecraft.text.Text;

import java.util.Collection;
import java.util.function.Function;

public interface CyclingButtonWidgetBuilder<T> extends WidgetBuilder<CyclingButtonWidgetBuilder<T>, T> {
    /**
     * Sets the values for the cycling button. This defaults to all available values of the
     * property, but can be changed to include only a subset of all allowed values. However, all values
     * provided here must be valid values for the property.
     * @param values The values to set for the cycling button
     * @return The builder for chaining
     */
    CyclingButtonWidgetBuilder<T> values(Collection<T> values);

    /**
     * Sets the function for converting the current value of the cycling
     * button widget to the text to display on the button.
     * @param valueToText The text function
     * @return The builder for chaining
     */
    CyclingButtonWidgetBuilder<T> valueToText(Function<T, Text> valueToText);
}
