package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigBuilder;
import net.replaceitem.reconfigure.config.serialization.Serializer;
import org.jetbrains.annotations.Nullable;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigBuilderImpl implements ConfigBuilder {
    private final String namespace;
    @Nullable private Text title;
    @Nullable private Serializer serializer;

    public ConfigBuilderImpl(String namespace) {
        this.namespace = namespace;
    }
    
    @Override
    public ConfigBuilderImpl title(Text title) {
        this.title = title;
        return this;
    }
    
    public ConfigBuilderImpl serializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public Config build() {
        if (title == null)
            this.title = Text.translatable(NAMESPACE + ".title." + namespace);
        return new ConfigImpl(namespace, title, serializer);
    }
}
