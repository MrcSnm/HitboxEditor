package app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class AnimationFrame extends JComponent
{
    public BufferedImage image;
    public int x;
    public int y;
    public float anchorX = .5f;
    public float anchorY = .5f;
    public int width;
    public int height;

    AnimationFrame(BufferedImage image)
    {
        super();
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void setAnchor(float x, float y)
    {
        anchorX = x;
        anchorY = y;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.image, (int)(x + anchorX * width), (int)(y + anchorY * height), null);

    }
}