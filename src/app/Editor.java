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
                if(currentCreating.RECT_X != currentCreating.RECT_X_2 && currentCreating.RECT_Y != currentCreating.RECT_Y_2)
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
                currentCreating = new Box(e.getX(), e.getY(), 0, 0);
                editor.currentMode = MainWindow.currentMode;
                switch(editor.currentMode)
                {
                    case HITBOX:
                        currentCreating.boxBorderColor = new Color(255, 0, 0);
                        currentCreating.boxFillColor = new Color(255, 0, 0, 100);
                        editor.add(currentCreating);
                        break;
                    case HURTBOX:
                        currentCreating.boxBorderColor = new Color(0, 255, 0);
                        currentCreating.boxFillColor = new Color(0, 255, 0, 100);
                        editor.add(currentCreating);
                        break;
                    case ANCHOR:
                        editor.setAnchor(e.getX(), e.getY());
                        //Anchor Set Position
                        break;
                    case POINTER:
                        break;
                }
            }
        
            @Override
            public void mouseExited(MouseEvent e) 
            {
                if(currentCreating != null)
                {
                    editor.remove(currentCreating);
                    currentCreating = null;
                }
                
            }
        
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                
            }
        
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                
            }
        });

        addMouseMotionListener(new MouseMotionListener(){
        
            @Override
            public void mouseMoved(MouseEvent e) 
            {
               
            }
        
            @Override
            public void mouseDragged(MouseEvent e) {
                switch(editor.currentMode)
                {
                    case HITBOX:
                    case HURTBOX:
                        if(currentCreating != null)
                            currentCreating.setSizeByPoint(e.getX(), e.getY()); 
                        break;
                    case ANCHOR:
                        editor.setAnchor(e.getX(), e.getY());
                        break;
                    case POINTER:
                        break;
                }
            }
        });
    }

    public void setAnchor(int x, int y)
    {
        if(editingComponent != null)
        {
            editingComponent.anchorX = (float)editingComponent.texture.getWidth() / x;
            editingComponent.anchorY = (float)editingComponent.texture.getHeight() / y;
        }
    }

    public void setCurrentEditing(ImageComponent ic)
    {
        if(editingComponent != null)
        {
            for(Box hitbox : editingComponent.hitboxes)
                remove(hitbox);
            for(Box hurtbox : editingComponent.hurtboxes)
                remove(hurtbox);
          //  remove(editingComponent.texture);
        }

        for(Box hitbox : ic.hitboxes)
            add(hitbox);
        for(Box hurtbox : ic.hurtboxes)
            add(hurtbox);
        //add(editingComponent.texture);

        editingComponent = ic;
    }
}