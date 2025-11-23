package net.replaceitem.reconfigure.config.widget;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.replaceitem.reconfigure.api.ConfigTabBuilder;
import net.replaceitem.reconfigure.config.ConfigImpl;
import org.jetbrains.annotations.Nullable;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigTabBuilderImpl implements ConfigTabBuilder {
    private final ConfigImpl config;
    private final String name;
    @Nullable private Component title;

    public ConfigTabBuilderImpl(ConfigImpl config, String name) {
        this.config = config;
        this.name = name;
    }
    
    
    @Override
    public ConfigTabBuilder title(Component title) {
        this.title = title;
        return this;
    }

    @Override
    public ConfigTabImpl build() {
        if (title == null)
            this.title = Component.translatable(ResourceLocation.fromNamespaceAndPath(config.getNamespace(), name).toLanguageKey(NAMESPACE + ".tab"));
        ConfigTabImpl configTab = new ConfigTabImpl(config, name, title);
        this.config.addTab(configTab);
        return configTab;
    }
}
