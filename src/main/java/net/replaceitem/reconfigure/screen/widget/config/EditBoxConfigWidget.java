package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicMultiLineEditBox;

public class EditBoxConfigWidget extends SimpleConfigWidget<MultiLineEditBox, String> {
    public static final int BOX_HEIGHT = 65;
    public static final int HEIGHT = SimpleConfigWidget.DEFAULT_HEIGHT + INNER_PADDING + BOX_HEIGHT;

    public EditBoxConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<String> property,
            BaseSettings baseSettings,
            Component placeholder
    ) {
        super(listWidget, HEIGHT, property, baseSettings);
        ScreenRectangle widgetPos = getWidgetPos();
        setWidget(new DynamicMultiLineEditBox(listWidget.getTextRenderer(), widgetPos.left(), widgetPos.top(), widgetPos.width(), widgetPos.height(), placeholder, Component.empty()));
        this.widget().setValueListener(s -> this.onValueChanged());
        this.loadValue(property.get());
    }

    @Override
    protected void positionName() {
        positionNameFullWidth();
    }

    @Override
    protected ScreenRectangle getWidgetPos() {
        return new ScreenRectangle(this.getContentX() + INNER_PADDING, this.getContentY() + INNER_PADDING + NAME_HEIGHT + INNER_PADDING, this.getContentWidth() - 2 * INNER_PADDING, BOX_HEIGHT);
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
