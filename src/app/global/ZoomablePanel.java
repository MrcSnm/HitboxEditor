package app.global;

import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class ZoomablePanel extends JPanel 
{
    public float currentZoom = 1.0f;
    private float scrollAmount = .05f;

    public ZoomablePanel() 
    {
        addMouseWheelListener(new MouseWheelListener() 
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) 
            {
                if (!KeyChecker.IS_CONTROL_PRESSED)
                    return;
                if(e.getWheelRotation() > 0)
                {
                	currentZoom+= scrollAmount;
                }
                else
                {
                	if(currentZoom - scrollAmount > 0)
                		currentZoom-= scrollAmount;
                }
                revalidate();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponents(g);

    }
}