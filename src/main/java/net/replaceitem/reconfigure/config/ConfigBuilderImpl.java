package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigBuilder;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigBuilderImpl implements ConfigBuilder {
    private final String namespace;
    private Text title;

    public ConfigBuilderImpl(String namespace) {
        this.namespace = namespace;
    }
    
    @Override
    public ConfigBuilder title(Text title) {
        this.title = title;
        return this;
    }

    @Override
    public Config build() {
        if (title == null)
            this.title = Text.translatable(NAMESPACE + ".title." + namespace);
        return new ConfigImpl(namespace, title);
    }
}
