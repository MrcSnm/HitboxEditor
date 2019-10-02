package app;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImageView extends JPanel
{
    public final int BORDER_THICKNESS = 5;
    public JLabel image;
    private Icon ic;
    private int leftD = 0;
    private int rightD = 0;
    private int topD = 0;
    private int botD = 0;
    public ImageView()
    {
        super(null);
        setBorder(new LineBorder(new Color(180, 180 , 180, 255), BORDER_THICKNESS));
        setBackground(null);
        image = new JLabel();
        image.setAlignmentX(JLabel.CENTER);
        add(image);
    }

    public void setIcon(Icon icon)
    {
        image.setIcon(icon);
        ic = image.getIcon();
    }

    public Icon getIcon()
    {
        return image.getIcon();
    }

    public void setImageDistanceFromBorder(int left, int top, int right, int bot)
    {
        leftD = left;
        rightD = right;
        topD = top;
        botD = bot;
    }

    public void setImageDistanceFromBorder(int every)
    {
        setImageDistanceFromBorder(every, every, every, every);
    }


    @Override
    public void setBounds(int x, int y, int w, int h)
    {
        if(ic != null)
        {
            super.setBounds(x - leftD, y - topD, x + rightD, h + botD);
        }
        else
            super.setBounds(x, y, w, h);
    }
}