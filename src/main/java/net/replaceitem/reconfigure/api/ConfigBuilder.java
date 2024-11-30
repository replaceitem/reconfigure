package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;

public interface ConfigBuilder {
    ConfigBuilder title(Text title);

    Config build();
}
