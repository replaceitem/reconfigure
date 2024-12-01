package net.replaceitem.reconfigure.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.Reconfigure;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigTabBuilder;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.serialization.Serializer;
import net.replaceitem.reconfigure.config.widget.ConfigTabBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.ConfigScreen;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ConfigImpl implements Config {
    private final String namespace;
    private final Text title;
    private final List<ConfigTabImpl> tabs = new ArrayList<>();
    private final Serializer serializer;

    protected final Map<Identifier, PropertyImpl<?>> properties = new LinkedHashMap<>();

    protected ConfigImpl(String namespace, Text title, Serializer serializer) {
        this.namespace = namespace;
        this.title = title;
        this.serializer = serializer;
    }
    
    @Override
    public ConfigTabBuilder createTab(String name) {
        return new ConfigTabBuilderImpl(this, name);
    }

    public String getNamespace() {
        return namespace;
    }

    public Text getTitle() {
        return title;
    }

    public List<ConfigTabImpl> getTabs() {
        return Collections.unmodifiableList(tabs);
    }
    
    public Map<Identifier, PropertyImpl<?>> getProperties() {
        return properties;
    }

    public void addTab(ConfigTabImpl configTab) {
        this.tabs.add(configTab);
    }

    public void addProperty(PropertyImpl<?> property) {
        if(this.properties.containsKey(property.getId())) {
            throw new RuntimeException("Config " + namespace + " already contains a property with id " + property.getId());
        }
        this.properties.put(property.getId(), property);
    }

    @Deprecated
    @Override
    public ConfigScreen createScreen(@Nullable Screen parent) {
        return new ConfigScreen(this, parent);
    }
    
    private File getConfigFile() throws IOException {
        if(this.serializer == null) return null;
        Path configDir = FabricLoader.getInstance().getConfigDir().normalize();
        Files.createDirectories(configDir);
        return configDir.resolve(this.namespace + "." + this.serializer.getFileExtension()).normalize().toFile();
    }
    
    @Override
    public void save() {
        try {
            File configFile = getConfigFile();
            if(configFile == null) {
                Reconfigure.LOGGER.warn("Not saving config {} because no serializer is defined", this.namespace);
                return;
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(configFile)) {
                this.serializer.write(this, fileOutputStream);
            }
        } catch (IOException e) {
            Reconfigure.LOGGER.error("Could save config", e);
        }
    }
    
    @Override
    public void load() {
        try {
            File configFile = getConfigFile();
            if(configFile == null) {
                Reconfigure.LOGGER.warn("Not loading config {} because no serializer is defined", this.namespace);
                return;
            }
            if (configFile.exists()) {
                try(FileInputStream fileInputStream = new FileInputStream(configFile)) {
                    this.serializer.read(this, fileInputStream);
                }
            } else {
                this.save();
                Reconfigure.LOGGER.info("Created {}", configFile.getName());
            }
        } catch (IOException e) {
            Reconfigure.LOGGER.error("Could load config", e);
        }
    }
}
