package net.replaceitem.reconfigure.api.widget;

public interface SliderWidgetBuilder<SELF extends SliderWidgetBuilder<SELF, T>, T extends Number & Comparable<T>> extends WidgetBuilder<SELF, T> {
    /**
     * The minimum value for the slider. By default, this is the minimum value of the property (if present),
     * but can be changed to differ from the allowed range of the property.
     * @param min The minimum value
     * @return The builder for chaining
     */
    SELF min(T min);
    
    /**
     * The maximum value for the slider. By default, this is the maximum value of the property (if present),
     * but can be changed to differ from the allowed range of the property.
     * @param max The maximum value
     * @return The builder for chaining
     */
    SELF max(T max);

    /**
     * Convenience method for setting {@link SliderWidgetBuilder#min(Number)} and
     * {@link SliderWidgetBuilder#max(Number)} at once.
     * @param min The minimum value of the slider
     * @param max The maximum value of the slider
     * @return The builder for chaining
     */
    default SELF range(T min, T max) {
        return min(min).max(max);
    }

    /**
     * The step distance for the slider. When set, the slider can only set values in this increment.
     * A step of 5 will mean your slider has the values {@code min, min+5, min+10, ...}.
     * @param step The step of the slider
     * @return The builder for chaining
     */
    SELF step(T step);
}
