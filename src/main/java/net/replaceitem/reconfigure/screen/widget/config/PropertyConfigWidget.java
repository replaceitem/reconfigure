package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.replaceitem.reconfigure.api.ValidationResult;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.Objects;

public abstract class PropertyConfigWidget<P> extends ConfigWidget {
    public static final int PADDING = 3;
    public static final int BASIC_WIDGET_SIZE = 20;
    public static final int NAME_HEIGHT = BASIC_WIDGET_SIZE;
    public static final int DEFAULT_HEIGHT = NAME_HEIGHT + 2*PADDING;
    
    protected final int textPadding;

    protected final PropertyImpl<P> property;
    private final BaseSettings baseSettings;

    protected ValidationResult validationResult = ValidationResult.valid();
    
    protected final TextWidget nameWidget;

    public PropertyConfigWidget(ConfigWidgetList listWidget, int height, PropertyImpl<P> property, BaseSettings baseSettings) {
        super(listWidget, height);
        this.property = property;
        this.baseSettings = baseSettings;
        this.nameWidget = new TextWidget(Text.empty(), this.parent.getTextRenderer());
        this.nameWidget.alignLeft();
        this.children.add(nameWidget);
        this.textPadding = DEFAULT_HEIGHT / 2 - this.parent.getTextRenderer().fontHeight / 2;
        this.setNameText(this.property.get());
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        this.positionName();
    }
    
    protected void positionName() {
        int maxNameWidth = this.width / 2 - textPadding;
        this.nameWidget.setWidth(Math.min(this.parent.getTextRenderer().getWidth(this.nameWidget.getMessage().asOrderedText()), maxNameWidth));
        this.nameWidget.setPosition(x + textPadding, y + textPadding);
    }
    
    protected void positionNameFullWidth() {
        int maxNameWidth = this.width - 2 * this.textPadding;
        this.nameWidget.setWidth(Math.min(this.parent.getTextRenderer().getWidth(this.nameWidget.getMessage().asOrderedText()), maxNameWidth));
        this.nameWidget.setPosition(x + textPadding, y + textPadding);
    }

    @Override
    public void onSave() {
        this.property.set(this.getSaveValue());
    }
    
    protected void onValueChanged() {
        this.validate();
    }

    private void validate() {
        P saveValue = this.getSaveValue();
        this.validationResult = this.property.validate(saveValue);
        this.setNameText(saveValue);
    }
    
    private void setNameText(P saveValue) {
        boolean wasChanged = !Objects.equals(saveValue, this.property.get());
        this.nameWidget.setMessage(baseSettings.displayName().copy().styled(style -> {
            style = style.withItalic(wasChanged);
            if(this.validationResult.isInvalid()) style = style.withColor(Formatting.RED);
            return style;
        }));
        MutableText tooltipText = Text.empty();
        if(baseSettings.tooltip() != null) {
            tooltipText.append(baseSettings.tooltip());
        }
        if(baseSettings.tooltip() != null && this.validationResult.isInvalid()) tooltipText.append("\n");
        if(this.validationResult.isInvalid()) {
            tooltipText.append(Text.literal(validationResult.getMessage()).formatted(Formatting.RED));
        }
        this.nameWidget.setTooltip(Tooltip.of(tooltipText));
    }

    protected abstract P getSaveValue();
    
    public int getContentWidth() {
        return this.width - 2*PADDING;
    }
}
