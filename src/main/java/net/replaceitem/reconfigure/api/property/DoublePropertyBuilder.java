package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.SliderWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;

public interface DoublePropertyBuilder extends NumericPropertyBuilder<DoublePropertyBuilder, Double> {
    /**
     * Finishes building the double property and starts building a slider widget for it.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The slider widget builder
     */
    SliderWidgetBuilder<?, Double> asSlider();
}
