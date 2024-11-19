package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public class CheckboxConfigWidget extends SimpleConfigWidget<CheckboxWidget, Boolean> {
    public CheckboxConfigWidget(ConfigWidgetList listWidget, Property<Boolean> property, Text displayName) {
        super(listWidget, property, displayName);
        setWidget(CheckboxWidget.builder(Text.empty(), this.parent.getTextRenderer()).build());
    }
    
    protected int getWidgetHeight() {
        return CheckboxWidget.getCheckboxSize(this.parent.getTextRenderer());
    }

    @Override
    protected void refreshPosition() {
        this.widget.setPosition(x + width - getWidgetHeight() - PADDING, y + (height-getWidgetHeight())/2);
    }

    @Override
    protected Boolean getSaveValue() {
        return this.widget.isChecked();
    }
}
