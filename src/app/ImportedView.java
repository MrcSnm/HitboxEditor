package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

class ImportedView extends JScrollPane
{
    public Map<String, ImageComponent> images;
    public JPanel panel;
    public static ImageComponent currentSelected;
    
    ImportedView()
    {
        super();
        images = new HashMap<String, ImageComponent>();
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        panel = new JPanel(true);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
        panel.setPreferredSize(new Dimension(500, 1000));
        getViewport().add(panel);
        
    }

    public void addImportedImages(File[] f)
    {
        ImageComponent im;
        String name;
        for(int i = 0, len = f.length; i < len; i++)
        {
            name = f[i].getName();
            if(images.get(name) != null)
            {
            	panel.remove(images.get(name));
            }
            im = new ImageComponent(f[i]);
            images.put(name,im);
            panel.add(im, null);
        }
    }
}