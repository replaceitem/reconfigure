package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicEditBoxWidget;

public class EditBoxConfigWidget extends SimpleConfigWidget<EditBoxWidget, String> {
    public static final int BOX_HEIGHT = 65;
    public static final int HEIGHT = BOX_HEIGHT + SimpleConfigWidget.HEIGHT;

    public EditBoxConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<String> property,
            BaseSettings baseSettings,
            String placeholder
    ) {
        super(listWidget, HEIGHT, property, baseSettings);
        setWidget(new DynamicEditBoxWidget(listWidget.getTextRenderer(), 0, 0, 0, 0, Text.of(placeholder), Text.of(property.get())));
    }

    @Override
    protected void positionName() {
        int maxNameWidth = this.width - 2 * this.textPadding;
        this.nameWidget.setWidth(Math.min(this.nameWidget.getWidth(), maxNameWidth));
        this.nameWidget.setPosition(x + textPadding, y + textPadding);
    }

    @Override
    protected void refreshPosition() {
        super.refreshPosition();
        this.widget.setDimensionsAndPosition(width - 2 * PADDING, BOX_HEIGHT, x + PADDING, y + PADDING + NAME_HEIGHT);
    }

    @Override
    protected String getSaveValue() {
        return this.widget.getText();
    }
}
