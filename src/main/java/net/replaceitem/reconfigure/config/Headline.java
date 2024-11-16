package net.replaceitem.reconfigure.config;

import net.minecraft.text.Text;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.ConfigWidget;
import net.replaceitem.reconfigure.screen.widget.HeadlineConfigWidget;

public class Headline implements ConfigTab.TabItem {
    private final Text text;

    public Headline(Text text) {
        this.text = text;
    }

    @Override
    public ConfigWidget createWidget(ConfigWidgetList parent) {
        return new HeadlineConfigWidget(parent, text);
    }
}
