package PaintPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainPanel extends JPanel {
    private List<PaintComponent> paintComponents = new ArrayList<>();


    public List<PaintComponent> getPaintComponents() {
        return paintComponents;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PaintComponent paintComponent: paintComponents){
            g.setColor(paintComponent.getColor());
            paintComponent.paintComponent(g);
//            g.drawOval(paintComponent.getWindowX(), paintComponent.getWindowY(),
//                    paintComponent.getSide(), paintComponent.getSide());
        }

    }

    protected void paintMirror(){
        int middle = this.getWidth()/2;
        this.getGraphics().drawLine(middle, 0, middle, this.getHeight());
        for (PaintComponent paintComponent: paintComponents){
                int position = paintComponent.getWindowX()+paintComponent.getSide() - middle;
                position = middle - position;
                paintComponent.setWindowX(position);
                paintComponent.paintComponent(this.getGraphics());
        }
    }

    protected void shiftComponent(){
        for (PaintComponent paintComponent: paintComponents){
            paintComponent.setWindowY(paintComponent.getWindowY()+paintComponent.getSide());
        }
        repaint();
    }
}
