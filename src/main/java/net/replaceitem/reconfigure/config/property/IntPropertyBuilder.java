package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.config.widget.SliderWidgetBuilder;

import java.util.function.Consumer;

public class IntPropertyBuilder extends NumericPropertyBuilder<IntPropertyBuilder, Integer> {
    public IntPropertyBuilder(Consumer<Property<Integer>> propertyConsumer, Identifier id) {
        super(propertyConsumer, id);
        defaultValue = 0;
    }
    public SliderWidgetBuilder.IntSliderWidgetBuilder asSlider() {
        return SliderWidgetBuilder.createInt(this).min(this.min).max(this.max);
    }
}
