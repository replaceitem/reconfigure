package net.replaceitem.reconfigure.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public abstract class PropertyConfigWidget<P> extends ConfigWidget {

    protected final Property<P> property;
    private final Text displayName;

    public PropertyConfigWidget(ConfigWidgetList listWidget, Property<P> property, Text displayName) {
        super(listWidget);
        this.property = property;
        this.displayName = displayName;
    }

    protected void renderName(DrawContext context, int index, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int textPadding = height / 2 - this.parent.getTextRenderer().fontHeight / 2;
        int textY = y + textPadding;
        context.drawTextWithShadow(this.parent.getTextRenderer(), displayName, x + textPadding, textY, Colors.WHITE);
    }

    @Override
    public void renderWidgets(DrawContext context, int index, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        this.renderName(context, index, mouseX, mouseY, hovered, tickDelta);
    }
}
