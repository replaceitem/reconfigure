package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.serialization.Serializer;

public interface ConfigBuilder {
    ConfigBuilder title(Text title);

    ConfigBuilder serializer(Serializer<?, ?> serializer);

    Config build();
}
