package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public class CheckboxConfigWidget extends SimpleConfigWidget<Checkbox, Boolean> {
    public CheckboxConfigWidget(ConfigWidgetList listWidget, PropertyImpl<Boolean> property, BaseSettings baseSettings) {
        super(listWidget, property, baseSettings);
        setWidget(Checkbox.builder(Component.empty(), this.parent.getTextRenderer()).onValueChange((_, _) -> this.onValueChanged()).selected(property.get()).build());
    }

    @Override
    protected ScreenRectangle getWidgetPos() {
        int widgetSize = Checkbox.getBoxSize(this.parent.getTextRenderer());
        return new ScreenRectangle(getContentRight() - INNER_PADDING - this.resetButtonWidget.getWidth() - INNER_PADDING - widgetSize, this.getContentY() + (this.getContentHeight()-widgetSize) / 2, widgetSize, widgetSize);
    }

    @Override
    protected Boolean getSaveValue() {
        return this.widget().selected();
    }

    @Override
    protected void loadValue(Boolean value) {
        if(value != this.widget().selected()) this.widget().onPress(null);
    }
}
