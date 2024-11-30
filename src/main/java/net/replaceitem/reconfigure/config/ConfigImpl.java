package net.replaceitem.reconfigure.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigTabBuilder;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.widget.ConfigTabBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.ConfigScreen;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ConfigImpl implements Config {
    private final String namespace;
    private final Text title;
    private final List<ConfigTabImpl> tabs = new ArrayList<>();

    protected Map<Identifier, PropertyImpl<?>> properties = new HashMap<>();

    protected ConfigImpl(String namespace, Text title) {
        this.namespace = namespace;
        this.title = title;
    }
    
    @Override
    public ConfigTabBuilder createTab(String name) {
        return new ConfigTabBuilderImpl(this, name);
    }

    public String getNamespace() {
        return namespace;
    }

    public Text getTitle() {
        return title;
    }

    public List<ConfigTabImpl> getTabs() {
        return Collections.unmodifiableList(tabs);
    }
    
    public Map<Identifier, PropertyImpl<?>> getProperties() {
        return properties;
    }

    public void addTab(ConfigTabImpl configTab) {
        this.tabs.add(configTab);
    }
    
    @Deprecated
    @Override
    public ConfigScreen createScreen(@Nullable Screen parent) {
        return new ConfigScreen(this, parent);
    }

    public void addProperty(PropertyImpl<?> property) {
        if(this.properties.containsKey(property.getId())) {
            throw new RuntimeException("Config " + namespace + " already contains a property with id " + property.getId());
        }
        this.properties.put(property.getId(), property);
    }
}
