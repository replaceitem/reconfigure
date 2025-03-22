package net.replaceitem.reconfigure.api.widget;

import net.minecraft.text.Text;

public interface AbstractTextFieldWidgetBuilder<SELF extends AbstractTextFieldWidgetBuilder<SELF>> extends WidgetBuilder<SELF, String> {
    SELF placeholder(Text placeholder);
    SELF placeholder(String placeholder);
}
