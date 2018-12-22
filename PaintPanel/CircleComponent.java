package PaintPanel;

import PaintPanel.PaintComponent;

import java.awt.*;

public class CircleComponent extends PaintComponent {


    public int getRadius() {
        return super.getSide()/2;
    }

    public CircleComponent(Color color, int windowX, int windowY ){
        super(color, windowX, windowY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getColor());
        g.drawOval(getWindowX(), getWindowY(), getSide(),getSide());
    }


}
