package net.replaceitem.reconfigure.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;

import java.util.function.Consumer;

public class ConfigScreenTab implements Tab {
    private final ConfigWidgetList list;
    private final ConfigTabImpl tab;

    public ConfigScreenTab(ConfigTabImpl tab, Minecraft client) {
        this.tab = tab;
        this.list = new ConfigWidgetList(tab, client, 0, 0, 0) {
            @Override
            public int getRowWidth() {
                return Math.min(this.width - 40, 400);
            }
        };
    }

    @Override
    public Component getTabTitle() {
        return this.tab.getTitle();
    }

    @Override
    public Component getTabExtraNarration() {
        return this.tab.getTitle();
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(list);
    }

    @Override
    public void doLayout(ScreenRectangle tabArea) {
        this.list.updateSizeAndPosition(tabArea.width(), tabArea.height(), tabArea.left(), tabArea.top());
    }

    public boolean allValid() {
        return this.list.allValid();
    }

    public void onSave() {
        this.list.children().forEach(ConfigWidget::onSave);
    }

    public void addWidgetChangedListener(Runnable runnable) {
        this.list.addWidgetChangedListener(runnable);
    }
    public void removeWidgetChangedListener(Runnable runnable) {
        this.list.removeWidgetChangedListener(runnable);
    }
}
