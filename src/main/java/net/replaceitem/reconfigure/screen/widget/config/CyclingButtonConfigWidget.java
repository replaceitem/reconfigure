package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.Collection;
import java.util.function.Function;

public class CyclingButtonConfigWidget<P> extends SimpleConfigWidget<CycleButton<P>, P> {
    public CyclingButtonConfigWidget(
            ConfigWidgetList listWidget,
            PropertyImpl<P> property,
            BaseSettings baseSettings,
            Function<P, Component> valueToText,
            Collection<P> values
    ) {
        super(listWidget, property, baseSettings);
        setWidget(CycleButton.builder(valueToText)
                .displayOnlyValue()
                .withValues(values)
                .withInitialValue(property.get())
                .create(Component.empty(), (button, value) -> this.onValueChanged()));
    }

    @Override
    protected P getSaveValue() {
        return this.widget().getValue();
    }

    @Override
    protected void loadValue(P value) {
        this.widget().setValue(value);
        // manually need to trigger since setValue doesn't
        this.onValueChanged();
    }
}
