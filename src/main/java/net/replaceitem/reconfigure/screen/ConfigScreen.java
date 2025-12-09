package net.replaceitem.reconfigure.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.replaceitem.reconfigure.config.ConfigImpl;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class ConfigScreen extends Screen {
    @Nullable private final Screen parent;
    private final ConfigImpl config;
    @Nullable private StringWidget headline;
    @Nullable private TabNavigationBar tabNavigation;
    private final TabManager tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
    private final HeaderAndFooterLayout threePartsLayoutWidget = new HeaderAndFooterLayout(this);
    private ConfigScreenTab[] tabs = {};

    public ConfigScreen(ConfigImpl config, @Nullable Screen parent) {
        super(config.getTitle());
        this.config = config;
        this.parent = parent;
    }

    @Override
    protected void init() {
        tabs = this.config.getTabs().stream()
                .map(configTab -> new ConfigScreenTab(configTab, Objects.requireNonNull(minecraft)))
                .toArray(ConfigScreenTab[]::new);
        this.tabNavigation = TabNavigationBar.builder(this.tabManager, this.width)
                .addTabs(tabs)
                .build();
        this.tabNavigation.selectTab(0, false);
        if(!config.hasSingleDefaultTab()) {
            this.addRenderableWidget(this.tabNavigation);
        } else {
            this.headline = new StringWidget(config.getTitle(), this.font);
            this.addRenderableWidget(headline);
        }
        LinearLayout directionalLayoutWidget = this.threePartsLayoutWidget.addToFooter(LinearLayout.horizontal().spacing(8));
        directionalLayoutWidget.addChild(Button.builder(Component.translatable("reconfigure.cancel"), button -> onClose()).build());
        directionalLayoutWidget.addChild(Button.builder(Component.translatable("reconfigure.save"), button -> saveAndClose()).build());

        this.threePartsLayoutWidget.visitWidgets(this::addRenderableWidget);

        repositionElements();
    }

    private void saveAndClose() {
        for (ConfigScreenTab tab : this.tabs) {
            tab.onSave();
        }
        this.config.save();
        this.onClose();
    }

    @Override
    public void onClose() {
        if(this.minecraft != null) this.minecraft.setScreen(parent);
    }

    @Override
    protected void repositionElements() {
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.arrangeElements();
            if(headline != null) {
                this.headline.setHeight(this.tabNavigation.getRectangle().height());
                this.headline.setX(this.width / 2 - this.headline.getWidth() / 2);
            }
            int headerHeight = this.tabNavigation.getRectangle().bottom();
            ScreenRectangle screenRect = new ScreenRectangle(0, headerHeight, this.width, this.height - this.threePartsLayoutWidget.getFooterHeight() - headerHeight);
            this.tabManager.setTabArea(screenRect);
            this.threePartsLayoutWidget.setHeaderHeight(headerHeight);
            this.threePartsLayoutWidget.arrangeElements();
        }
    }
}
