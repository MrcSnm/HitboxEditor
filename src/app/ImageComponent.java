package app;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ImageComponent extends JLabel
{
    public List<Box> hitboxes;
    public List<Box> hurtboxes;
    public float anchorX = .5f;
    public float anchorY = .5f;

    public JComponent currentCreating;

    ImageComponent(String name, BufferedImage image)
    {
        super(name);
        hitboxes = new ArrayList<Box>();
        hurtboxes = new ArrayList<Box>();
        
        setSize(100, 115);
        this.setIcon(new ImageIcon(image.getScaledInstance(100, 115 , java.awt.Image.SCALE_DEFAULT)));
        this.setName(name);
        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalTextPosition(JLabel.CENTER);
        setBackground(Color.GRAY);
        setIconTextGap(15);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        ImageComponent comp = this;
        addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(ImportedView.currentSelected != comp)
				{
					comp.setOpaque(false);
					comp.setBackground(Color.LIGHT_GRAY);
					comp.setForeground(Color.WHITE);
					comp.repaint();
				}				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				if(ImportedView.currentSelected != comp)
				{
					comp.setOpaque(true);
					comp.setBackground(Color.GRAY);
					comp.repaint();	
				}
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				ImportedView.setSelected(comp);
				
			}
		});
        
        
    }

    public void addCreation()
    {
        switch(MainWindow.currentMode)
        {
            case HITBOX:
                break;
            case HURTBOX:
                break;
            case ANCHOR:
                break;
            case POINTER:
                break;
            default:
                break;

        }
    }

    public void removeCreation(Box b)
    {
        switch(MainWindow.currentMode)
        {
            case HITBOX:
                this.deleteHurtbox(b);
                break;
            case HURTBOX:
                this.deleteHurtbox(b);
                break;
            case ANCHOR:
                this.setAnchor(.5f, .5f);
                break;
            case POINTER:
                break;
            default:
                break;

        }
    }

    public void addHitbox(Box b)
    {
        Box hitbox = new Box(0, 0, 0, 0);
        hitbox.boxBorderColor = new Color(255,0, 0, 160);
        hitbox.boxFillColor = new Color(200, 0, 0, 100);
        this.hitboxes.add(b);
    }

    public void addHurtbox(Box b)
    {
        Box hurtbox = new Box(0, 0, 0, 0);
        hurtbox.boxBorderColor = new Color(0, 255, 0, 160);
        hurtbox.boxFillColor = new Color(0, 200, 0, 100);
        this.hurtboxes.add(b);
    }

    public void setAnchor(float x, float y)
    {
        anchorX = x;
        anchorY = y;
    }

    public void deleteHitbox(Box b)
    {
        this.hitboxes.remove(b);
    }

    public void deleteHurtbox(Box b)
    {
        this.hurtboxes.remove(b);
    }
}