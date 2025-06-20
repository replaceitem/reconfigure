package net.replaceitem.reconfigure.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.function.Consumer;

public class ConfigScreenTab implements Tab {
    private final ConfigWidgetList list;
    private final ConfigTabImpl tab;

    public ConfigScreenTab(ConfigTabImpl tab, MinecraftClient client) {
        this.tab = tab;
        this.list = new ConfigWidgetList(tab, client, 0, 0, 0, 30) {
            @Override
            public int getRowWidth() {
                return Math.min(this.width - 40, 400);
            }
        };
    }

    @Override
    public Text getTitle() {
        return this.tab.getTitle();
    }

    @Override
    public Text getNarratedHint() {
        return this.tab.getTitle();
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        consumer.accept(list);
    }

    @Override
    public void refreshGrid(ScreenRect tabArea) {
        this.list.setDimensionsAndPosition(tabArea.width(), tabArea.height(), tabArea.getLeft(), tabArea.getTop());
    }

    public void onSave() {
        this.list.children().forEach(ConfigWidget::onSave);
    }
}
