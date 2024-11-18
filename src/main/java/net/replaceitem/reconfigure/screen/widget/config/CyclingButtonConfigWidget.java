package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import net.replaceitem.reconfigure.config.Property;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;

import java.util.Collection;
import java.util.function.Function;

public class CyclingButtonConfigWidget<T> extends SimpleConfigWidget<CyclingButtonWidget<T>, T> {
    public CyclingButtonConfigWidget(ConfigWidgetList listWidget, Property<T> property, Text displayName, Function<T, Text> valueToText, Collection<T> values) {
        super(listWidget, property, displayName);
        setWidget(CyclingButtonWidget.builder(valueToText)
                .omitKeyText()
                .values(values)
                .initially(property.get())
                .build(Text.empty(), (button, value) -> {}));
    }

    @Override
    protected void onSave() {
        this.property.set(this.widget.getValue());
    }
    
}
