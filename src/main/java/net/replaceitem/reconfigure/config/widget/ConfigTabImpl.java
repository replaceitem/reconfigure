package net.replaceitem.reconfigure.config.widget;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.ConfigTab;
import net.replaceitem.reconfigure.api.property.BooleanPropertyBuilder;
import net.replaceitem.reconfigure.api.property.EnumPropertyBuilder;
import net.replaceitem.reconfigure.api.property.StringPropertyBuilder;
import net.replaceitem.reconfigure.config.ConfigImpl;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.property.builder.*;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigTabImpl implements ConfigTab, PropertyBuildContext {

    final ConfigImpl config;
    private final String name;
    private final Text title;
    private final List<TabItem> entries = new ArrayList<>();

    protected ConfigTabImpl(ConfigImpl config, String name, Text title) {
        this.config = config;
        this.name = name;
        this.title = title;
    }

    public List<TabItem> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public Text getTitle() {
        return this.title;
    }

    @Override
    public Void createHeadline(String name) {
        return createHeadline(Text.translatable(Identifier.of(config.getNamespace(), name).toTranslationKey(NAMESPACE + ".headline")).styled(style -> style.withUnderline(true)));
    }

    @Override
    public Void createHeadline(Text text) {
        this.entries.add(new Headline(text));
        return null;
    }
    
    private Identifier getPropertyId(String name) {
        return Identifier.of(this.config.getNamespace(), name);
    }

    @Override
    public StringPropertyBuilder createStringProperty(String name) {
        return new StringPropertyBuilderImpl(this, getPropertyId(name));
    }
    
    @Override
    public IntPropertyBuilderImpl createIntegerProperty(String name) {
        return new IntPropertyBuilderImpl(this, getPropertyId(name));
    }
    
    @Override
    public DoublePropertyBuilderImpl createDoubleProperty(String name) {
        return new DoublePropertyBuilderImpl(this, getPropertyId(name));
    }
    
    @Override
    public BooleanPropertyBuilder createBooleanProperty(String name) {
        return new BooleanPropertyBuilderImpl(this, getPropertyId(name));
    }
    
    @Override
    public <T extends Enum<T>> EnumPropertyBuilderImpl<T> createEnumProperty(String name, Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        if(enumConstants == null) throw new IllegalArgumentException(enumClass.getSimpleName() + " is not an enum");
        return new EnumPropertyBuilderImpl<>(this, getPropertyId(name), Arrays.stream(enumConstants).toList());
    }
    @Override
    public <T> EnumPropertyBuilder<T> createEnumProperty(String name, List<T> values) {
        return new EnumPropertyBuilderImpl<>(this, getPropertyId(name), values);
    }

    @Override
    public void addProperty(PropertyImpl<?> property) {
        this.config.addProperty(property);
    }

    @Override
    public void addWidget(Widget<?> widget) {
        this.entries.add(widget);
    }


    public interface TabItem {
        ConfigWidget createWidget(ConfigWidgetList parent);
    }
}
