package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.Collection;
import java.util.function.Function;

public class CyclingButtonConfigWidget<P> extends SimpleConfigWidget<CyclingButtonWidget<P>, P> {
    public CyclingButtonConfigWidget(
            ConfigWidgetList listWidget,
            Property<P> property,
            BaseSettings baseSettings,
            Function<P, Text> valueToText,
            Collection<P> values
    ) {
        super(listWidget, property, baseSettings);
        setWidget(CyclingButtonWidget.builder(valueToText)
                .omitKeyText()
                .values(values)
                .initially(property.get())
                .build(Text.empty(), (button, value) -> {
                }));
    }

    @Override
    protected P getSaveValue() {
        return this.widget.getValue();
    }
}
