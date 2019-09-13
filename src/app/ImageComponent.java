package app;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageComponent extends JLabel
{
    private BufferedImage read;
    public List<Box> hitboxes;
    public List<Box> hurtboxes;
    public float anchorX = .5f;
    public float anchorY = .5f;

    public JComponent currentCreating;

    ImageComponent(File file)
    {
        super(file.getName());
        hitboxes = new ArrayList<Box>();
        hurtboxes = new ArrayList<Box>();
        try
        {
            read = ImageIO.read(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            MainWindow.handleError();
        }
        setSize(100, 115);
        this.setIcon(new ImageIcon(read.getScaledInstance(100, 115 , java.awt.Image.SCALE_DEFAULT)));
        this.setName(file.getName());
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
        setIconTextGap(15);
        setBorder(BorderFactory.createLineBorder(Color.black));
        
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