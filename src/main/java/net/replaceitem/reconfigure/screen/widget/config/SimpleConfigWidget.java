package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class SimpleConfigWidget<W extends ClickableWidget, P> extends PropertyConfigWidget<P> {
    @Nullable
    private W widget;

    public SimpleConfigWidget(ConfigWidgetList listWidget, int contentHeight, PropertyImpl<P> property, BaseSettings baseSettings) {
        super(listWidget, contentHeight, property, baseSettings);
    }

    public SimpleConfigWidget(ConfigWidgetList listWidget, PropertyImpl<P> property, BaseSettings baseSettings) {
        this(listWidget, DEFAULT_HEIGHT, property, baseSettings);
    }

    // TODO maybe this can be removed when JEP 447 (Statements before super) is usable in JDK 22+ https://openjdk.org/jeps/447
    protected void setWidget(W widget) {
        if(this.widget != null) throw new RuntimeException("Widget already assigned");
        this.widget = widget;
        this.children.addFirst(widget);
    }

    protected W widget() {
        return Objects.requireNonNull(this.widget, "SimpleConfigWidget.widget should not be null. It should be initialized in the subclass constructor using setWidget()");
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        if(this.widget != null) {
            ScreenRect widgetPos = getWidgetPos();
            this.widget.setDimensionsAndPosition(widgetPos.width(), widgetPos.height(), widgetPos.getLeft(), widgetPos.getTop());
        }
    }
    
    protected ScreenRect getWidgetPos() {
        int maxNameWidth = this.getContentWidth() / 2 - textPadding;
        return new ScreenRect(this.getContentX() + textPadding + maxNameWidth, this.getContentY() + INNER_PADDING, this.getContentWidth() - maxNameWidth - INNER_PADDING - textPadding - this.resetButtonWidget.getWidth(), NAME_HEIGHT);
    }
}
