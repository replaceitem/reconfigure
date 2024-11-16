package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigTabBuilder {
    private final Config config;
    private final String name;
    private Text title;

    ConfigTabBuilder(Config config, String name) {
        this.config = config;
        this.name = name;
    }
    
    
    public ConfigTabBuilder title(Text title) {
        this.title = title;
        return this;
    }

    public ConfigTab build() {
        if (title == null)
            this.title = Text.translatable(Identifier.of(config.getNamespace(), name).toTranslationKey(NAMESPACE + ".tab"));
        ConfigTab configTab = new ConfigTab(config, name, title);
        this.config.addTab(configTab);
        return configTab;
    }
}
