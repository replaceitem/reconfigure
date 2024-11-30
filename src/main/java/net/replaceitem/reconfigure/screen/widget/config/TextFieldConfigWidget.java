package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public class TextFieldConfigWidget extends SimpleConfigWidget<TextFieldWidget, String> {
    public TextFieldConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<String> property,
            BaseSettings baseSettings,
            String placeholder
    ) {
        super(listWidget, property, baseSettings);
        setWidget(new TextFieldWidget(listWidget.getTextRenderer(), 0, 0, Text.literal(property.get())));
        if (placeholder != null) this.widget.setPlaceholder(Text.literal(placeholder));
    }

    @Override
    protected String getSaveValue() {
        return this.widget.getText();
    }
}
