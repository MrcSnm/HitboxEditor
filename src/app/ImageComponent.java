package app;

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

    ImageComponent(File file)
    {
        super();
        hitboxes = new ArrayList<Box>();
        hurtboxes = new ArrayList<Box>();

        try
        {
            read = ImageIO.read(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            App.handleError();
        }
        this.setIcon(new ImageIcon(read));
        this.setName(file.getName());
    }

    public void addHitbox(Box b)
    {
        this.hitboxes.add(b);
    }

    public void addHurtbox(Box b)
    {
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