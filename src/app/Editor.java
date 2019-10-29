package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import app.base.AnchorPoint;
import app.base.Inspector;

public class Editor extends JScrollPane
{

    public Box selectedBox = null;
    public Box currentCreating;
    public List<ImageComponent> editedComponents = new ArrayList<ImageComponent>();
    public ImageComponent editingComponent;
    public JPanel panel;
    public MainWindow.MODE currentMode = MainWindow.MODE.POINTER;
    public JButton imageView;

    private Inspector inspector;

    public Editor(Inspector inspector)
    {
        super();
        this.inspector = inspector;
        panel = new JPanel(true);
        imageView = new JButton();
        imageView.setEnabled(false);
        imageView.setLayout(null);
        imageView.setBackground(null);
        imageView.setForeground(null);
        imageView.setBorder(new LineBorder(new Color(180, 180, 180, 255), 5));
        //panel.setLayout(new BorderLayout());
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1000, 300));
        panel.add(imageView);
        //imageView.setPreferredSize(new Dimension(500, 500));
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        setViewportView(panel);
        

        final Editor editor = this;

        imageView.addMouseListener(new MouseListener()
        {
        
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
		                        imageView.add(currentCreating);
		                        editor.getViewport().validate();
		                        break;
		                    case HURTBOX:
		                    	currentCreating = editingComponent.addHurtbox();
                                currentCreating.setStartPoint(e.getX(), e.getY());
		                        imageView.add(currentCreating);
		                        editor.getViewport().validate();
		                        break;
                            case ANCHOR:
                                float anchorX = e.getX() / editingComponent.texture.getWidth();
                                float anchorY = e.getY() / editingComponent.texture.getHeight();
                                editingComponent.setAnchor(anchorX, anchorY);
                                // System.out.println("AnchorX: " + anchorX + " AnchorY: " + anchorY);
                                System.out.println("X: "+ normallizeX(e.getX()) + " Y: " + normallizeY(e.getY()));
		                        //Anchor Set Position
		                        break;
                            case POINTER: 
                                Box b = editingComponent.getBox(e.getX(), e.getY());
                                if(b != null)
                                    setSelectedBox(b);
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
            							imageView.remove(b);
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
            							imageView.remove(b);
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

        imageView.addMouseMotionListener(new MouseMotionListener()
        {
        
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
                            currentCreating.revalidate();	
                        }
                        break;
                    case ANCHOR:
                        editor.setAnchor(e.getX(), e.getY());
                        break;
                    case POINTER:
                        moveSelectedBox(e.getX(), e.getY());
                        break;  
                }
            }
        });
    }

    private void updateBounds()
    {
        Icon ic = imageView.getIcon();
        int wid = ic.getIconWidth();
        int hei = ic.getIconHeight();

        imageView.setBounds(panel.getWidth() / 2 - wid / 2, 0, wid * 2, hei * 2);

        imageView.validate();
        panel.setPreferredSize(new Dimension(wid * 2, hei* 2));
        panel.validate();
    }

    public void setAnchor(int x, int y)
    {
        if(editingComponent != null)
        {
            editingComponent.setAnchor((float)editingComponent.texture.getWidth() / x, (float)editingComponent.texture.getHeight() / y);
        }
    }

    public void setCurrentEditing(ImageComponent ic)
    {
        if(editingComponent != null)
        {
            for(Box hitbox : editingComponent.hitboxes)
            {
                hitbox.normallize(this);
                System.out.println(hitbox);
                imageView.remove(hitbox);
            }
            for(Box hurtbox : editingComponent.hurtboxes)
            {
                hurtbox.normallize(this);
                System.out.println(hurtbox);
                imageView.remove(hurtbox);
            }
            imageView.setIcon(null);
            imageView.setDisabledIcon(null);
            imageView.remove(ic.anchor);
        }
        editingComponent = ic;


        imageView.setIcon(new ImageIcon(editingComponent.texture));
        imageView.setDisabledIcon(new ImageIcon(editingComponent.texture));
        updateBounds();
        panel.setPreferredSize(new Dimension(ic.texture.getWidth() + 400, ic.texture.getHeight() + 200));
        getViewport().validate();

        for(Box hitbox : ic.hitboxes)
        {
            hitbox.unNormallize(this);
            imageView.add(hitbox);
            getViewport().validate();
        }
        for(Box hurtbox : ic.hurtboxes)
        {
            hurtbox.unNormallize(this);
            imageView.add(hurtbox);
            getViewport().validate();
        }
        imageView.add(ic.anchor);
        getViewport().validate();
        
        //System.out.println(AnchorPoint.img);
    }

    public int normallizeX(int X)
    {
        return X -(imageView.getWidth() - editingComponent.texture.getWidth()) / 2;
    }

    public int unNormallizeX(int X)
    {
        return X + (imageView.getWidth() - editingComponent.texture.getWidth()) / 2;
    }

    public int normallizeY(int Y)
    {
        return Y - (imageView.getHeight() - editingComponent.texture.getHeight()) / 2;
    }

    public int unNormallizeY(int Y)
    {
        return Y + (imageView.getHeight() - editingComponent.texture.getHeight()) / 2;
    }

    public void setSelectedBox(Box b)
    {
        if(selectedBox != null)
            selectedBox.setSelected(false);
        selectedBox = b.setSelected(true);
        inspector.setTarget(b);
    }

    public void moveSelectedBox(int x, int y)
    {
        if(selectedBox != null)
        {
            Box b = selectedBox;
            int lastX, lastY;
            lastX = x - (b.RECT_X_2 - b.RECT_X) / 2;
            lastY = y - (b.RECT_Y_2 - b.RECT_Y) / 2;
            b.RECT_X_2 = lastX + (b.RECT_X_2 - b.RECT_X);
            b.RECT_X = lastX;
      
            b.RECT_Y_2 = lastY + (b.RECT_Y_2 - b.RECT_Y);
            b.RECT_Y = lastY;
            b.postSetOperation();
        }
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
                    if(!hitbox.canSave())
                    {
                        hitbox.toggleNormallize(this);
                        saveString+= "\n\t\t\t\t" + hitbox.getRectAsJSON();
                        hitbox.toggleNormallize(this);
                    }
                    else
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
                    if(!hurtbox.canSave())
                    {
                        hurtbox.toggleNormallize(this);
                        saveString+= "\n\t\t\t\t" + hurtbox.getRectAsJSON();
                        hurtbox.toggleNormallize(this);
                    }
                    else
                        saveString+= "\n\t\t\t\t" + hurtbox.getRectAsJSON();
                }
                saveString+= "\n\t\t\t]";
            }
            saveString+= "\n\t\t\"pivot\" : {\"x\" : " + edited.anchor.x + ", \"y\" : " + edited.anchor.y + "}";
        }

        saveString+= "\n}";
    }
}