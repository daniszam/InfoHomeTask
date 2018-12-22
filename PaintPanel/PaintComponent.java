package PaintPanel;

import javax.swing.*;
import java.awt.*;

public abstract class PaintComponent extends JComponent {

    private Color color;
    private int windowX;
    private int windowY;
    private int side;

    public  PaintComponent(Color color, int windowX, int windowY){
        this.color = color;
        this.windowX =(int)(Math.random()*windowX);
        this.windowY = (int)(Math.random()*windowY);
        this.side =(int) (Math.random()*windowX);
    }


    public Color getColor() {
        return color;
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public int getSide() {
        return side;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

    public void setSide(int side) {
        this.side = side;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(color);
        //g.drawRect(windowX,windowY,side ,side);
    }
}
