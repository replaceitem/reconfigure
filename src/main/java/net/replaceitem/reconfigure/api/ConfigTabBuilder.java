package net.replaceitem.reconfigure.api;

import net.minecraft.network.chat.Component;

public interface ConfigTabBuilder {
    /**
     * Sets the title text of the config tab. If not set, this defaults to a translatable text with key
     * {@code reconfigure.tab.[namespace].[tabname]} and {@code reconfigure.tab.[namespace].default}
     * for the default tab.
     * @param title The title text for the tab
     * @return The builder for chaining
     */
    ConfigTabBuilder title(Component title);

    /**
     * Builds the config tab.
     * @return The built config tab
     */
    ConfigTab build();
}
