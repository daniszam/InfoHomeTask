package PaintPanel;

import java.awt.*;

public class RectangleComponent extends PaintComponent {


    public RectangleComponent(Color color, int windowX, int windowY) {
        super(color, windowX, windowY);
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getColor());
        g.drawRect(getWindowX(), getWindowY(), getSide(),getSide());
    }
}
