package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.SliderWidgetBuilder;

public interface DoublePropertyBuilder extends NumericPropertyBuilder<DoublePropertyBuilder, Double> {
    SliderWidgetBuilder<?, Double> asSlider();
}
