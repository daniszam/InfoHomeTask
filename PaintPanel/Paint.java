package PaintPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Paint {

    //main frame
    private JFrame mainFrame;
    private Container mainPane;
    // buttons
    private JButton circle;
    private JButton rectangle;
    private JButton reflection;
    private List<JButton> colors;
    //paint panel
    private PainPanel paintPanel;
    //info panel
    private JPanel info;
    private Color color;
    private JButton mirror;
    private JColorChooser colorChooser;
    private JButton colorChooserButton;
    private JButton paintColor;
    private JButton shiftComponent;

    //
    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;

    public void run(){
        mainFrame = new JFrame("paint");
        mainPane = mainFrame.getContentPane();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //paint panel

        paintPanel = new PainPanel();
        paintPanel.setSize(300,300);
        mainPane.add(paintPanel,BorderLayout.CENTER);

        //info
        info = new JPanel();
        info.setLayout(new GridLayout(13,2));
        colors = new ArrayList<>();
        for (int i=0; i<20; i++){
            Color randomColor =  new Color(
                    (int)(Math.random()*255),
                    (int)(Math.random()*255),
                    (int)(Math.random()*255));
            JButton button = new JButton("color");
            button.setBackground(randomColor);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    color = button.getBackground();
                }
            });
            colors.add(button);
            info.add(button);
        }
        circle = new JButton("circle");
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CircleComponent circleComponent = new CircleComponent(color, paintPanel.getHeight(), paintPanel.getWidth());
                //circleComponent.paintComponent(paintPanel.getGraphics());
                paintPanel.getPaintComponents().add(circleComponent);
                paintPanel.repaint();
            }
        });
        rectangle = new JButton("rectangle");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RectangleComponent rectangleComponent = new RectangleComponent(color, paintPanel.getHeight(), paintPanel.getWidth());
                //rectangleComponent.paintComponent(paintPanel.getGraphics());
                paintPanel.getPaintComponents().add(rectangleComponent);
                paintPanel.repaint();
            }
        });
        mirror = new JButton("mirror");
        mirror.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.paintMirror();
            }
        });
        colorChooser = new JColorChooser();
        colorChooserButton = new JButton("change color");
        colorChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(mainFrame, "color", true);
                dialog.getContentPane().add(colorChooser);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        paintColor = new JButton("change background");
        paintColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.setBackground(colorChooser.getColor());
            }
        });
        shiftComponent = new JButton("shift bottom");
        shiftComponent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.shiftComponent();
            }
        });
        info.add(shiftComponent);
        info.add(mirror);
        info.add(circle);
        info.add(rectangle);
        info.add(colorChooserButton);
        info.add(paintColor);

        paintPanel.addMouseMotionListener(new MyMouseListener());
        paintPanel.addMouseListener(new MyMouseListener());
        mainPane.add(info, BorderLayout.EAST);

        mainFrame.setMinimumSize(new Dimension(300,300));
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Paint paint = new Paint();
        paint.run();
    }

    private class MyMouseListener implements MouseMotionListener, MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            oldX = e.getX();
            oldY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentX = e.getX();
            currentY = e.getY();

            paintPanel.getGraphics().drawLine(oldX, oldY, currentX, currentY);

            oldX = currentX;
            oldY = currentY;
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

}
