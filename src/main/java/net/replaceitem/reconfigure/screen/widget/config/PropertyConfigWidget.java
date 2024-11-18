package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

public abstract class PropertyConfigWidget<P> extends ConfigWidget {
    public static final int PADDING = 3;
    public static final int NAME_HEIGHT = 20;
    public static final int HEIGHT = NAME_HEIGHT + 2*PADDING;
    
    protected final Property<P> property;
    private final Text displayName;

    public PropertyConfigWidget(ConfigWidgetList listWidget, int height, Property<P> property, Text displayName) {
        super(listWidget, height);
        this.property = property;
        this.displayName = displayName;
    }

    @Override
    public void renderWidgets(DrawContext context, int index, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        this.renderName(context);
    }

    protected void renderName(DrawContext context) {
        int textPadding = HEIGHT / 2 - this.parent.getTextRenderer().fontHeight / 2;
        int textY = y + textPadding;
        context.drawTextWithShadow(this.parent.getTextRenderer(), displayName, x + textPadding, textY, Colors.WHITE);
    }
}
