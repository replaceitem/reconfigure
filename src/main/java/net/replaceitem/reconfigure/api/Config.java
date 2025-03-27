package net.replaceitem.reconfigure.api;

import net.minecraft.client.gui.screen.Screen;
import net.replaceitem.reconfigure.config.ConfigBuilderImpl;
import net.replaceitem.reconfigure.screen.ConfigScreen;
import org.jetbrains.annotations.Nullable;

public interface Config {
    static ConfigBuilder builder(String namespace) {
        return new ConfigBuilderImpl(namespace);
    }

    ConfigTabBuilder createTab(String name);
    ConfigTabBuilder createDefaultTab();

    @Deprecated
    ConfigScreen createScreen(@Nullable Screen parent);

    void save();
    void load();
}
