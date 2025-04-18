package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.Collection;
import java.util.function.Function;

public class CyclingButtonConfigWidget<P> extends SimpleConfigWidget<CyclingButtonWidget<P>, P> {
    public CyclingButtonConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<P> property,
            BaseSettings baseSettings,
            Function<P, Text> valueToText,
            Collection<P> values
    ) {
        super(listWidget, property, baseSettings);
        setWidget(CyclingButtonWidget.builder(valueToText)
                .omitKeyText()
                .values(values)
                .initially(property.get())
                .build(Text.empty(), (button, value) -> this.onValueChanged()));
    }

    @Override
    protected P getSaveValue() {
        return this.widget.getValue();
    }

    @Override
    protected void loadValue(P value) {
        this.widget.setValue(value);
        // manually need to trigger since setValue doesn't
        this.onValueChanged();
    }
}
