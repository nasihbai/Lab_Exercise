/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nasih
 */
// Interface for rotation functionality
public interface Rotatable {
    void rotate(double angle);
}

// ImagePanel class to handle different types of images
public class ImagePanel extends JPanel implements Rotatable, MouseListener, MouseMotionListener {
    private BufferedImage image;
    private double rotationAngle;

    public ImagePanel(BufferedImage img) {
        this.image = img;
        this.rotationAngle = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(rotationAngle), image.getWidth() / 2, image.getHeight() / 2);
        g2d.drawImage(image, 0, 0, this);
    }

    @Override
    public void rotate(double angle) {
        this.rotationAngle += angle;
        repaint();
    }

    // Implement MouseListener and MouseMotionListener methods for interaction
    // ...
}

// CustomDrawingPanel class for freehand drawing
public class CustomDrawingPanel extends JPanel {
    // Existing drawing functionalities from lab 5
    // Add save functionality
    public void saveDrawing(File file) {
        try {
            ImageIO.write(getBufferedImage(), "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getBufferedImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        paint(image.getGraphics());
        return image;
    }
}

// Toolbar class for adding toolbar functionalities
public class Toolbar extends JToolBar {
    public Toolbar(CustomDrawingPanel drawingPanel, ImagePanel leftCanvas) {
        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            drawingPanel.setPenColor(newColor);
        });

        JButton penSizeButton = new JButton("Pen Size");
        penSizeButton.addActionListener(e -> {
            String sizeStr = JOptionPane.showInputDialog("Enter pen size:");
            int newSize = Integer.parseInt(sizeStr);
            drawingPanel.setPenSize(newSize);
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                drawingPanel.saveDrawing(file);
            }
        });

        // Add more buttons for including images, rotating, etc.
        // ...

        add(colorButton);
        add(penSizeButton);
        add(saveButton);
        // Add more buttons to toolbar
    }
}

// Main Application class to tie everything together
public class DrawingApplication extends JFrame {
    private CustomDrawingPanel drawingPanel;
    private ImagePanel leftCanvas;

    public DrawingApplication() {
        drawingPanel = new CustomDrawingPanel();
        leftCanvas = new ImagePanel();

        Toolbar toolbar = new Toolbar(drawingPanel, leftCanvas);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCanvas, drawingPanel);
        add(splitPane, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);

        setTitle("Drawing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingApplication().setVisible(true);
        });
    }
}
