package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicEditBoxWidget;

public class EditBoxConfigWidget extends SimpleConfigWidget<EditBoxWidget, String> {
    public static final int BOX_HEIGHT = 65;
    public static final int HEIGHT = BOX_HEIGHT + SimpleConfigWidget.DEFAULT_HEIGHT;

    public EditBoxConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<String> property,
            BaseSettings baseSettings,
            Text placeholder
    ) {
        super(listWidget, HEIGHT, property, baseSettings);
        ScreenRect widgetPos = getWidgetPos();
        setWidget(new DynamicEditBoxWidget(listWidget.getTextRenderer(), widgetPos.getLeft(), widgetPos.getTop(), widgetPos.width(), widgetPos.height(), placeholder, Text.empty()));
        this.widget.setChangeListener(s -> this.onValueChanged());
        this.loadValue(property.get());
    }

    @Override
    protected void positionName() {
        positionNameFullWidth();
    }

    @Override
    protected ScreenRect getWidgetPos() {
        return new ScreenRect(x + PADDING, y + PADDING + NAME_HEIGHT + PADDING, width - 2 * PADDING, BOX_HEIGHT);
    }

    @Override
    protected String getSaveValue() {
        return this.widget.getText();
    }

    @Override
    protected void loadValue(String value) {
        this.widget.setText(value);
    }
}
