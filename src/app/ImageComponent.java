package app;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;


import app.base.AnchorPoint;
import app.Animation.AnimationMenu;
import app.global.KeyChecker;

public class ImageComponent extends JLabel
{
    public List<Box> hitboxes;
    public List<Box> hurtboxes;
    public AnchorPoint anchor;


    public BufferedImage texture;

    public String absolutePath;

    public String imgName;

    public JComponent currentCreating;

    ImageComponent(String name, BufferedImage image)
    {
        super(name);
        anchor = new AnchorPoint(this);
        hitboxes = new ArrayList<Box>();
        hurtboxes = new ArrayList<Box>();
        
        setSize(100, 115);
        this.setIcon(new ImageIcon(image.getScaledInstance(100, 115 , java.awt.Image.SCALE_DEFAULT)));
        texture = image;
        this.setName(name);
        imgName = name;
        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalTextPosition(JLabel.CENTER);
        setBackground(Color.GRAY);
        setIconTextGap(15);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));

        //add(anchor);
        
        ImageComponent comp = this;
        addMouseListener(new MouseAdapter() 
        {
			@Override
            public void mouseExited(MouseEvent e) 
            {
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
                if(!KeyChecker.IS_CONTROL_PRESSED)
                    ImportedView.setSelected(comp);
                else
                    AnimationMenu.addImageToAnimation(comp);
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

    public Box addHitbox()
    {
        Box hitbox = new Box(this.hitboxes, this);
        hitbox.boxBorderColor = new Color(255,0, 0, 160);
        hitbox.boxFillColor = new Color(200, 0, 0, 100);

        hitbox.selectedBoxBorderColor = new Color(255, 255, 0, 160);
        hitbox.selectedBoxFillColor = new Color(200, 0, 0, 100);

        this.hitboxes.add(hitbox);
        return hitbox;
    }

    public Box addHurtbox()
    {
        Box hurtbox = new Box(this.hurtboxes, this);
        hurtbox.boxBorderColor = new Color(0, 255, 0, 160);
        hurtbox.boxFillColor = new Color(0, 200, 0, 100);

        hurtbox.selectedBoxBorderColor = new Color(255, 255, 0, 160);
        hurtbox.selectedBoxFillColor = new Color(100, 200, 100, 100);

        this.hurtboxes.add(hurtbox);
        return hurtbox;
    }

    public Box getBox(int x, int y)
    {
        List<Box> boxes = new ArrayList<Box>();
        boxes.addAll(hurtboxes);
        boxes.addAll(hitboxes);
        for(int i = 0, len = boxes.size(); i < len; i++)
        {
            Box b = boxes.get(i);
            if(b.pointIntersection(x, y))
                return b;
        }
        return null;
    }

    public void setAnchor(float x, float y)
    {
        this.anchor.setAnchor(x, y);
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