package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Editor extends JScrollPane
{
    public Box currentCreating;
    public List<ImageComponent> editedComponents = new ArrayList<ImageComponent>();
    public ImageComponent editingComponent;
    public JPanel panel;
    public MainWindow.MODE currentMode = MainWindow.MODE.POINTER;
    private JLabel imageView;

    public Editor()
    {
        super();
        panel = new JPanel(true);
        imageView = new JLabel();
        imageView.setBorder(new LineBorder(new Color(255, 255, 255, 150), 5));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(1000, 300));
        panel.add(imageView);
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        setViewportView(panel);
        

        final Editor editor = this;


        addMouseListener(new MouseListener(){
        
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if(currentCreating != null)
                {
                    if(currentCreating.RECT_X != currentCreating.RECT_X_2 && currentCreating.RECT_Y != currentCreating.RECT_Y_2)
                    {
                        switch(currentMode)
                        {
                            case HITBOX:
                                break;
                            case HURTBOX:
                                break;
                            default:
                                break;
                        }
                    }
                    currentCreating = null;
                }
            }
        
            @Override
            public void mousePressed(MouseEvent e) 
            {
                editor.currentMode = MainWindow.currentMode;
            	
            	switch(e.getButton())
            	{
            		case MouseEvent.BUTTON1:
		            	switch(editor.currentMode)
		                {
		                    case HITBOX:
		                    	currentCreating = editingComponent.addHitbox();
		                        currentCreating.setStartPoint(e.getX(), e.getY());
		                        panel.add(currentCreating);
		                        editor.getViewport().validate();
		                        break;
		                    case HURTBOX:
		                    	currentCreating = editingComponent.addHurtbox();
		                        currentCreating.setStartPoint(e.getX(), e.getY());
		                        panel.add(currentCreating);
		                        editor.getViewport().validate();
		                        break;
		                    case ANCHOR:
		                        editor.setAnchor(e.getX(), e.getY());
		                        //Anchor Set Position
		                        break;
		                    case POINTER:
		                        break;
		                }
		            	break;
            		case MouseEvent.BUTTON3:
            			switch(editor.currentMode)
            			{
            				case HITBOX:
            					for(Box b : editingComponent.hitboxes)
            					{
            						if(b.pointIntersection(e.getX(), e.getY()))
            						{
            							b.remove();
            							panel.remove(b);
            							editor.revalidate();
            							currentCreating = null;
            							break;
            						}
            					}
            					break;
            				case HURTBOX:
            					for(Box b : editingComponent.hurtboxes)
            					{
            						if(b.pointIntersection(e.getX(), e.getY()))
            						{
            							b.remove();
            							panel.remove(b);
            							editor.revalidate();
            							currentCreating = null;
            							break;
            						}
            					}
            					break;
        					default:
        						break;
            			}
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
                        {
                        	currentCreating.setSizeByPoint(e.getX(), e.getY()); 
                            currentCreating.repaint();	
                        }
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
                panel.remove(hitbox);
            for(Box hurtbox : editingComponent.hurtboxes)
                panel.remove(hurtbox);
            imageView.setIcon(null);
        }
        editingComponent = ic;

        for(Box hitbox : ic.hitboxes)
            panel.add(hitbox);
        for(Box hurtbox : ic.hurtboxes)
            panel.add(hurtbox);
        imageView.setIcon(new ImageIcon(editingComponent.texture));
        panel.setPreferredSize(new Dimension(ic.texture.getWidth() + 400, ic.texture.getHeight() + 200));

        getViewport().validate();
    }


    public void saveEdited()
    {

        Collections.sort(editedComponents, new Comparator<ImageComponent>()
        {
            @Override
            public int compare(ImageComponent i1, ImageComponent i2)
            {
                return i1.imgName.compareTo(i2.imgName);
            }
        });

        String saveString = "{\n";

        for(ImageComponent edited : editedComponents)
        {
            saveString+= "\t";
            saveString+= edited.imgName + " : " + "\n";
            saveString+= "\t{\n" + "\t\t";
            if(edited.hitboxes.size() > 0)
            {
                saveString+= "\"hitboxes\" : \n\t\t\t[";
                for(Box hitbox : edited.hitboxes)
                {
                    saveString+= "\n\t\t\t\t" + hitbox.getRectAsJSON();
                }
                saveString+= "\n\t\t\t]";
            }
            if(edited.hurtboxes.size() > 0)
            {
                if(edited.hitboxes.size() > 0)
                    saveString+= "\t\t";
                saveString+= "\"hurtboxes\" : \n\t\t\t[";
                for(Box hurtbox : edited.hurtboxes)
                {
                    saveString+= "\n\t\t\t\t" + hurtbox.getRectAsJSON();
                }
                saveString+= "\n\t\t\t]";
            }
            saveString+= "\n\t\t\"pivot\" : {\"x\" : " + edited.anchorX + ", \"y\" : " + edited.anchorY + "}";
        }

        saveString+= "\n}";
    }
}