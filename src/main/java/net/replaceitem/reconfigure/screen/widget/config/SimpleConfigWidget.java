package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public abstract class SimpleConfigWidget<W extends ClickableWidget, P> extends PropertyConfigWidget<P> {
    protected W widget;

    public SimpleConfigWidget(ConfigWidgetList listWidget, int height, PropertyImpl<P> property, BaseSettings baseSettings) {
        super(listWidget, height, property, baseSettings);
    }

    public SimpleConfigWidget(ConfigWidgetList listWidget, PropertyImpl<P> property, BaseSettings baseSettings) {
        this(listWidget, HEIGHT, property, baseSettings);
    }

    protected void setWidget(W widget) {
        if(this.widget != null) throw new RuntimeException("Widget already assigned");
        this.widget = widget;
        this.children.add(widget);
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        ScreenRect widgetPos = getWidgetPos();
        this.widget.setDimensionsAndPosition(widgetPos.width(), widgetPos.height(), widgetPos.getLeft(), widgetPos.getTop());
    }
    
    protected ScreenRect getWidgetPos() {
        int maxNameWidth = this.width / 2 - textPadding;
        return new ScreenRect(x + textPadding + maxNameWidth, y + PADDING, width - maxNameWidth - PADDING - textPadding, NAME_HEIGHT);
    }
}
