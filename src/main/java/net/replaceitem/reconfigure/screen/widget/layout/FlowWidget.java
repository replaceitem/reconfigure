package net.replaceitem.reconfigure.screen.widget.layout;

import net.minecraft.client.gui.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FlowWidget extends WrapperWidget {

    private final List<Widget> children = new ArrayList<>();
    private final List<Element> elements = new ArrayList<>();
    private final Positioner mainPositioner = Positioner.create();
    
    private int flowSpacing = 0;
    private int wrapSpacing = 0;
    private final DisplayAxis flowAxis;

    public FlowWidget(DisplayAxis flowAxis) {
        this(0, 0, 0, 0, flowAxis);
    }

    public FlowWidget(int x, int y, int width, int height, DisplayAxis flowAxis) {
        super(x, y, width, height);
        this.flowAxis = flowAxis;
    }

    public void setFlowSpacing(int flowSpacing) {
        this.flowSpacing = flowSpacing;
    }

    public void setWrapSpacing(int wrapSpacing) {
        this.wrapSpacing = wrapSpacing;
    }
    
    public void setFlowWidth(int size) {
        switch (this.flowAxis) {
            case HORIZONTAL -> this.width = size;
            case VERTICAL -> this.height = size;
        }
    }
    
    public <T extends Widget> T add(T widget) {
        return this.add(widget, this.getMainPositioner());
    }

    public <T extends Widget> T add(T widget, Positioner positioner) {
        this.elements.add(new Element(widget, positioner));
        this.children.add(widget);
        return widget;
    }

    @Override
    public void forEachElement(Consumer<Widget> consumer) {
        this.children.forEach(consumer);
    }

    @Override
    public void refreshPositions() {
        super.refreshPositions();
        
        int flowAxisPos = 0;
        int flowAxisTotalSize = flowAxis.getLength(this);
        int flowAxisOrigin = flowAxis.getCoordinate(this);

        int wrapAxisPos = 0;
        int wrapAxisOrigin = flowAxis.other().getCoordinate(this);
        
        List<Element> thisRowElements = new ArrayList<>();
        for (Element element : this.elements) {
            int flowAxisElementSize = flowAxis.getLength(element);
            if(flowAxisPos + flowAxisElementSize >= flowAxisTotalSize && !thisRowElements.isEmpty()) {
                int maxWrapAxisSize = thisRowElements.stream().mapToInt(e -> flowAxis.other().getLength(e)).max().orElse(0);
                for (Element thisRowElement : thisRowElements) {
                    flowAxis.other().setCoordinate(thisRowElement, wrapAxisOrigin + wrapAxisPos, maxWrapAxisSize);
                }
                thisRowElements.clear();
                wrapAxisPos += maxWrapAxisSize + wrapSpacing;
                flowAxisPos = 0;
            }
            thisRowElements.add(element);
            flowAxis.setCoordinate(element, flowAxisOrigin + flowAxisPos, element.getWidth());
            flowAxisPos += flowAxis.getLength(element) + flowSpacing;
        }
        int maxWrapAxisSize = thisRowElements.stream().mapToInt(e -> flowAxis.other().getLength(e)).max().orElse(0);
        for (Element thisRowElement : thisRowElements) {
            flowAxis.other().setCoordinate(thisRowElement, wrapAxisOrigin + wrapAxisPos, maxWrapAxisSize);
        }
        thisRowElements.clear();
        switch (flowAxis) {
            case HORIZONTAL -> this.height = wrapAxisPos + maxWrapAxisSize;
            case VERTICAL -> this.width = wrapAxisPos + maxWrapAxisSize;
        }
    }

    public Positioner copyPositioner() {
        return this.mainPositioner.copy();
    }

    public Positioner getMainPositioner() {
        return this.mainPositioner;
    }

    protected static class Element extends WrappedElement {
        protected Element(Widget widget, Positioner positioner) {
            super(widget, positioner);
        }
    }


    public enum DisplayAxis {
        HORIZONTAL,
        VERTICAL;
        
        DisplayAxis other() {
            return DisplayAxis.values()[1 - this.ordinal()];
        }
        
        int getCoordinate(Widget widget) {
            return switch (this) {
                case HORIZONTAL -> widget.getX();
                case VERTICAL -> widget.getY();
            };
        }
        
        int getLength(Widget widget) {
            return switch (this) {
                case HORIZONTAL -> widget.getWidth();
                case VERTICAL -> widget.getHeight();
            };
        }
        
        int getLength(Element element) {
            return switch (this) {
                case HORIZONTAL -> element.getWidth();
                case VERTICAL -> element.getHeight();
            };
        }

        void setCoordinate(Element element, int low, int high) {
            switch (this) {
                case HORIZONTAL:
                    element.setX(low, high);
                    break;
                case VERTICAL:
                    element.setY(low, high);
            }
        }
    }
}
