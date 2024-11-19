package net.replaceitem.reconfigure.config.widget;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.WidgetBuilder;
import net.replaceitem.reconfigure.config.property.BooleanPropertyBuilder;
import net.replaceitem.reconfigure.config.PropertyBuilder;
import net.replaceitem.reconfigure.screen.widget.config.CyclingButtonConfigWidget;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.replaceitem.reconfigure.Reconfigure.NAMESPACE;

public class CyclingButtonWidgetBuilder<T> extends WidgetBuilder<CyclingButtonWidgetBuilder<T>, T> {

    private Collection<T> values;
    private final Collection<T> allValues;
    private Function<T, Text> valueToText;

    public CyclingButtonWidgetBuilder(PropertyBuilder<?, T> propertyBuilder, Collection<T> values) {
        super(propertyBuilder);
        this.values = values;
        this.allValues = values.stream().toList();
    }

    private static final List<Boolean> BOOLEAN_VALUES = List.of(Boolean.FALSE, Boolean.TRUE);
    public static CyclingButtonWidgetBuilder<Boolean> createBoolean(BooleanPropertyBuilder propertyBuilder) {
        return new CyclingButtonWidgetBuilder<>(propertyBuilder, BOOLEAN_VALUES).valueToText(value -> value ? ScreenTexts.ON : ScreenTexts.OFF);
    }


    public CyclingButtonWidgetBuilder<T> values(Collection<T> values) {
        this.values = values;
        return this;
    }

    public CyclingButtonWidgetBuilder<T> valueToText(Function<T, Text> valueToText) {
        this.valueToText = valueToText;
        return this;
    }

    @Override
    protected void preBuild() {
        if(values.stream().anyMatch(Predicate.not(allValues::contains))) {
            String invalidValues = values.stream().filter(Predicate.not(allValues::contains)).map(Objects::toString).collect(Collectors.joining(", "));
            String validValues = allValues.stream().map(Objects::toString).collect(Collectors.joining(", "));
            throw new RuntimeException("The value(s) " + invalidValues + " assigned to the enum widget are not in the values " + validValues + " for its property " + getPropertyId());
        }
        super.preBuild();
        if(valueToText == null) valueToText = (T value) -> Text.translatable(getPropertyId().toTranslationKey(NAMESPACE + ".property", "enum." + value.toString().toLowerCase()));
    }

    @Override
    protected ConfigWidgetFactory<T> buildWidgetFactory(BaseSettings baseSettings) {
        return (parent, property) -> new CyclingButtonConfigWidget<>(parent, property, baseSettings, valueToText, values);
    }
}
