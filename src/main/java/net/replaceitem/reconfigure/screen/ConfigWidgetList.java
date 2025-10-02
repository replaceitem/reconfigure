package net.replaceitem.reconfigure.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.widget.PositioningEntryWidget;
import net.replaceitem.reconfigure.screen.widget.VariableHeightElementListWidget;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.Optional;

public class ConfigWidgetList extends VariableHeightElementListWidget<ConfigWidget> {
    public ConfigWidgetList(ConfigTabImpl tab, MinecraftClient minecraftClient, int width, int height, int y) {
        super(minecraftClient, width, height, y);
        this.replaceEntries(
                tab.getEntries().stream()
                        .map(tabItem -> tabItem.createWidget(this))
                        .flatMap(Optional::stream)
                        .toList()
        );
        this.children().forEach(PositioningEntryWidget::refreshPosition);
    }

    public TextRenderer getTextRenderer() {
        return this.client.textRenderer;
    }
}
