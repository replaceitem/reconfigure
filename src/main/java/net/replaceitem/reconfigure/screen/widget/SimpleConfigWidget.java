package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public abstract class SimpleConfigWidget<W extends ClickableWidget, P> extends PropertyConfigWidget<P> {
    public static final int PADDING_RIGHT = 3;
    protected W widget;
    
    public SimpleConfigWidget(ConfigWidgetList listWidget, Property<P> property, Text displayName) {
        super(listWidget, property, displayName);
    }
    
    protected void setWidget(W widget) {
        if(this.widget != null) throw new RuntimeException("Widget already assigned");
        this.widget = widget;
        this.children.add(widget);
    }

    protected int getWidgetHeight() {
        return 20;
    }

    @Override
    protected void refreshPosition() {
        this.widget.setDimensionsAndPosition(width-getNameWidth()-PADDING_RIGHT, getWidgetHeight(), x + getNameWidth(), y + (height-getWidgetHeight())/2);
    }
}
