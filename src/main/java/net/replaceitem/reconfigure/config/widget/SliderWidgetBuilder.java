package net.replaceitem.reconfigure.config.widget;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.config.WidgetBuilder;
import net.replaceitem.reconfigure.screen.widget.SliderConfigWidget;

import java.util.function.DoubleFunction;
import java.util.function.Function;

public abstract class SliderWidgetBuilder<SELF extends SliderWidgetBuilder<SELF, T>, T extends Number & Comparable<T>> extends WidgetBuilder<SELF, T> {

    protected T min;
    protected T max;
    protected T step;
    private DoubleFunction<T> controlToValue;
    private Function<T, Double> valueToControl;
    private Function<T, Text> valueToText;

    protected SliderWidgetBuilder(PropertyBuilder<?, T> propertyBuilder) {
        super(propertyBuilder);
    }

    public SELF min(T min) {
        this.min = min;
        return self();
    }

    public SELF max(T max) {
        this.max = max;
        return self();
    }

    public SELF range(T min, T max) {
        return min(min).max(max);
    }

    public SELF step(T step) {
        this.step = step;
        return self();
    }

    @Override
    protected void preBuild() {
        super.preBuild();
        if(min == null) throw new RuntimeException("Missing min value for slider widget " + getPropertyId());
        if(max == null) throw new RuntimeException("Missing max value for slider widget " + getPropertyId());
        if(controlToValue == null) controlToValue = controlToNumber();
        if(valueToControl == null) valueToControl = numberToControl();
        if(valueToText == null) valueToText = numberToText();
    }

    @Override
    protected ConfigWidgetFactory<T> buildWidgetFactory() {
        return (parent, property) ->
                new SliderConfigWidget<>(parent, property, displayName, controlToValue, valueToControl, valueToText);
    }

    protected abstract DoubleFunction<T> controlToNumber();
    protected abstract Function<T, Double> numberToControl();
    protected abstract Function<T, Text> numberToText();
    
    
    public static IntSliderWidgetBuilder createInt(PropertyBuilder<?, Integer> propertyBuilder) {
        return new IntSliderWidgetBuilder(propertyBuilder);
    }
    
    public static DoubleSliderWidgetBuilder createDouble(PropertyBuilder<?, Double> propertyBuilder) {
        return new DoubleSliderWidgetBuilder(propertyBuilder);
    }
    
    public static class IntSliderWidgetBuilder extends SliderWidgetBuilder<IntSliderWidgetBuilder, Integer> {
        protected IntSliderWidgetBuilder(PropertyBuilder<?, Integer> propertyBuilder) {
            super(propertyBuilder);
        }

        @Override
        protected DoubleFunction<Integer> controlToNumber() {
            int multiple = step == null ? 1 : step;
            int diff = SliderWidgetBuilder.floorToMultiple(max-min, multiple);
            return value -> min + SliderWidgetBuilder.roundToMultiple(value * diff, multiple);
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
    public static class DoubleSliderWidgetBuilder extends SliderWidgetBuilder<DoubleSliderWidgetBuilder, Double> {

        protected DoubleSliderWidgetBuilder(PropertyBuilder<?, Double> propertyBuilder) {
            super(propertyBuilder);
        }

        private static final int RELEVANT_DIGITS = 4;

        @Override
        protected DoubleFunction<Double> controlToNumber() {
            if(step == null) {
                double diff = max-min;
                return value -> min + value * diff;
            }
            double diff = SliderWidgetBuilder.floorToMultiple(max-min, step);
            return value -> min + SliderWidgetBuilder.roundToMultiple(value * diff, step);
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
