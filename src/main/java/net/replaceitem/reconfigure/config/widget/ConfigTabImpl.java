package net.replaceitem.reconfigure.config.widget;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.replaceitem.reconfigure.api.ConfigTab;
import net.replaceitem.reconfigure.api.property.BooleanPropertyBuilder;
import net.replaceitem.reconfigure.api.property.EnumPropertyBuilder;
import net.replaceitem.reconfigure.api.property.StringPropertyBuilder;
import net.replaceitem.reconfigure.config.ConfigImpl;
import net.replaceitem.reconfigure.config.PropertyHolder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.*;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.*;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigTabImpl implements ConfigTab, PropertyBuildContext {

    final ConfigImpl config;
    private final String name;
    private final Component title;
    private final List<TabItem> entries = new ArrayList<>();

    protected ConfigTabImpl(ConfigImpl config, String name, Component title) {
        this.config = config;
        this.name = name;
        this.title = title;
    }

    public List<TabItem> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public Component getTitle() {
        return this.title;
    }

    @Override
    public Void createHeadline(String name) {
        return createHeadline(Component.translatable(ResourceLocation.fromNamespaceAndPath(config.getNamespace(), name).toLanguageKey(NAMESPACE + ".headline")).withStyle(style -> style.withUnderlined(true)));
    }

    @Override
    public Void createHeadline(Component text) {
        this.entries.add(new Headline(text));
        return null;
    }
    
    private ResourceLocation getPropertyId(String name) {
        return ResourceLocation.fromNamespaceAndPath(this.config.getNamespace(), name);
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
    public ListPropertyBuilderImpl createListProperty(String name) {
        return new ListPropertyBuilderImpl(this, getPropertyId(name), new ArrayList<>());
    }

    @Override
    public void addProperty(PropertyHolder<?> holder) {
        this.entries.add(holder);
        this.config.addProperty(holder);
    }

    public interface TabItem {
        Optional<ConfigWidget> createWidget(ConfigWidgetList parent);
    }
}
