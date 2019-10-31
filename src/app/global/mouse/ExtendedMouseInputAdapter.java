package app.global.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public abstract class ExtendedMouseInputAdapter extends MouseInputAdapter implements MouseDrag
{
    private boolean isDragging = false;
    private MouseListener mMouseListener = null;
    private MouseMotionListener mMouseMotion = null;
    public ExtendedMouseInputAdapter(JComponent target) 
    {
        
        MouseListener adaptListener = this;
        MouseMotionListener adaptMotionListener = this;

        mMouseListener = new MouseListener() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                adaptListener.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                adaptListener.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if(isDragging)
                    onDragEnd(e);
                isDragging = false;
                adaptListener.mouseReleased(e);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                adaptListener.mouseEntered(e);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                adaptListener.mouseExited(e);

            }

        };
        mMouseMotion = new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                if(!isDragging)
                    onDragStart(e);
                isDragging = true;
                adaptMotionListener.mouseDragged(e);
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                adaptMotionListener.mouseMoved(e);

            }
            
        };

        target.addMouseListener(mMouseListener);   

        target.addMouseMotionListener(mMouseMotion);
        target.addMouseWheelListener(this);
    }
}