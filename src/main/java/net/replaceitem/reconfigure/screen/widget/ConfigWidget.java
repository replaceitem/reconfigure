package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ConfigWidget extends PositioningEntryWidget {
    
    protected final List<ClickableWidget> children = new ArrayList<>();
    protected final ConfigWidgetList parent;

    public ConfigWidget(ConfigWidgetList parent) {
        this.parent = parent;
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return this.children;
    }

    @Override
    public List<? extends Element> children() {
        return this.children;
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        children.forEach(consumer);
    }

    protected int getNameWidth() {
        return width / 2;
    }

    @Override
    public final void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
        context.fill(x, y, x+entryWidth, y+entryHeight, ColorHelper.withAlpha(60, Colors.BLACK));
        this.renderWidgets(context, index, mouseX, mouseY, hovered, tickDelta);
        for (ClickableWidget child : children) {
            child.render(context, mouseX, mouseY, tickDelta);
        }
    }
    
    public void renderWidgets(DrawContext context, int index, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        
    }
    
    protected abstract void onSave();
}
