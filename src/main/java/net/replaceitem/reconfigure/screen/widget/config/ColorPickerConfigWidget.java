package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.mixin.AbstractSliderButtonAccessor;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.ColorPlanePickerWidget;
import net.replaceitem.reconfigure.screen.widget.ColorPreviewWidget;
import net.replaceitem.reconfigure.screen.widget.GradientSlider;
import net.replaceitem.reconfigure.util.ColorUtil;
import org.jspecify.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorPickerConfigWidget extends PropertyConfigWidget<Integer> {

    private final EditBox textField;
    private final ColorPreviewWidget colorPreviewWidget;
    private final ColorPlanePickerWidget colorPlanePickerWidget;
    private final GradientSlider hueSlider;
    private final GradientSlider saturationSlider;
    private final GradientSlider valueSlider;
    private final GradientSlider alphaSlider;
    private final GridLayout grid = new GridLayout(0, 0);
    private boolean isOpen = false;
    private boolean isInvalid = false;
    private int color;
    
    private static final int EXPANSION_HEIGHT = (20+8)*4-8;
    private static final int SPACING = 8;
    
    private enum UpdateTargets {
        PLANE,
        SLIDERS,
        TEXT_FIELD
    }
    
    private record BiFormatColor(int argb, ColorUtil.HSVColor hsv, float alpha) {
        public BiFormatColor(int argb) {
            this(argb, ColorUtil.rgbToHsvFloats(argb), ARGB.alphaFloat(argb));
        }

        public BiFormatColor(ColorUtil.HSVColor hsv, float alpha) {
            this(ARGB.color(ARGB.as8BitChannel(alpha), ColorUtil.hsvToRgb(hsv.hue(), hsv.saturation(), hsv.value())), hsv, alpha);
        }
    }
    
    private static final ScopedValue<Boolean> preventUpdate = ScopedValue.newInstance();

    public ColorPickerConfigWidget(ConfigWidgetList listWidget, PropertyImpl<Integer> property, BaseSettings baseSettings) {
        super(listWidget, DEFAULT_HEIGHT, property, baseSettings);
        this.colorPreviewWidget = new ColorPreviewWidget(0, 0, BASIC_WIDGET_SIZE, BASIC_WIDGET_SIZE) {
            @Override
            public void onClick(MouseButtonEvent click, boolean doubled) {
                if(click.button() == 0) {
                    setOpen(!isOpen);
                }
            }
        };
        this.textField = new EditBox(listWidget.getTextRenderer(), 70, NAME_HEIGHT, Component.empty());
        this.textField.setResponder(this::setColorFromTextField);
        this.textField.addFormatter((string, _) -> FormattedCharSequence.forward(
                string, isInvalid ? Style.EMPTY.withColor(ChatFormatting.RED) : Style.EMPTY
        ));
        
        this.colorPlanePickerWidget = new ColorPlanePickerWidget(0, 0, 0, EXPANSION_HEIGHT) {
            @Override
            public void onColorChanged(float hue, float saturation, float value) {
                ColorPickerConfigWidget.this.setColor(new BiFormatColor(new ColorUtil.HSVColor(hue, saturation, value), (float) alphaSlider.getValue()), UpdateTargets.PLANE);
            }
        };

        this.hueSlider = new GradientSlider(0, 0, 0, 20, Component.empty(), 0, (v) -> ARGB.colorFromFloat(1.0f, v, 0.5f, 0.5f)) {
            @Override
            protected void applyValue() {
                setColorFromSliders();
            }
        };
        this.saturationSlider = new GradientSlider(0, 0, 0, 20, Component.empty(), 1,
                (v) -> ColorUtil.hsvToRgb((float) hueSlider.getValue(), v, 1f)
        ) {
            @Override
            protected void applyValue() {
                setColorFromSliders();
            }
        };
        this.valueSlider = new GradientSlider(0, 0, 0, 20, Component.empty(), 1,
                (v) -> ColorUtil.hsvToRgb((float) hueSlider.getValue(), (float) saturationSlider.getValue(), v)
        ) {
            @Override
            protected void applyValue() {
                setColorFromSliders();
            }
        };
        this.alphaSlider = new GradientSlider(0, 0, 0, 20, Component.empty(), 1,
                (v) -> ARGB.color(v*255, ColorUtil.hsvToRgb((float) hueSlider.getValue(), (float) saturationSlider.getValue(), (float) valueSlider.getValue()))
        ) {
            @Override
            protected void applyValue() {
                setColorFromSliders();
            }
        };
        
        this.hueSlider.setHsv(true);
        
        grid.spacing(SPACING);
        grid.addChild(colorPlanePickerWidget, 0, 0, 4, 1);
        grid.addChild(hueSlider, 0, 1);
        grid.addChild(saturationSlider, 1, 1);
        grid.addChild(valueSlider, 2, 1);
        grid.addChild(alphaSlider, 3, 1);
        
        grid.visitWidgets(this.children::add);
        
        this.children.add(textField);
        this.children.add(colorPreviewWidget);
        
        this.loadValue(property.get());
        this.setOpen(false);
    }
    
    private void setColor(BiFormatColor color, @Nullable UpdateTargets excludeUpdate) {
        if(preventUpdate.orElse(false)) return;
        
        this.color = color.argb();
        this.colorPreviewWidget.setColor(color.argb());
        
        ScopedValue.where(preventUpdate, true).run(() -> {
                ColorUtil.HSVColor hsv = color.hsv();

                if(excludeUpdate != UpdateTargets.PLANE) {
                    this.colorPlanePickerWidget.setFromHsv(hsv.hue(), hsv.saturation(), hsv.value());
                }
                if(excludeUpdate != UpdateTargets.SLIDERS) {
                    ((AbstractSliderButtonAccessor) this.hueSlider).callSetValue(hsv.hue());
                    ((AbstractSliderButtonAccessor) this.saturationSlider).callSetValue(hsv.saturation());
                    ((AbstractSliderButtonAccessor) this.valueSlider).callSetValue(hsv.value());
                    ((AbstractSliderButtonAccessor) this.alphaSlider).callSetValue(color.alpha());
                }
                if(excludeUpdate != UpdateTargets.TEXT_FIELD) {
                    this.textField.setValue(String.format("#%08X", color.argb()));
                }
        });
        this.onValueChanged();
    }
    
    private void setColorFromSliders() {
        float hue = (float) hueSlider.getValue();
        float saturation = (float) saturationSlider.getValue();
        float value = (float) valueSlider.getValue();
        float alpha = (float) alphaSlider.getValue();
        this.setColor(new BiFormatColor(new ColorUtil.HSVColor(hue, saturation, value), alpha), UpdateTargets.SLIDERS);
    }
    
    private static final Pattern HEX_PATTERN = Pattern.compile("^#?([\\dA-F]{3,8})$");
    private void setColorFromTextField(String s) {
        s = s.toUpperCase();
        Matcher matcher = HEX_PATTERN.matcher(s);
        if(!matcher.matches()) {
            this.isInvalid = true;
            return;
        }
        String hex = matcher.group(1);
        if(hex.length() != 3 && hex.length() != 4 && hex.length() != 6  && hex.length() != 8) {
            this.isInvalid = true;
            return;
        }
        int hexInt = ((int) Long.parseUnsignedLong(hex, 16));
        // if it's a shortform code like #FFF or #FA09
        if(hex.length() <= 4) hexInt = ((hexInt & 0xF000) * 0x11000) | ((hexInt & 0xF00) * 0x1100) | ((hexInt & 0x0F0) * 0x0110) | ((hexInt & 0x00F) * 0x0011);
        // if alpha is missing use full opacity
        if(hex.length() % 3 == 0) hexInt |= 0xFF000000;
        ColorPickerConfigWidget.this.setColor(new BiFormatColor(hexInt), UpdateTargets.TEXT_FIELD);
        this.isInvalid = false;
    }
    
    private void setOpen(boolean open) {
        this.isOpen = open;
        this.setContentHeight(DEFAULT_HEIGHT + (open ? EXPANSION_HEIGHT + INNER_PADDING : 0));
        this.grid.visitWidgets(clickableWidget -> clickableWidget.visible = open);
        this.parent.reposition();
    }

    @Override
    public void refreshPosition() {
        super.refreshPosition();
        int topRowY = this.getContentY() + (DEFAULT_HEIGHT -20) / 2;
        this.colorPreviewWidget.setPosition(getContentRight() - INNER_PADDING - this.resetButtonWidget.getWidth() - this.colorPreviewWidget.getWidth(), topRowY);
        this.textField.setPosition(this.colorPreviewWidget.getX() - textField.getWidth(), topRowY);
        int contentWidth = getContentWidth() - 2*INNER_PADDING;
        this.colorPlanePickerWidget.setWidth(contentWidth/2);
        int sliderWidth = contentWidth - colorPlanePickerWidget.getWidth() - SPACING;
        this.hueSlider.setWidth(sliderWidth);
        this.saturationSlider.setWidth(sliderWidth);
        this.valueSlider.setWidth(sliderWidth);
        this.alphaSlider.setWidth(sliderWidth);
        this.grid.setPosition(getContentX() + INNER_PADDING, getContentY() + DEFAULT_HEIGHT);
        this.grid.arrangeElements();
    }

    @Override
    protected Integer getSaveValue() {
        return color;
    }

    @Override
    protected void loadValue(Integer value) {
        this.setColor(new BiFormatColor(value), null);
    }
}
