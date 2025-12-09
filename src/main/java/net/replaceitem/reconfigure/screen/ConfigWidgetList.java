package net.replaceitem.reconfigure.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.replaceitem.reconfigure.config.widget.ConfigTabImpl;
import net.replaceitem.reconfigure.screen.widget.PositioningSelectionListEntry;
import net.replaceitem.reconfigure.screen.widget.VariableHeightSelectionList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;
import net.replaceitem.reconfigure.screen.widget.config.PropertyConfigWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigWidgetList extends VariableHeightSelectionList<ConfigWidget> {
    private boolean allValid = true;

    private final List<Runnable> configWidgetChangedListeners = new ArrayList<>(0);

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

    public void onConfigWidgetChanged() {
        this.allValid = this.children().stream().allMatch(child ->
                !(child instanceof PropertyConfigWidget<?> propertyConfigWidget)
                || propertyConfigWidget.getValidationResult().isValid()
        );
        this.configWidgetChangedListeners.forEach(Runnable::run);
    }

    public void addWidgetChangedListener(Runnable runnable) {
        this.configWidgetChangedListeners.add(runnable);
    }
    public void removeWidgetChangedListener(Runnable runnable) {
        this.configWidgetChangedListeners.remove(runnable);
    }

    public boolean allValid() {
        return allValid;
    }
}
