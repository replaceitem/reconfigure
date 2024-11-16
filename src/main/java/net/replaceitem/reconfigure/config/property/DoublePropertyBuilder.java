package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.widget.SliderWidgetBuilder;

import java.util.function.Consumer;

public class DoublePropertyBuilder extends NumericPropertyBuilder<DoublePropertyBuilder, Double> {
    public DoublePropertyBuilder(Consumer<Property<Double>> propertyConsumer, Identifier id) {
        super(propertyConsumer, id);
        defaultValue = 0.0;
    }
    
    public SliderWidgetBuilder<?, Double> asSlider() {
        return SliderWidgetBuilder.createDouble(this).min(min).max(max);
    }
}
