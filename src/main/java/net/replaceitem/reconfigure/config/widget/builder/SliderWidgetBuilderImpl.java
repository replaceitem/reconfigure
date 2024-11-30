package net.replaceitem.reconfigure.config.widget.builder;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.api.widget.SliderWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.SliderConfigWidget;

import java.util.function.DoubleFunction;
import java.util.function.Function;

public abstract class SliderWidgetBuilderImpl<SELF extends SliderWidgetBuilderImpl<SELF, T>, T extends Number & Comparable<T>> extends WidgetBuilderImpl<SELF, T> implements SliderWidgetBuilder<SELF, T> {

    protected T min;
    protected T max;
    protected T step;
    private DoubleFunction<T> controlToValue;
    private Function<T, Double> valueToControl;
    private Function<T, Text> valueToText;

    protected SliderWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, T> propertyBuilder) {
        super(propertyBuildContext, propertyBuilder);
    }

    @Override
    public SELF min(T min) {
        this.min = min;
        return self();
    }

    @Override
    public SELF max(T max) {
        this.max = max;
        return self();
    }

    @Override
    public SELF step(T step) {
        this.step = step;
        return self();
    }

    @Override
    protected void preBuild(PropertyImpl<T> property) {
        super.preBuild(property);
        if(min == null) throw new RuntimeException("Missing min value for slider widget " + property.getId());
        if(max == null) throw new RuntimeException("Missing max value for slider widget " + property.getId());
        if(controlToValue == null) controlToValue = controlToNumber();
        if(valueToControl == null) valueToControl = numberToControl();
        if(valueToText == null) valueToText = numberToText();
    }

    @Override
    protected ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) ->
                new SliderConfigWidget<>(parent, property, baseSettings, controlToValue, valueToControl, valueToText);
    }

    protected abstract DoubleFunction<T> controlToNumber();
    protected abstract Function<T, Double> numberToControl();
    protected abstract Function<T, Text> numberToText();
    
    
    public static IntSliderWidgetBuilder createInt(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Integer> propertyBuilder) {
        return new IntSliderWidgetBuilder(propertyBuildContext, propertyBuilder);
    }
    
    public static DoubleSliderWidgetBuilder createDouble(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Double> propertyBuilder) {
        return new DoubleSliderWidgetBuilder(propertyBuildContext, propertyBuilder);
    }
    
    public static class IntSliderWidgetBuilder extends SliderWidgetBuilderImpl<IntSliderWidgetBuilder, Integer> {
        protected IntSliderWidgetBuilder(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Integer> propertyBuilder) {
            super(propertyBuildContext, propertyBuilder);
        }

        @Override
        protected DoubleFunction<Integer> controlToNumber() {
            int multiple = step == null ? 1 : step;
            int diff = SliderWidgetBuilderImpl.floorToMultiple(max-min, multiple);
            return value -> min + SliderWidgetBuilderImpl.roundToMultiple(value * diff, multiple);
        }

        @Override
        protected Function<Integer, Double> numberToControl() {
            int diff = max - min;
            return value -> ((double) (MathHelper.clamp(value, min, max) - min)) / diff;
        }

        @Override
        protected Function<Integer, Text> numberToText() {
            return number -> Text.literal(number.toString());
        }
    }
    public static class DoubleSliderWidgetBuilder extends SliderWidgetBuilderImpl<DoubleSliderWidgetBuilder, Double> {

        protected DoubleSliderWidgetBuilder(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Double> propertyBuilder) {
            super(propertyBuildContext, propertyBuilder);
        }

        private static final int RELEVANT_DIGITS = 4;

        @Override
        protected DoubleFunction<Double> controlToNumber() {
            if(step == null) {
                double diff = max-min;
                return value -> min + value * diff;
            }
            double diff = SliderWidgetBuilderImpl.floorToMultiple(max-min, step);
            return value -> min + SliderWidgetBuilderImpl.roundToMultiple(value * diff, step);
        }

        @Override
        protected Function<Double, Double> numberToControl() {
            double diff = max - min;
            return value -> (MathHelper.clamp(value, min, max) - min) / diff;
        }

        @Override
        protected Function<Double, Text> numberToText() {
            int decimalDigits = Math.max(1, RELEVANT_DIGITS - ((int) Math.floor(Math.log10(Math.max(Math.abs(min), Math.abs(max))))));
            String formatString = "%." + decimalDigits + "f";
            return number -> Text.literal(String.format(formatString, number));
        }
    }
    
    private static double floorToMultiple(double val, double multiple) {
        return multiple * Math.floor(val / multiple);
    }
    private static double roundToMultiple(double val, double multiple) {
        return multiple * Math.round(val / multiple);
    }
    
    private static int roundToMultiple(double val, int multiple) {
        return (int) (multiple * Math.round(val / multiple));
    }
    private static int floorToMultiple(int val, int multiple) {
        return multiple * Math.floorDiv(val, multiple);
    }
}
