package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.property.*;

import java.util.List;

public interface ConfigTab {
    /**
     * Creates a headline in the config tab.
     * The name is used for the translatable text with key {@code reconfigure.headline.[namespace].[name]}.
     * @param name The name of the headline
     * @return {@link Void} in order to be able to invoke this inline in a class field
     * initializer using an assignment to a dummy {@link Void} field.
     */
    Void createHeadline(String name);
    
    /**
     * Creates a headline in the config tab with the provided text.
     * @param text The text of the headline
     * @return {@link Void} in order to be able to invoke this inline in a class field
     * initializer using an assignment to a dummy {@link Void} field.
     */
    Void createHeadline(Text text);

    /**
     * Starts building a string property for this config tab.
     * @param name The name of the property
     * @return The builder for chaining
     */
    StringPropertyBuilder createStringProperty(String name);
    
    /**
     * Starts building an integer property for this config tab.
     * @param name The name of the property
     * @return The builder for chaining
     */
    IntPropertyBuilder createIntegerProperty(String name);
    
    /**
     * Starts building a double property for this config tab.
     * @param name The name of the property
     * @return The builder for chaining
     */
    DoublePropertyBuilder createDoubleProperty(String name);

    /**
     * Starts building a boolean property for this config tab.
     * @param name The name of the property
     * @return The builder for chaining
     */
    BooleanPropertyBuilder createBooleanProperty(String name);

    /**
     * Starts building an enum property for this config tab.
     * @param name The name of the property
     * @param enumClass The enum class of which all enum values will be used for the property
     * @return The builder for chaining
     */
    <T extends Enum<T>> EnumPropertyBuilder<T> createEnumProperty(String name, Class<T> enumClass);

    /**
     * Starts building an enum property for this config tab.
     * @param name The name of the property
     * @param values The values for the enum property. These do not need to be values of an actual enum
     * @return The builder for chaining
     */
    <T> EnumPropertyBuilder<T> createEnumProperty(String name, List<T> values);

    /**
     * Starts building a list property for this config tab.
     * @param name The name of the property
     * @return The builder for chaining
     */
    ListPropertyBuilder createListProperty(String name);
}
