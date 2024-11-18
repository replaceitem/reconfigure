package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.property.*;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigTab {

    final Config config;
    private final String name;
    private final Text title;
    private final List<TabItem> entries = new ArrayList<>();

    protected ConfigTab(Config config, String name, Text title) {
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

    void addEntry(Property<?> property) {
        this.entries.add(property);
    }

    public Void createHeadline(String name) {
        return createHeadline(Text.translatable(Identifier.of(config.getNamespace(), name).toTranslationKey(NAMESPACE + ".headline")).styled(style -> style.withUnderline(true)));
    }

    public Void createHeadline(Text text) {
        this.entries.add(new Headline(text));
        return null;
    }
    
    private Identifier getPropertyId(String name) {
        return Identifier.of(this.config.getNamespace(), name);
    }

    public StringPropertyBuilder createStringProperty(String name) {
        return new StringPropertyBuilder(this::addEntry, getPropertyId(name));
    }
    
    public IntPropertyBuilder createIntegerProperty(String name) {
        return new IntPropertyBuilder(this::addEntry, getPropertyId(name));
    }
    
    public DoublePropertyBuilder createDoubleProperty(String name) {
        return new DoublePropertyBuilder(this::addEntry, getPropertyId(name));
    }
    
    public BooleanPropertyBuilder createBooleanProperty(String name) {
        return new BooleanPropertyBuilder(this::addEntry, getPropertyId(name));
    }
    
    public <T extends Enum<T>> EnumPropertyBuilder<T> createEnumProperty(String name, Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        if(enumConstants == null) throw new IllegalArgumentException(enumClass.getSimpleName() + " is not an enum");
        return new EnumPropertyBuilder<>(this::addEntry, getPropertyId(name), Arrays.stream(enumConstants).toList());
    }
    public <T> EnumPropertyBuilder<T> createEnumProperty(String name, List<T> values) {
        return new EnumPropertyBuilder<>(this::addEntry, getPropertyId(name), values);
    }




    public interface TabItem {
        ConfigWidget createWidget(ConfigWidgetList parent);
    }
}
