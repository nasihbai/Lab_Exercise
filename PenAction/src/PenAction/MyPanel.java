import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyPanel extends JPanel implements MouseMotionListener, MouseListener {
    Point oldPoint = new Point();
    Point newPoint = new Point();
    Image image;
    Graphics2D graphics2D;
    public static Color penColor = new Color(0, 0, 0);
    public static int pen = 4;

    MyPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        graphics2D.setStroke(new BasicStroke(pen));
        graphics2D.setColor(penColor);
        newPoint = me.getPoint();
        if (graphics2D != null)
            graphics2D.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
        repaint();
        oldPoint = newPoint;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        oldPoint = me.getPoint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            clear();
        } else {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (SwingUtilities.isRightMouseButton(me)) {
            penColor = JColorChooser.showDialog(null, "Change pen color", penColor);
            DrawingProgram.cColor.setBackground(penColor);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    public static void main(String[] args) {
        DrawingProgram drawingProgram = new DrawingProgram();
    }
}

class DrawingProgram {
    public static JPanel cColor = new JPanel();

    DrawingProgram() {
        JFrame frame = new JFrame("Drawing Program");
        MyPanel panel = new MyPanel();
        frame.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();

        // Button to change pen color
        JButton colorButton = new JButton("Change Pen Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyPanel.penColor = JColorChooser.showDialog(null, "Choose Pen Color", MyPanel.penColor);
                if (MyPanel.penColor != null) {
                    cColor.setBackground(MyPanel.penColor);
                }
            }
        });
        controlPanel.add(colorButton);

        // Slider to change pen size
        JLabel penSizeLabel = new JLabel("Pen Size:");
        JSlider penSizeSlider = new JSlider(1, 20, MyPanel.pen);
        penSizeSlider.addChangeListener(e -> MyPanel.pen = penSizeSlider.getValue());
        controlPanel.add(penSizeLabel);
        controlPanel.add(penSizeSlider);

        cColor.setBackground(MyPanel.penColor);
        controlPanel.add(cColor);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
