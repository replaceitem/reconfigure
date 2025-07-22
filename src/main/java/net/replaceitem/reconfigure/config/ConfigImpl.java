package net.replaceitem.reconfigure.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.Reconfigure;
import net.replaceitem.reconfigure.api.Config;
import net.replaceitem.reconfigure.api.ConfigTabBuilder;
import net.replaceitem.reconfigure.config.serialization.SerializationTarget;
import net.replaceitem.reconfigure.config.serialization.Serializer;
import net.replaceitem.reconfigure.config.widget.ConfigTabBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.ConfigScreen;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

public class ConfigImpl implements Config, SerializationTarget {
    private final String namespace;
    private final Text title;
    private final List<ConfigTabImpl> tabs = new ArrayList<>();
    private boolean hasSingleDefaultTab = false;
    @Nullable private final Serializer<?,?> serializer;
    private boolean dirty = false;
    @Nullable
    private Timer saveTimer;

    protected final Map<Identifier, PropertyHolder<?>> properties = new LinkedHashMap<>();

    protected ConfigImpl(String namespace, Text title, @Nullable Serializer<?,?> serializer) {
        this.namespace = namespace;
        this.title = title;
        this.serializer = serializer;
    }
    
    @Override
    public ConfigTabBuilder createTab(String name) {
        return new ConfigTabBuilderImpl(this, name);
    }

    @Override
    public ConfigTabBuilder createDefaultTab() {
        this.hasSingleDefaultTab = true;
        return new ConfigTabBuilderImpl(this, "default");
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    public Text getTitle() {
        return title;
    }

    public List<ConfigTabImpl> getTabs() {
        return Collections.unmodifiableList(tabs);
    }

    public boolean hasSingleDefaultTab() {
        return hasSingleDefaultTab;
    }

    @Override
    public @Nullable PropertyHolder<?> getProperty(Identifier key) {
        return this.properties.get(key);
    }

    @Override
    public @Nullable PropertyHolder<?> getProperty(String key) {
        return this.getProperty(Identifier.of(this.namespace, key));
    }

    @Override
    public Collection<PropertyHolder<?>> getProperties() {
        return properties.values();
    }

    public void addTab(ConfigTabImpl configTab) {
        if(this.hasSingleDefaultTab && !this.tabs.isEmpty()) {
            throw new RuntimeException("Tried adding more than one tab to a config with a default tab. If you use config.createDefaultTab() your config can only have one tab.");
        }
        this.tabs.add(configTab);
    }

    public void addProperty(PropertyHolder<?> property) {
        if(this.properties.containsKey(property.getId())) {
            throw new RuntimeException("Config " + namespace + " already contains a property with id " + property.getId());
        }
        property.getProperty().addListener(o -> this.markDirty());
        this.properties.put(property.getId(), property);
    }

    @Deprecated
    @Override
    public ConfigScreen createScreen(@Nullable Screen parent) {
        return new ConfigScreen(this, parent);
    }
    
    private static File getConfigFile(String namespace, Serializer<?, ?> serializer) throws IOException {
        Path configDir = FabricLoader.getInstance().getConfigDir().normalize();
        Files.createDirectories(configDir);
        return configDir.resolve(namespace + "." + serializer.getFileExtension()).normalize().toFile();
    }
    
    @Override
    public void save() {
        try {
            if(this.serializer == null) {
                Reconfigure.LOGGER.warn("Not saving config {} because no serializer is defined", this.namespace);
                return;
            }
            File configFile = getConfigFile(this.namespace, this.serializer);
            try (FileOutputStream fileOutputStream = new FileOutputStream(configFile)) {
                this.serializer.serialize(this, fileOutputStream);
            }
            this.dirty = false;
        } catch (IOException e) {
            Reconfigure.LOGGER.error("Could save config", e);
        }
    }
    
    @Override
    public void load() {
        try {
            if(this.serializer == null) {
                Reconfigure.LOGGER.warn("Not loading config {} because no serializer is defined", this.namespace);
                return;
            }
            File configFile = getConfigFile(this.namespace, this.serializer);
            if (configFile.exists()) {
                try(FileInputStream fileInputStream = new FileInputStream(configFile)) {
                    this.serializer.deserialize(this, fileInputStream);
                }
            } else {
                this.save();
                Reconfigure.LOGGER.info("Created {}", configFile.getName());
            }
        } catch (IOException e) {
            Reconfigure.LOGGER.error("Could load config", e);
        }
    }

    protected void markDirty() {
        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void saveIfDirty() {
        if(this.isDirty()) {
            this.save();
        }
    }

    @Override
    public void scheduleSave(Duration duration) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (saveTimer != null) {
            saveTimer.cancel();
        }
        saveTimer = new Timer();
        saveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.send(ConfigImpl.this::saveIfDirty);
            }
        }, duration.toMillis());
    }
}
