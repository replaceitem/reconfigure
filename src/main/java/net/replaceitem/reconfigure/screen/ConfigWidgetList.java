package net.replaceitem.reconfigure.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.widget.PositioningSelectionListEntry;
import net.replaceitem.reconfigure.screen.widget.VariableHeightSelectionList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.Optional;

public class ConfigWidgetList extends VariableHeightSelectionList<ConfigWidget> {
    public ConfigWidgetList(ConfigTabImpl tab, Minecraft minecraftClient, int width, int height, int y) {
        super(minecraftClient, width, height, y);
        this.replaceEntries(
                tab.getEntries().stream()
                        .map(tabItem -> tabItem.createWidget(this))
                        .flatMap(Optional::stream)
                        .toList()
        );
        this.children().forEach(PositioningSelectionListEntry::refreshPosition);
    }

    public Font getTextRenderer() {
        return this.minecraft.font;
    }
}
