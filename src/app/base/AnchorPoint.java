package app.base;

import app.ImageComponent;
import app.global.Globals;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class AnchorPoint extends JComponent
{
    public static Image img = Globals.createImage("anchor.png", "Anchor Point");;
    public float x = .5f;
    public float y = .5f;
    private ImageComponent target;

    public AnchorPoint(ImageComponent target)
    {
        super();
        this.target = target;
    }

    public void setAnchor(float x, float y)
    {
        this.x = x;
        this.y = y;

        this.setBounds((int)(this.target.getX() + this.target.getWidth() * x), (int)(this.target.getY() + this.target.getHeight() * y), 40, 40);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 0,0, this);
    }

    @Override
    public Dimension getPreferredSize() 
    {
       return new Dimension(img.getWidth(this), img.getHeight(this));
    }
 
    @Override
    public Rectangle getBounds(Rectangle r)
    {
       super.getBounds(r);
       r.width = img.getWidth(this);
       r.height = img.getHeight(this);
       return r;  
    }
}