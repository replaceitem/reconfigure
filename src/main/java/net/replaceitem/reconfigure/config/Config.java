package net.replaceitem.reconfigure.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.screen.ConfigScreen;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config {
    private final String namespace;
    private final Text title;
    private final List<ConfigTab> tabs = new ArrayList<>();

    protected Config(String namespace, Text title) {
        this.namespace = namespace;
        this.title = title;
    }

    public static ConfigBuilder builder(String namespace) {
        return new ConfigBuilder(namespace);
    }
    
    public ConfigTabBuilder createTab(String name) {
        return new ConfigTabBuilder(this, name);
    }

    public String getNamespace() {
        return namespace;
    }

    public Text getTitle() {
        return title;
    }

    public List<ConfigTab> getTabs() {
        return Collections.unmodifiableList(tabs);
    }

    void addTab(ConfigTab configTab) {
        this.tabs.add(configTab);
    }
    
    @Deprecated
    public ConfigScreen createScreen(@Nullable Screen parent) {
        return new ConfigScreen(this, parent);
    }

}
