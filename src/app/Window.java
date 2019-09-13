package app;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

class Window extends JPanel
{
    public Dimension dimension;
    private JButton importImages;
    private JButton hitBox;
    private JButton hurtBox;
    private JButton anchorPoint;
    private JButton pointer;
    private JScrollPane scroll;

    public static enum MODE
    {
        POINTER,
        ANCHOR,
        HITBOX,
        HURTBOX 
    }


    public MODE currentMode;

    Window(JFrame frame)
    {
        super(true);
        frame.add(this);
        setVisible(true);

        importImages = new JButton("Import Images");
        importImages.setSize(100, 50);
        importImages.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(CrossPlatformFunctions.crossPlatformSelectMulti("Select images to import", "png"));
            }
        });

        scroll = new JScrollPane();

        hitBox = new JButton("HITBOX");
        //hitBox
        hitBox.setSize(50, 50);
        hurtBox = new JButton("HURTBOX");
        hurtBox.setSize(50, 50);
        anchorPoint = new JButton("ANCHOR");
        anchorPoint.setSize(50, 50);
        pointer = new JButton("POINTER");
        pointer.setSize(50, 50);

        add(importImages);
        
        add(hurtBox);
        add(hitBox);
        add(anchorPoint);
        add(pointer);
        add(scroll);

    }

    public void drawOptions()
    {
        ImageComponent img = ImportedView.currentSelected;
        

    }

}