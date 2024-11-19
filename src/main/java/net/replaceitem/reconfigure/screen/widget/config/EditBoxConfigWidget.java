package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicEditBoxWidget;

public class EditBoxConfigWidget extends SimpleConfigWidget<EditBoxWidget, String> {
    public static final int BOX_HEIGHT = 65;
    public static final int HEIGHT = BOX_HEIGHT + SimpleConfigWidget.HEIGHT;

    public EditBoxConfigWidget(
            ConfigWidgetList listWidget,
            Property<String> property,
            Text displayName,
            String placeholder
    ) {
        super(listWidget, HEIGHT, property, displayName);
        setWidget(new DynamicEditBoxWidget(listWidget.getTextRenderer(), 0, 0, 0, 0, Text.of(placeholder), Text.of(property.get())));
    }

    @Override
    protected int getNameWidth() {
        return width;
    }

    @Override
    protected void refreshPosition() {
        this.widget.setDimensionsAndPosition(width - 2 * PADDING, BOX_HEIGHT, x + PADDING, y + PADDING + NAME_HEIGHT);
    }

    @Override
    protected String getSaveValue() {
        return this.widget.getText();
    }
}
