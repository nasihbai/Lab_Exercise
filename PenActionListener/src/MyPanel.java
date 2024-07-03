import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawingProgram extends JFrame implements MouseMotionListener{
    
    Point mousePnt=new Point();

    public DrawingProgram() {

        //JFrame drawingProgram = new JFrame();
        //drawingProgram.add(...);
        
        super("Painter");
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(new Label("Drag mouse to draw"));
        this.add(toolbar, BorderLayout.SOUTH);
        
        
        
        JPanel canvas = new JPanel();     //aading canvas to jframe
        this.add(canvas,BorderLayout.CENTER);
        canvas.addMouseMotionListener(this);
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

public void mouseDragged(MouseEvent me){
 mousePnt = me.getPoint();
repaint();
}
public void mouseMoved(MouseEvent me){

}
public void paint(Graphics g){
g.fillOval(mousePnt.x, mousePnt.y,4,4);
}

    public static void main(String[] a) {
        new DrawingProgram();
    }
}
