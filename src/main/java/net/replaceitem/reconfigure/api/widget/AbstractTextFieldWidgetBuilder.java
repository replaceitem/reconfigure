package net.replaceitem.reconfigure.api.widget;

import net.minecraft.network.chat.Component;

public interface AbstractTextFieldWidgetBuilder<SELF extends AbstractTextFieldWidgetBuilder<SELF>> extends WidgetBuilder<SELF, String> {
    /**
     * Sets the placeholder text for the text field widget.
     * The placeholder is shown in the text field in gray when it is empty.
     * @param placeholder The placeholder text
     * @return The builder for chaining
     */
    SELF placeholder(Component placeholder);
    
    /**
     * Sets the placeholder for the text field widget to the default translatable text.
     * The translation key will be {@code reconfigure.property.[namespace].[propertyname].placeholder}.
     * The placeholder is shown in the text field in gray when it is empty.
     * @return The builder for chaining
     */
    SELF placeholder();
}
