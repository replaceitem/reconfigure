package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.function.DoubleFunction;
import java.util.function.Function;

public class SliderConfigWidget<T extends Number> extends SimpleConfigWidget<SliderWidget, T> {
    
    private double sliderValue;
    private final DoubleFunction<T> controlToValue;
    
    public SliderConfigWidget(ConfigWidgetList listWidget, Property<T> property, Text displayName, DoubleFunction<T> controlToValue, Function<T, Double> valueToControl, Function<T, Text> valueToText) {
        super(listWidget, property, displayName);
        this.controlToValue = controlToValue;
        setWidget(new SliderWidget(0, 0, 0, 0, valueToText.apply(property.get()), valueToControl.apply(property.get())) {
            @Override
            protected void updateMessage() {
                this.setMessage(valueToText.apply(controlToValue.apply(value)));
            }

            @Override
            protected void applyValue() {
                sliderValue = this.value;
            }
        });
    }

    @Override
    protected void onSave() {
        this.property.set(controlToValue.apply(sliderValue));
    }

}
