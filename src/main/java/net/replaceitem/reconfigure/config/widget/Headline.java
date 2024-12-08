package net.replaceitem.reconfigure.config.widget;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;
import net.replaceitem.reconfigure.screen.widget.config.HeadlineConfigWidget;

import java.util.Optional;

public class Headline implements ConfigTabImpl.TabItem {
    private final Text text;

    public Headline(Text text) {
        this.text = text;
    }

    @Override
    public Optional<ConfigWidget> createWidget(ConfigWidgetList parent) {
        return Optional.of(new HeadlineConfigWidget(parent, text));
    }
}
