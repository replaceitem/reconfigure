package net.replaceitem.reconfigure.config.widget.builder;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.api.widget.CyclingButtonWidgetBuilder;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.PropertyHolder;
import net.replaceitem.reconfigure.config.property.PropertyBuildContext;
import net.replaceitem.reconfigure.config.property.builder.BooleanPropertyBuilderImpl;
import net.replaceitem.reconfigure.config.property.builder.PropertyBuilderImpl;
import net.replaceitem.reconfigure.config.widget.ConfigWidgetFactory;
import net.replaceitem.reconfigure.screen.widget.config.CyclingButtonConfigWidget;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CyclingButtonWidgetBuilderImpl<T> extends WidgetBuilderImpl<CyclingButtonWidgetBuilder<T>, T> implements CyclingButtonWidgetBuilder<T> {

    private Collection<T> values;
    private final Collection<T> allValues;
    @Nullable private Function<T, Text> valueToText;

    public CyclingButtonWidgetBuilderImpl(PropertyBuildContext propertyBuildContext, PropertyBuilderImpl<?, T> propertyBuilder, Collection<T> values) {
        super(propertyBuildContext, propertyBuilder);
        this.values = values;
        this.allValues = values.stream().toList();
    }

    private static final List<Boolean> BOOLEAN_VALUES = List.of(Boolean.FALSE, Boolean.TRUE);
    public static CyclingButtonWidgetBuilderImpl<Boolean> createBoolean(PropertyBuildContext propertyBuildContext, BooleanPropertyBuilderImpl propertyBuilder) {
        return new CyclingButtonWidgetBuilderImpl<>(propertyBuildContext, propertyBuilder, BOOLEAN_VALUES).valueToText(value -> value ? ScreenTexts.ON : ScreenTexts.OFF);
    }


    @Override
    public CyclingButtonWidgetBuilderImpl<T> values(Collection<T> values) {
        this.values = values;
        return this;
    }

    @Override
    public CyclingButtonWidgetBuilderImpl<T> valueToText(Function<T, Text> valueToText) {
        this.valueToText = valueToText;
        return this;
    }

    @Override
    protected void preBuild(PropertyHolder<T> property) {
        if(values.stream().anyMatch(Predicate.not(allValues::contains))) {
            String invalidValues = values.stream().filter(Predicate.not(allValues::contains)).map(Objects::toString).collect(Collectors.joining(", "));
            String validValues = allValues.stream().map(Objects::toString).collect(Collectors.joining(", "));
            throw new RuntimeException("The value(s) " + invalidValues + " assigned to the enum widget are not in the values " + validValues + " for its property " + property.getId());
        }
        if(valueToText == null) throw new RuntimeException("valueToText is required for a cycling button widget");
        super.preBuild(property);
    }

    @Override
    protected ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings) {
        assert valueToText != null;
        return (parent, property) -> new CyclingButtonConfigWidget<>(parent, property, baseSettings, valueToText, values);
    }
}
