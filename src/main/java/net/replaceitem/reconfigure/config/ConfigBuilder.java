package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class ConfigBuilder {
    private final String namespace;
    private Text title;

    ConfigBuilder(String namespace) {
        this.namespace = namespace;
    }

    public ConfigBuilder title(Text title) {
        this.title = title;
        return this;
    }

    public Config build() {
        if (title == null)
            this.title = Text.translatable(NAMESPACE + ".title." + namespace);
        return new Config(namespace, title);
    }
}
