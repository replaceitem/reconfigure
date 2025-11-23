package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicEditBox;
import net.replaceitem.reconfigure.screen.widget.layout.FlowWidget;
import net.replaceitem.reconfigure.screen.widget.layout.SocketWidget;

import java.util.ArrayList;
import java.util.List;

public class ChipListConfigWidget extends PropertyConfigWidget<List<String>> {
    private final DynamicEditBox editBox;
    private final GridLayout grid = new GridLayout();
    private final Button addButton;
    private final List<Chip> chips = new ArrayList<>();
    private final SocketWidget<FlowWidget> flowSocket = new SocketWidget<>(createFlowWidget());
    private final boolean chipsEditable;

    public ChipListConfigWidget(ConfigWidgetList listWidget, PropertyImpl<List<String>> property, BaseSettings baseSettings, boolean chipsEditable) {
        super(listWidget, 0, property, baseSettings);
        this.chipsEditable = chipsEditable;
        
        this.grid.rowSpacing(INNER_PADDING);
        
        this.editBox = new DynamicEditBox(listWidget.getTextRenderer(), 0, 0, getContentWidth() - 2* INNER_PADDING, 20, Component.empty());
        this.editBox.setMaxLength(10000);
        grid.addChild(editBox, 0, 0);
        
        this.addButton = Button.builder(Component.literal("+")
                .withStyle(style -> style.withColor(ChatFormatting.GREEN)), this::addChipButtonClicked)
                .size(20, 20)
                .build();
        grid.addChild(addButton, 0, 1);
        
        grid.addChild(flowSocket, 1, 0, 1, 2);
        
        grid.visitWidgets(this.children::add);

        this.loadValue(property.get());
    }

    private void addChipButtonClicked(Button buttonWidget) {
        this.addChip(this.editBox.getValue());
    }
    
    private static FlowWidget createFlowWidget() {
        FlowWidget flowWidget = new FlowWidget(FlowWidget.DisplayAxis.HORIZONTAL);
        flowWidget.setFlowSpacing(1);
        flowWidget.setWrapSpacing(2);
        return flowWidget;
    }

    private void addChip(String value) {
        Chip chip = new Chip(this.parent.getTextRenderer(), value);
        this.chips.add(chip);
        this.children.add(chip);
        this.onValueChanged();
        refreshChips();
    }

    private void removeChip(Chip chip) {
        this.chips.remove(chip);
        this.children.remove(chip);
        this.onValueChanged();
        refreshChips();
    }

    private void refreshChips() {
        FlowWidget flowWidget = createFlowWidget();
        this.flowSocket.setInner(flowWidget);
        for (Chip c : chips) {
            flowWidget.add(c);
        }

        this.parent.reposition();
    }
    
    @Override
    protected List<String> getSaveValue() {
        return chips.stream().map(EditBox::getValue).toList();
    }

    @Override
    protected void loadValue(List<String> value) {
        this.chips.forEach(this.children::remove);
        this.chips.clear();
        for (String item : value) {
            Chip chip = new Chip(this.parent.getTextRenderer(), item);
            this.chips.add(chip);
            this.children.add(chip);
        }
        this.onValueChanged();
        this.refreshChips();
    }

    @Override
    protected void positionName() {
        this.positionNameFullWidth();
    }
    
    @Override
    public void refreshPosition() {
        super.refreshPosition();
        int gridWidth = this.getContentWidth() - 2* INNER_PADDING;
        this.editBox.setWidth(gridWidth - addButton.getWidth());
        this.flowSocket.getInner().setFlowWidth(gridWidth);
        this.grid.setPosition(this.getContentX() + INNER_PADDING, this.getContentY() + NAME_HEIGHT + INNER_PADDING *2);
        this.grid.arrangeElements();
        this.setHeight(NAME_HEIGHT + this.grid.getHeight() + 4* INNER_PADDING);
    }
    
    class Chip extends EditBox {
        private final Font textRenderer;

        public Chip(Font textRenderer, String value) {
            super(textRenderer, Mth.clamp(textRenderer.width(value) + 8 + 2 + REMOVE_BUTTON_SIZE, 50, 250), 20, Component.empty());
            this.textRenderer = textRenderer;
            this.setMaxLength(Integer.MAX_VALUE);
            this.setValue(value);
            this.moveCursorTo(0, false);
            this.setEditable(chipsEditable);
            this.setResponder(s -> ChipListConfigWidget.this.onValueChanged());
        }
        
        private static final Component REMOVE_TEXT = Component.literal("x").withStyle(style -> style.withColor(ChatFormatting.RED));
        public static final int REMOVE_BUTTON_SIZE = 10;

        @Override
        public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
            super.renderWidget(context, mouseX, mouseY, delta);
            context.drawCenteredString(this.textRenderer, REMOVE_TEXT, getRight() - height/2, getY() + height/2 - textRenderer.lineHeight/2, 0xFFFFFFFF);
            int backgroundColor = ARGB.color(removeButtonHovered(mouseX, mouseY) ? 128 : 64, 255, 255, 255);
            context.fill(getRight() - height/2 - REMOVE_BUTTON_SIZE/2, getY() + height/2 - REMOVE_BUTTON_SIZE/2, getRight() - height/2 + REMOVE_BUTTON_SIZE/2, getY() + height/2 + REMOVE_BUTTON_SIZE/2, backgroundColor);
        }
        
        public boolean removeButtonHovered(int mouseX, int mouseY) {
            return mouseX >= getRight() - height / 2 - REMOVE_BUTTON_SIZE / 2 &&
                    mouseY >= getY() + height / 2 - REMOVE_BUTTON_SIZE / 2 &&
                    mouseX < getRight() - height / 2 + REMOVE_BUTTON_SIZE / 2 &&
                    mouseY < getY() + height / 2 + REMOVE_BUTTON_SIZE / 2;
        }

        @Override
        public void onClick(MouseButtonEvent click, boolean doubled) {
            if(this.removeButtonHovered((int) click.x(), (int) click.y())) {
                playButtonClickSound(Minecraft.getInstance().getSoundManager());
                ChipListConfigWidget.this.removeChip(this);
            } else super.onClick(click, doubled);
        }

        @Override
        public int getInnerWidth() {
            return super.getInnerWidth() - REMOVE_BUTTON_SIZE - 2;
        }
    }
}
