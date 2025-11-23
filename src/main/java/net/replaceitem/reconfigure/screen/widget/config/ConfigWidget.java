package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.PositioningSelectionListEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ConfigWidget extends PositioningSelectionListEntry<ConfigWidget> {
    
    protected final List<AbstractWidget> children = new ArrayList<>();
    protected final ConfigWidgetList parent;

    public ConfigWidget(ConfigWidgetList parent, int contentHeight) {
        super();
        this.setHeight(contentHeight + 4);
        this.parent = parent;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Override
    public void visitWidgets(Consumer<AbstractWidget> consumer) {
        children.forEach(consumer);
    }

    @Override
    public void renderContent(GuiGraphics context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        context.fill(this.getContentX(), this.getContentY(), getContentRight(), getContentBottom(), ARGB.color(60, CommonColors.BLACK));
        for (AbstractWidget child : children) {
            child.render(context, mouseX, mouseY, deltaTicks);
        }
    }
    
    public void onSave() {
        
    }
}
