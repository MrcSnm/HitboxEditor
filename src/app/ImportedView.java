package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ImportedView extends JScrollPane
{
    public Map<String, ImageComponent> images;
    public JPanel panel;
    public static ImageComponent currentSelected;
    public ImageProgress imageProgress;
    JProgressBar j;
    private Thread updater;
    
    public ImportedView()
    {
        super();
        images = new HashMap<String, ImageComponent>();
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        panel = new JPanel(true);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
        panel.setPreferredSize(new Dimension(500, 1000));
        j = new JProgressBar();
        j.setMaximum(100);
        j.setMinimum(0);
        panel.add(j);
        getViewport().add(panel);

        imageProgress = new ImageProgress(j);
        final ImportedView view = this;
        imageProgress.setOnCompleteHandler(new Callable<String>()
        {
            @Override
            public String call()
            {
                System.out.println(ImageProgress.currentScheduledFile.getName());
                String name = ImageProgress.currentScheduledFile.getName();
                if(images.get(name) != null)
                {
                    panel.remove(images.get(name));
                }
                ImageComponent im = new ImageComponent(name, imageProgress.imageRead);

                images.put(name,im);
                panel.add(im, null);
                view.repaint();
                return name;
            }
        });
    }

    public void addImportedImages(File[] f)
    {
        ImageComponent im;
        String name;
        
        imageProgress.setImagesToRead(f);
        try {
			imageProgress.readImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}