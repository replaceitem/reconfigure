package net.replaceitem.reconfigure.api;

import net.minecraft.util.Identifier;

public interface Property<T> extends Bindable<T> {
    /**
     * Sets the value of the property. If the provided value is invalid according to the validators,
     * an {@link IllegalArgumentException} is thrown.
     * @param value The value to set
     * @throws IllegalArgumentException If the valid is invalid
     */
    void set(T value) throws IllegalArgumentException;

    /**
     * Sets the value of the property. If the provided value is invalid according to the validators,
     * the previous value is kept and no listeners are invoked.
     * @param value The value to set
     */
    void setIfValid(T value);

    /**
     * Sets the value of the property. If the provided value is invalid according to the validators,
     * the default value of the property will be set instead.
     * @param value The value to set
     */
    void setOrDefault(T value);

    /**
     * Resets the property to the default value.
     */
    void reset();

    /**
     * Gets the default value of the property.
     * @return The default value of the property
     */
    T getDefaultValue();

    /**
     * Returns whether the property is currently set to the default value according to
     * {@link java.util.Objects#equals(Object, Object)}.
     * @return {@code true} if the value of the property equals the default value, {@code false} otherwise
     */
    boolean isDefault();

    /**
     * Returns whether the value is equal to the default value according to
     * {@link java.util.Objects#equals(Object, Object)}.
     * @param value The value to check whether it is equal to the properties default value
     * @return {@code true} if the value equals the default value, {@code false} otherwise
     */
    boolean isDefault(T value);

    /**
     * Gets the identifier of the property. This consists of the namespace provided when building the
     * config with {@link Config#builder(String)} and the name of the property provided when building the
     * property with {@link ConfigTab}{@code .create???Property()}.
     * @return The identifier of the property.
     */
    Identifier getId();
}
