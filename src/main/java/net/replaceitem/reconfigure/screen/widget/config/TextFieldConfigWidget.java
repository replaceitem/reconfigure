package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicTextFieldWidget;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TextFieldConfigWidget extends SimpleConfigWidget<TextFieldWidget, String> {
    public TextFieldConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<String> property,
            BaseSettings baseSettings,
            @Nullable Text placeholder
    ) {
        super(listWidget, property, baseSettings);
        ScreenRect widgetPos = getWidgetPos();
        setWidget(new DynamicTextFieldWidget(listWidget.getTextRenderer(), widgetPos.getLeft(), widgetPos.getTop(), widgetPos.width(), widgetPos.height(), Text.empty()));
        this.widget().setMaxLength(10000);
        this.widget().setChangedListener(s -> this.onValueChanged());
        this.widget().setText(property.get());
        if (placeholder != null) this.widget().setPlaceholder(placeholder);
    }

    @Override
    protected String getSaveValue() {
        return this.widget().getText();
    }

    @Override
    protected void loadValue(String value) {
        this.widget().setText(value);
    }
}
