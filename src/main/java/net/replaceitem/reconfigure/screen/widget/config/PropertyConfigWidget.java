package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.replaceitem.reconfigure.Reconfigure;
import net.replaceitem.reconfigure.api.ValidationResult;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.TooltippedStringWidget;

import java.util.Objects;

public abstract class PropertyConfigWidget<P> extends ConfigWidget {
    public static final int INNER_PADDING = 3;
    public static final int BASIC_WIDGET_SIZE = 20;
    public static final int NAME_HEIGHT = BASIC_WIDGET_SIZE;
    public static final int DEFAULT_HEIGHT = NAME_HEIGHT + 2* INNER_PADDING;
    
    private static final Identifier RESET_ICON = Reconfigure.id("reconfigure/reset");
    
    protected final int textPadding;

    protected final PropertyImpl<P> property;
    private final BaseSettings baseSettings;

    protected ValidationResult validationResult = ValidationResult.valid();

    protected final TooltippedStringWidget nameWidget;
    protected final Button resetButtonWidget;

    public PropertyConfigWidget(ConfigWidgetList listWidget, int contentHeight, PropertyImpl<P> property, BaseSettings baseSettings) {
        super(listWidget, contentHeight);
        this.property = property;
        this.baseSettings = baseSettings;
        this.nameWidget = new TooltippedStringWidget(Component.empty(), this.parent.getTextRenderer());
        this.children.add(nameWidget);
        this.resetButtonWidget = SpriteIconButton.builder(Component.empty(), button -> {
            this.loadValue(this.property.getDefaultValue());
        }, true).sprite(RESET_ICON, 10, 10).width(20).build();
        this.resetButtonWidget.active = !property.isDefault();
        this.children.add(this.resetButtonWidget);
        this.textPadding = DEFAULT_HEIGHT / 2 - this.parent.getTextRenderer().lineHeight / 2;
        this.setNameText(this.property.get());
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        this.positionName();
        this.positionResetButton();
    }

    private void positionResetButton() {
        this.resetButtonWidget.setPosition(getContentRight() - INNER_PADDING - this.resetButtonWidget.getWidth(), this.getContentY() + INNER_PADDING);
    }

    protected void positionName() {
        int maxNameWidth = this.getContentWidth() / 2 - textPadding;
        this.nameWidget.setMaxWidth(Math.min(this.parent.getTextRenderer().width(this.nameWidget.getMessage().getVisualOrderText()), maxNameWidth));
        this.nameWidget.setPosition(this.getContentX() + textPadding, this.getContentY() + textPadding);
    }
    
    protected void positionNameFullWidth() {
        int maxNameWidth = this.getContentWidth() - 2 * this.textPadding;
        this.nameWidget.setMaxWidth(Math.min(this.parent.getTextRenderer().width(this.nameWidget.getMessage().getVisualOrderText()), maxNameWidth));
        this.nameWidget.setPosition(this.getContentX() + textPadding, this.getContentY() + textPadding);
    }

    @Override
    public void onSave() {
        this.property.set(this.getSaveValue());
    }
    
    protected void onValueChanged() {
        P saveValue = this.getSaveValue();
        this.validate(saveValue);
        this.setNameText(saveValue);
        this.resetButtonWidget.active = !this.property.isDefault(saveValue);
        this.parent.onConfigWidgetChanged();
    }

    private void validate(P saveValue) {
        this.validationResult = this.property.validate(saveValue);
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    private void setNameText(P saveValue) {
        boolean wasChanged = !Objects.equals(saveValue, this.property.get());
        this.nameWidget.setMessage(baseSettings.displayName().copy().withStyle(style -> {
            style = style.withItalic(wasChanged);
            if(this.validationResult.isInvalid()) style = style.withColor(ChatFormatting.RED);
            return style;
        }));
        MutableComponent tooltipText = Component.empty();
        if(baseSettings.tooltip() != null) {
            tooltipText.append(baseSettings.tooltip());
        }
        if(baseSettings.tooltip() != null && this.validationResult.isInvalid()) tooltipText.append("\n");
        if(this.validationResult.isInvalid()) {
            tooltipText.append(validationResult.getMessage().copy().withStyle().withStyle(style -> {
                if(style.getColor() != null) return style;
                return style.applyFormat(ChatFormatting.RED);
            }));
        }
        this.nameWidget.setAdditionalTooltip(tooltipText.getSiblings().isEmpty() ? null : tooltipText);
    }

    protected abstract P getSaveValue();
    protected abstract void loadValue(P value);
}
