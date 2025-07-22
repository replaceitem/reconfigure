package net.replaceitem.reconfigure.api;

import net.minecraft.client.gui.screen.Screen;
import net.replaceitem.reconfigure.config.ConfigBuilderImpl;
import net.replaceitem.reconfigure.screen.ConfigScreen;
import org.jetbrains.annotations.Nullable;

public interface Config {
    /**
     * This is the entrypoint for starting to build a config and all its tabs, properties and widgets.
     * @param namespace The namespace to identify the config. This should be your mod id.
     * @return The config builder.
     */
    static ConfigBuilder builder(String namespace) {
        return new ConfigBuilderImpl(namespace);
    }

    /**
     * Creates a new tab for the config.
     * Use this if you want to split your config into multiple tabs.
     * @param name The name of the tab
     * @return A new config tab builder
     */
    ConfigTabBuilder createTab(String name);

    /**
     * Creates the default tab for the config.
     * Use this if you don't want to use multiple tabs for your config.
     * This will create the single default tab to which you can add all your properties.
     * @return The default config tab builder
     */
    ConfigTabBuilder createDefaultTab();

    /**
     * Creates a screen for the config.
     * Use this with {@link net.minecraft.client.MinecraftClient#setScreen(Screen)} to show the config screen.
     * @param parent The parent screen to return to when closing the config screen
     * @return The config screen
     */
    @Deprecated
    ConfigScreen createScreen(@Nullable Screen parent);

    /**
     * Saves the config to a file using the {@link net.replaceitem.reconfigure.config.serialization.Serializer}
     * defined in the config.
     */
    void save();
    
    /**
     * Loads the config from a file using the {@link net.replaceitem.reconfigure.config.serialization.Serializer}
     * defined in the config.
     */
    void load();

    /**
     * @return Whether any property has been changed since the config was last saved.
     */
    boolean isDirty();

    /**
     * Saves the config only if any property has been changed since last saving.
     */
    void saveIfDirty();
}
