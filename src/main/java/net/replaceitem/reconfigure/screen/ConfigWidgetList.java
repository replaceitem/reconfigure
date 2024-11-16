package net.replaceitem.reconfigure.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.replaceitem.reconfigure.config.ConfigTab;
import net.replaceitem.reconfigure.screen.widget.*;

public class ConfigWidgetList extends ElementListWidget<ConfigWidget> {
    public ConfigWidgetList(ConfigTab tab, MinecraftClient minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
        this.replaceEntries(tab.getEntries().stream().map(tabItem -> tabItem.createWidget(this)).toList());
    }
    
    public TextRenderer getTextRenderer() {
        return this.client.textRenderer;
    }
}
