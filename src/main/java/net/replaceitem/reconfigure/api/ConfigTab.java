package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.property.BooleanPropertyBuilder;
import net.replaceitem.reconfigure.api.property.EnumPropertyBuilder;
import net.replaceitem.reconfigure.api.property.StringPropertyBuilder;
import net.replaceitem.reconfigure.config.property.builder.DoublePropertyBuilderImpl;
import net.replaceitem.reconfigure.config.property.builder.EnumPropertyBuilderImpl;
import net.replaceitem.reconfigure.config.property.builder.IntPropertyBuilderImpl;

import java.util.List;

public interface ConfigTab {
    Void createHeadline(String name);

    Void createHeadline(Text text);

    StringPropertyBuilder createStringProperty(String name);

    IntPropertyBuilderImpl createIntegerProperty(String name);

    DoublePropertyBuilderImpl createDoubleProperty(String name);

    BooleanPropertyBuilder createBooleanProperty(String name);

    <T extends Enum<T>> EnumPropertyBuilderImpl<T> createEnumProperty(String name, Class<T> enumClass);

    <T> EnumPropertyBuilder<T> createEnumProperty(String name, List<T> values);
}
