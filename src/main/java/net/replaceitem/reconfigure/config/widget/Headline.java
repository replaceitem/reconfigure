package net.replaceitem.reconfigure.config.widget;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.config.ConfigWidget;
import net.replaceitem.reconfigure.screen.widget.config.HeadlineConfigWidget;

public class Headline implements ConfigTabImpl.TabItem {
    private final Text text;

    public Headline(Text text) {
        this.text = text;
    }

    @Override
    public ConfigWidget createWidget(ConfigWidgetList parent) {
        return new HeadlineConfigWidget(parent, text);
    }
}
