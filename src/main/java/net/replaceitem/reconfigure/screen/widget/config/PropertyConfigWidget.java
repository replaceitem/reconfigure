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

public abstract class PropertyConfigWidget<P> extends ConfigWidget {
    public static final int PADDING = 3;
    public static final int NAME_HEIGHT = 20;
    public static final int HEIGHT = NAME_HEIGHT + 2*PADDING;
    
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
        this.textPadding = HEIGHT / 2 - this.parent.getTextRenderer().fontHeight / 2;
        this.setNameText();
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
        this.validationResult = this.property.validate(this.getSaveValue());
        this.setNameText();
    }
    
    private void setNameText() {
        this.nameWidget.setMessage(baseSettings.displayName().copy().formatted(this.validationResult.isValid() ? Formatting.WHITE : Formatting.RED));
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
}
