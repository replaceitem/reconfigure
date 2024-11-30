package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;

public interface ConfigTabBuilder {
    ConfigTabBuilder title(Text title);

    ConfigTab build();
}
