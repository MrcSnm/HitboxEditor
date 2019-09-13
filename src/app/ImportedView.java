package app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

class ImportedView extends JScrollPane
{
    public Map<String, ImageComponent> images;
    public static ImageComponent currentSelected;
    
    ImportedView()
    {
        super();
        images = new HashMap<String, ImageComponent>();
    }

    public void addImportedImages(File[] f)
    {
        ImageComponent im;
        String name;
        for(int i = 0, len = f.length; i < len; i++)
        {
            name = f[i].getName();
            if(images.get(name) != null)
                remove(images.get(name));
            im = new ImageComponent(f[i]);
            images.put(name,im);
            add(im);
        }
    }
}