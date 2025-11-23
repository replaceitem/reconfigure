package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicEditBox;
import org.jetbrains.annotations.Nullable;

public class TextFieldConfigWidget extends SimpleConfigWidget<EditBox, String> {
    public TextFieldConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<String> property,
            BaseSettings baseSettings,
            @Nullable Component placeholder
    ) {
        super(listWidget, property, baseSettings);
        ScreenRectangle widgetPos = getWidgetPos();
        setWidget(new DynamicEditBox(listWidget.getTextRenderer(), widgetPos.left(), widgetPos.top(), widgetPos.width(), widgetPos.height(), Component.empty()));
        this.widget().setMaxLength(10000);
        this.widget().setResponder(s -> this.onValueChanged());
        this.widget().setValue(property.get());
        if (placeholder != null) this.widget().setHint(placeholder);
    }

    @Override
    protected String getSaveValue() {
        return this.widget().getValue();
    }

    @Override
    protected void loadValue(String value) {
        this.widget().setValue(value);
    }
}
