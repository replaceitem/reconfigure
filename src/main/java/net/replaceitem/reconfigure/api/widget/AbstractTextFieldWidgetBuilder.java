package net.replaceitem.reconfigure.api.widget;

public interface AbstractTextFieldWidgetBuilder<SELF extends AbstractTextFieldWidgetBuilder<SELF>> extends WidgetBuilder<SELF, String> {
    SELF placeholder(String placeholder);
}
