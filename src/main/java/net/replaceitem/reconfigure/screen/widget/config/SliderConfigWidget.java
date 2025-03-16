package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.mixin.SliderWidgetAccessor;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.function.DoubleFunction;
import java.util.function.Function;

public class SliderConfigWidget<T extends Number> extends SimpleConfigWidget<SliderWidget, T> {

    private double sliderValue;
    private final DoubleFunction<T> controlToValue;
    private final Function<T, Double> valueToControl;
    
    public SliderConfigWidget(ConfigWidgetList listWidget, PropertyImpl<T> property, BaseSettings baseSettings, DoubleFunction<T> controlToValue, Function<T, Double> valueToControl, Function<T, Text> valueToText) {
        super(listWidget, property, baseSettings);
        this.controlToValue = controlToValue;
        this.valueToControl = valueToControl;
        this.sliderValue = valueToControl.apply(property.get());
        setWidget(new SliderWidget(0, 0, 0, 0, valueToText.apply(property.get()), sliderValue) {
            @Override
            protected void updateMessage() {
                this.setMessage(valueToText.apply(controlToValue.apply(value)));
            }

            @Override
            protected void applyValue() {
                sliderValue = this.value;
                SliderConfigWidget.this.onValueChanged();
            }
        });
    }

    @Override
    protected T getSaveValue() {
        return this.controlToValue.apply(this.sliderValue);
    }

    @Override
    protected void loadValue(T value) {
        ((SliderWidgetAccessor) this.widget).callSetValue(this.valueToControl.apply(value));
    }
}
