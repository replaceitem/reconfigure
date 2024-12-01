package net.replaceitem.reconfigure.config.widget;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.ConfigTabBuilder;
import net.replaceitem.reconfigure.config.ConfigImpl;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigTabBuilderImpl implements ConfigTabBuilder {
    private final ConfigImpl config;
    private final String name;
    private Text title;

    public ConfigTabBuilderImpl(ConfigImpl config, String name) {
        this.config = config;
        this.name = name;
    }
    
    
    @Override
    public ConfigTabBuilder title(Text title) {
        this.title = title;
        return this;
    }

    @Override
    public ConfigTabImpl build() {
        if (title == null)
            this.title = Text.translatable(Identifier.of(config.getNamespace(), name).toTranslationKey(NAMESPACE + ".tab"));
        ConfigTabImpl configTab = new ConfigTabImpl(config, name, title);
        this.config.addTab(configTab);
        return configTab;
    }
}
