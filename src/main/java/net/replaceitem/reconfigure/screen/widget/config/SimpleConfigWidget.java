package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public abstract class SimpleConfigWidget<W extends ClickableWidget, P> extends PropertyConfigWidget<P> {
    protected W widget;

    public SimpleConfigWidget(ConfigWidgetList listWidget, int height, Property<P> property, Text displayName) {
        super(listWidget, height, property, displayName);
    }

    public SimpleConfigWidget(ConfigWidgetList listWidget, Property<P> property, Text displayName) {
        this(listWidget, HEIGHT, property, displayName);
    }

    protected void setWidget(W widget) {
        if(this.widget != null) throw new RuntimeException("Widget already assigned");
        this.widget = widget;
        this.children.add(widget);
    }

    @Override
    protected void refreshPosition() {
        this.widget.setDimensionsAndPosition(width-getNameWidth() - PADDING, NAME_HEIGHT, x + getNameWidth(), y + PADDING);
    }
}
