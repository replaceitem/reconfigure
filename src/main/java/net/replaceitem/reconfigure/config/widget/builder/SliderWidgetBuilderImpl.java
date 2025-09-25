package net.replaceitem.reconfigure.config.widget.builder;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.api.widget.SliderWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.PropertyHolder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.SliderConfigWidget;
import org.jetbrains.annotations.Nullable;

import java.util.function.DoubleFunction;
import java.util.function.Function;

public abstract class SliderWidgetBuilderImpl<SELF extends SliderWidgetBuilderImpl<SELF, T>, T extends Number & Comparable<T>> extends WidgetBuilderImpl<SELF, T> implements SliderWidgetBuilder<SELF, T> {

    @Nullable protected T min;
    @Nullable protected T max;
    @Nullable protected T step;
    @Nullable private DoubleFunction<T> controlToValue;
    @Nullable private Function<T, Double> valueToControl;
    @Nullable private Function<T, Text> valueToText;

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
    protected void preBuild(PropertyHolder<T> property) {
        super.preBuild(property);
        if(min == null) throw new RuntimeException("Missing min value for slider widget " + property.getId());
        if(max == null) throw new RuntimeException("Missing max value for slider widget " + property.getId());
        if(controlToValue == null) controlToValue = defaultControlToNumber(min, max);
        if(valueToControl == null) valueToControl = defaultNumberToControl(min, max);
        if(valueToText == null) valueToText = defaultNumberToText(min, max);
    }

    @Override
    protected ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings) {
        assert controlToValue != null;
        assert valueToControl != null;
        assert valueToText != null;
        return (parent, property) ->
                new SliderConfigWidget<>(parent, property, baseSettings, controlToValue, valueToControl, valueToText);
    }

    protected abstract DoubleFunction<T> defaultControlToNumber(T min, T max);
    protected abstract Function<T, Double> defaultNumberToControl(T min, T max);
    protected abstract Function<T, Text> defaultNumberToText(T min, T max);
    
    
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
        protected DoubleFunction<Integer> defaultControlToNumber(Integer min, Integer max) {
            int multiple = step == null ? 1 : step;
            int diff = SliderWidgetBuilderImpl.floorToMultiple(max-min, multiple);
            return value -> min + SliderWidgetBuilderImpl.roundToMultiple(value * diff, multiple);
        }

        @Override
        protected Function<Integer, Double> defaultNumberToControl(Integer min, Integer max) {
            int diff = max - min;
            return value -> ((double) (MathHelper.clamp(value, min, max) - min)) / diff;
        }

        @Override
        protected Function<Integer, Text> defaultNumberToText(Integer min, Integer max) {
            return number -> Text.literal(number.toString());
        }
    }
    public static class DoubleSliderWidgetBuilder extends SliderWidgetBuilderImpl<DoubleSliderWidgetBuilder, Double> {

        protected DoubleSliderWidgetBuilder(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, Double> propertyBuilder) {
            super(propertyBuildContext, propertyBuilder);
        }

        private static final int RELEVANT_DIGITS = 4;

        @Override
        protected DoubleFunction<Double> defaultControlToNumber(Double min, Double max) {
            if(step == null) {
                double diff = max-min;
                return value -> min + value * diff;
            }
            double diff = SliderWidgetBuilderImpl.floorToMultiple(max-min, step);
            return value -> min + SliderWidgetBuilderImpl.roundToMultiple(value * diff, step);
        }

        @Override
        protected Function<Double, Double> defaultNumberToControl(Double min, Double max) {
            double diff = max - min;
            return value -> (MathHelper.clamp(value, min, max) - min) / diff;
        }

        @Override
        protected Function<Double, Text> defaultNumberToText(Double min, Double max) {
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
