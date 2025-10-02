package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.PositioningEntryWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ConfigWidget extends PositioningEntryWidget<ConfigWidget> {
    
    protected final List<ClickableWidget> children = new ArrayList<>();
    protected final ConfigWidgetList parent;

    public ConfigWidget(ConfigWidgetList parent, int contentHeight) {
        super();
        this.setHeight(contentHeight + 4);
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

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        context.fill(this.getContentX(), this.getContentY(), getContentRightEnd(), getContentBottomEnd(), ColorHelper.withAlpha(60, Colors.BLACK));
        for (ClickableWidget child : children) {
            child.render(context, mouseX, mouseY, deltaTicks);
        }
    }
    
    public void onSave() {
        
    }
}
