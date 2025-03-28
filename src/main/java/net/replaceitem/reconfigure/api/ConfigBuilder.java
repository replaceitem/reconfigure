package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.serialization.Serializer;

public interface ConfigBuilder {
    /**
     * Sets the title text for the config. If not set, the default title is a translatable text with key
     * {@code reconfigure.title.[namespace]}.
     * @param title The title text
     * @return The builder for chaining
     */
    ConfigBuilder title(Text title);

    /**
     * Sets the serializer for this config. The serializer is used for reading and
     * writing the config to a file in different formats.
     * Serializers can be built or fetched from {@link net.replaceitem.reconfigure.api.serializer.Serializers}.
     * @param serializer The serializer
     * @return The builder for chaining
     */
    ConfigBuilder serializer(Serializer<?, ?> serializer);

    /**
     * Builds the config. After this, the returned config can be used to create tabs for it.
     * @return The config
     */
    Config build();
}
