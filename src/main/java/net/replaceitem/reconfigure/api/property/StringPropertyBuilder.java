package net.replaceitem.reconfigure.api.property;

import net.replaceitem.reconfigure.api.widget.WidgetBuilder;
import net.replaceitem.reconfigure.config.widget.builder.EditBoxWidgetBuilderImpl;
import net.replaceitem.reconfigure.config.widget.builder.TextFieldWidgetBuilderImpl;

public interface StringPropertyBuilder extends PropertyBuilder<StringPropertyBuilder, String> {
    /**
     * Finishes building the string property and starts building a text field widget for it.
     * A text field widget is a single line text box.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The text field widget builder
     */
    TextFieldWidgetBuilderImpl asTextField();

    /**
     * Finishes building the string property and starts building an edit box widget for it.
     * An edit box is a multiline text box with scrolling support.
     * The returned widget builder can then be used to configure the widget.
     * To finish building the widget, use {@link WidgetBuilder#build()} which
     * will return the created property. 
     * @return The edit box widget builder
     */
    EditBoxWidgetBuilderImpl asEditBox();
}
