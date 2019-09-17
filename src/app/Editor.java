package app;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Editor extends JScrollPane
{
    private Box currentCreating;
    private ImageComponent editingComponent;
    private JPanel panel;
    private MainWindow.MODE currentMode = MainWindow.MODE.POINTER;

    public Editor()
    {
        super();
        panel = new JPanel(true);
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        add(panel);

        final Editor editor = this;
        addMouseListener(new MouseListener(){
        
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if(currentCreating.RECT_W != 0 && currentCreating.RECT_H != 0)
                {
                    switch(currentMode)
                    {
                        case HITBOX:
                            editingComponent.hitboxes.add(currentCreating);
                            break;
                        case HURTBOX:
                            editingComponent.hurtboxes.add(currentCreating);
                            break;
                        default:
                            break;
                    }
                }
                currentCreating = null;
            }
        
            @Override
            public void mousePressed(MouseEvent e) 
            {
            }
        
            @Override
            public void mouseExited(MouseEvent e) 
            {
                currentCreating = null;
                
            }
        
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                
            }
        
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                currentCreating = new Box(e.getX(), e.getY(), 0, 0);
                editor.currentMode = MainWindow.currentMode;
                switch(editor.currentMode)
                {
                    case HITBOX:
                        currentCreating.boxBorderColor = new Color(255, 0, 0);
                        currentCreating.boxFillColor = new Color(255, 0, 0, 100);
                        break;
                    case HURTBOX:
                        currentCreating.boxBorderColor = new Color(0, 255, 0);
                        currentCreating.boxFillColor = new Color(0, 255, 0, 100);
                        break;
                    case ANCHOR:
                        //Anchor Set Position
                        break;
                    case POINTER:
                        break;
                }
            }
        });

        addMouseMotionListener(new MouseMotionListener(){
        
            @Override
            public void mouseMoved(MouseEvent e) 
            {
               currentCreating.RECT_W = e.getX();
               currentCreating.RECT_H = e.getY(); 
            }
        
            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });

    
        
    }

    public void setCurrentEditing(ImageComponent ic)
    {

    }
}