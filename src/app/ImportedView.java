package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ImportedView extends JScrollPane
{
    public Map<String, ImageComponent> images;
    public JPanel panel;
    public JDialog dialog;
    public static ImageComponent currentSelected;
    public ImageProgress imageProgress;
    JProgressBar j;
    
    public ImportedView()
    {
        super();
        images = new HashMap<String, ImageComponent>();
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        panel = new JPanel(true);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
        panel.setPreferredSize(new Dimension(500, 1000));

        dialog = new JDialog((JDialog)null, "Load");
        dialog.setBackground(MainWindow.darkerGray);
        dialog.setForeground(Color.DARK_GRAY);
        dialog.setSize(400, 100);
        j = new JProgressBar();
        j.setBackground(Color.DARK_GRAY);
        j.setForeground(MainWindow.darkerGray);
        dialog.add(j);
        j.setMaximum(100);
        j.setMinimum(0);
        j.setStringPainted(true);
        getViewport().add(panel);

        imageProgress = new ImageProgress(j);
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
                panel.validate();
                return name;
            }
        });
        imageProgress.setOnProgressHandler(new Callable<Object>()
        {
            @Override
            public Object call()
            {
                dialog.setTitle("Loading " + ImageProgress.currentScheduledFile.getName());
                j.setString("Loading: " + ImageProgress.currentScheduledFile.getName() + "(" + String.format("%.2f", ImageProgress.currentProgress * 100) + "%)");
                return null;
            }
        });
        imageProgress.setOnProcessCompleteHandler(new Callable<Object>()
        {
            @Override
            public Object call()
            {
                dialog.setVisible(false);
                panel.validate();
                return null;
            }
        });

        dialog.addWindowListener(new WindowAdapter() 
        {
          public void windowClosed(WindowEvent e)
          {
            imageProgress.forceStop();
          }
          //MACOSX 
          public void windowClosing(WindowEvent e)
          {
            this.windowClosed(e);
          }
        });
    }

    public void addImportedImages(File[] f)
    {
        ImageComponent im;
        String name;
        
        imageProgress.setImagesToRead(f);
        try 
        {
            imageProgress.readImages();
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(null);
            
        }
         catch (IOException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}