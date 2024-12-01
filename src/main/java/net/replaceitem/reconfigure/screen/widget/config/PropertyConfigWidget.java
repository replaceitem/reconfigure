package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.TextWidget;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public abstract class PropertyConfigWidget<P> extends ConfigWidget {
    public static final int PADDING = 3;
    public static final int NAME_HEIGHT = 20;
    public static final int HEIGHT = NAME_HEIGHT + 2*PADDING;
    
    protected final int textPadding;

    protected final PropertyImpl<P> property;
    protected final TextWidget nameWidget;

    public PropertyConfigWidget(ConfigWidgetList listWidget, int height, PropertyImpl<P> property, BaseSettings baseSettings) {
        super(listWidget, height);
        this.property = property;
        this.nameWidget = new TextWidget(baseSettings.displayName(), this.parent.getTextRenderer());
        this.nameWidget.setTooltip(baseSettings.tooltip());
        this.nameWidget.alignLeft();
        this.children.add(nameWidget);
        this.textPadding = HEIGHT / 2 - this.parent.getTextRenderer().fontHeight / 2;
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        this.positionName();
    }
    
    protected void positionName() {
        int maxNameWidth = this.width / 2 - textPadding;
        this.nameWidget.setWidth(Math.min(this.parent.getTextRenderer().getWidth(this.nameWidget.getMessage().asOrderedText()), maxNameWidth));
        this.nameWidget.setPosition(x + textPadding, y + textPadding);
    }

    @Override
    public void onSave() {
        this.property.set(this.getSaveValue());
    }

    protected abstract P getSaveValue();
}
