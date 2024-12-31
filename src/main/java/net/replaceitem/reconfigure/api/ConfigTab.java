package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.property.*;

import java.util.List;

public interface ConfigTab {
    Void createHeadline(String name);

    Void createHeadline(Text text);

    StringPropertyBuilder createStringProperty(String name);

    IntPropertyBuilder createIntegerProperty(String name);

    DoublePropertyBuilder createDoubleProperty(String name);

    BooleanPropertyBuilder createBooleanProperty(String name);

    <T extends Enum<T>> EnumPropertyBuilder<T> createEnumProperty(String name, Class<T> enumClass);

    <T> EnumPropertyBuilder<T> createEnumProperty(String name, List<T> values);

    ListPropertyBuilder createListProperty(String name);
}
