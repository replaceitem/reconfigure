package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.config.widget.builder.SliderWidgetBuilderImpl;

public interface IntPropertyBuilder extends NumericPropertyBuilder<IntPropertyBuilder, Integer> {
    SliderWidgetBuilderImpl.IntSliderWidgetBuilder asSlider();
}