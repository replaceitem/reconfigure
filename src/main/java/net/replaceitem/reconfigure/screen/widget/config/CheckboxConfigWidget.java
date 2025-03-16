package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public class CheckboxConfigWidget extends SimpleConfigWidget<CheckboxWidget, Boolean> {
    public CheckboxConfigWidget(ConfigWidgetList listWidget, PropertyImpl<Boolean> property, BaseSettings baseSettings) {
        super(listWidget, property, baseSettings);
        setWidget(CheckboxWidget.builder(Text.empty(), this.parent.getTextRenderer()).callback((checkbox, checked) -> this.onValueChanged()).checked(property.get()).build());
    }

    @Override
    protected ScreenRect getWidgetPos() {
        int widgetSize = CheckboxWidget.getCheckboxSize(this.parent.getTextRenderer());
        return new ScreenRect(getRight() - PADDING - this.resetButtonWidget.getWidth() - PADDING - widgetSize, y + (height-widgetSize) / 2, widgetSize, widgetSize);
    }

    @Override
    protected Boolean getSaveValue() {
        return this.widget.isChecked();
    }

    @Override
    protected void loadValue(Boolean value) {
        if(value != this.widget.isChecked()) this.widget.onPress();
    }
}
