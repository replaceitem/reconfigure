package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.ColorPickerWidgetBuilder;
import net.replaceitem.reconfigure.api.widget.WidgetBuilder;
import net.replaceitem.reconfigure.config.widget.builder.SliderWidgetBuilderImpl;

public interface IntPropertyBuilder extends NumericPropertyBuilder<IntPropertyBuilder, Integer> {
    /**
     * Finishes building the integer property and starts building a slider widget for it.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The slider widget builder
     */
    SliderWidgetBuilderImpl.IntSliderWidgetBuilder asSlider();
    /**
     * Finishes building the integer property and starts building a color picker widget for it.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The color picker widget builder
     */
    ColorPickerWidgetBuilder asColorPicker();
}
