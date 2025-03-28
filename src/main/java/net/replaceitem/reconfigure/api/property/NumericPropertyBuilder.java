package net.replaceitem.reconfigure.api.property;

public interface NumericPropertyBuilder<SELF extends NumericPropertyBuilder<SELF, T>, T extends Number> extends PropertyBuilder<SELF, T> {
    /**
     * Sets the minimum value for the numeric property (inclusive).
     * @param min The minimum value for the property (inclusive)
     * @return The builder for chaining
     */
    SELF min(T min);
    
    /**
     * Sets the maximum value for the numeric property (inclusive).
     * @param max The maximum value for the property (inclusive)
     * @return The builder for chaining
     */
    SELF max(T max);

    /**
     * Sets the minimum and maximum values for the numeric property (inclusive).
     * @param min The minimum value for the property (inclusive)
     * @param max The maximum value for the property (inclusive)
     * @return The builder for chaining
     */
    SELF range(T min, T max);
}
