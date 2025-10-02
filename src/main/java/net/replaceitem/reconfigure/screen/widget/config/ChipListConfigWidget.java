package net.replaceitem.reconfigure.screen.widget.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.replaceitem.reconfigure.config.BaseSettings;
import net.replaceitem.reconfigure.config.property.PropertyImpl;
import net.replaceitem.reconfigure.screen.ConfigWidgetList;
import net.replaceitem.reconfigure.screen.widget.DynamicTextFieldWidget;
import net.replaceitem.reconfigure.screen.widget.layout.FlowWidget;
import net.replaceitem.reconfigure.screen.widget.layout.SocketWidget;

import java.util.ArrayList;
import java.util.List;

public class ChipListConfigWidget extends PropertyConfigWidget<List<String>> {
    private final DynamicTextFieldWidget textField;
    private final GridWidget grid = new GridWidget();
    private final ButtonWidget addButton;
    private final List<Chip> chips = new ArrayList<>();
    private final SocketWidget<FlowWidget> flowSocket = new SocketWidget<>(createFlowWidget());
    private final boolean chipsEditable;

    public ChipListConfigWidget(ConfigWidgetList listWidget, PropertyImpl<List<String>> property, BaseSettings baseSettings, boolean chipsEditable) {
        super(listWidget, 0, property, baseSettings);
        this.chipsEditable = chipsEditable;
        
        this.grid.setRowSpacing(INNER_PADDING);
        
        this.textField = new DynamicTextFieldWidget(listWidget.getTextRenderer(), 0, 0, getContentWidth() - 2* INNER_PADDING, 20, Text.empty());
        this.textField.setMaxLength(10000);
        grid.add(textField, 0, 0);
        
        this.addButton = ButtonWidget.builder(Text.literal("+")
                .styled(style -> style.withColor(Formatting.GREEN)), this::addChipButtonClicked)
                .size(20, 20)
                .build();
        grid.add(addButton, 0, 1);
        
        grid.add(flowSocket, 1, 0, 1, 2);
        
        grid.forEachChild(this.children::add);

        this.loadValue(property.get());
    }

    private void addChipButtonClicked(ButtonWidget buttonWidget) {
        this.addChip(this.textField.getText());
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

        this.refreshPosition();
    }
    
    @Override
    protected List<String> getSaveValue() {
        return chips.stream().map(TextFieldWidget::getText).toList();
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
        this.textField.setWidth(gridWidth - addButton.getWidth());
        this.flowSocket.getInner().setFlowWidth(gridWidth);
        this.grid.setPosition(this.getContentX() + INNER_PADDING, this.getContentY() + NAME_HEIGHT + INNER_PADDING *2);
        this.grid.refreshPositions();
        this.setHeight(NAME_HEIGHT + this.grid.getHeight() + 4* INNER_PADDING);
    }
    
    class Chip extends TextFieldWidget {
        private final TextRenderer textRenderer;

        public Chip(TextRenderer textRenderer, String value) {
            super(textRenderer, MathHelper.clamp(textRenderer.getWidth(value) + 8 + 2 + REMOVE_BUTTON_SIZE, 50, 250), 20, Text.empty());
            this.textRenderer = textRenderer;
            this.setMaxLength(Integer.MAX_VALUE);
            this.setText(value);
            this.setCursor(0, false);
            this.setEditable(chipsEditable);
            this.setChangedListener(s -> ChipListConfigWidget.this.onValueChanged());
        }
        
        private static final Text REMOVE_TEXT = Text.literal("x").styled(style -> style.withColor(Formatting.RED));
        public static final int REMOVE_BUTTON_SIZE = 10;

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            super.renderWidget(context, mouseX, mouseY, delta);
            context.drawCenteredTextWithShadow(this.textRenderer, REMOVE_TEXT, getRight() - height/2, getY() + height/2 - textRenderer.fontHeight/2, 0xFFFFFFFF);
            int backgroundColor = ColorHelper.getArgb(removeButtonHovered(mouseX, mouseY) ? 128 : 64, 255, 255, 255);
            context.fill(getRight() - height/2 - REMOVE_BUTTON_SIZE/2, getY() + height/2 - REMOVE_BUTTON_SIZE/2, getRight() - height/2 + REMOVE_BUTTON_SIZE/2, getY() + height/2 + REMOVE_BUTTON_SIZE/2, backgroundColor);
        }
        
        public boolean removeButtonHovered(int mouseX, int mouseY) {
            return mouseX >= getRight() - height / 2 - REMOVE_BUTTON_SIZE / 2 &&
                    mouseY >= getY() + height / 2 - REMOVE_BUTTON_SIZE / 2 &&
                    mouseX < getRight() - height / 2 + REMOVE_BUTTON_SIZE / 2 &&
                    mouseY < getY() + height / 2 + REMOVE_BUTTON_SIZE / 2;
        }

        @Override
        public void onClick(Click click, boolean doubled) {
            if(this.removeButtonHovered((int) click.x(), (int) click.y())) {
                playClickSound(MinecraftClient.getInstance().getSoundManager());
                ChipListConfigWidget.this.removeChip(this);
            } else super.onClick(click, doubled);
        }

        @Override
        public int getInnerWidth() {
            return super.getInnerWidth() - REMOVE_BUTTON_SIZE - 2;
        }
    }
}
