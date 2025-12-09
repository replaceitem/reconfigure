package net.replaceitem.reconfigure.config;

import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigBuilder;
import net.replaceitem.reconfigure.config.serialization.Serializer;
import org.jspecify.annotations.Nullable;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigBuilderImpl implements ConfigBuilder {
    private final String namespace;
    @Nullable private Component title;
    @Nullable private Serializer serializer;

    public ConfigBuilderImpl(String namespace) {
        this.namespace = namespace;
    }
    
    @Override
    public ConfigBuilderImpl title(Component title) {
        this.title = title;
        return this;
    }
    
    @Override
    public ConfigBuilderImpl serializer(Serializer<?, ?> serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public Config build() {
        if (title == null)
            this.title = Component.translatable(NAMESPACE + ".title." + namespace);
        return new ConfigImpl(namespace, title, serializer);
    }
}
