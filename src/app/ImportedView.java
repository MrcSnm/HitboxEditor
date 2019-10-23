package app;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import app.file.Loader;
import app.global.UIDefaults;

public class ImportedView extends JScrollPane 
{
    public Editor editorRef;
    public Map<String, ImageComponent> images;
    public JPanel panel;
    public JDialog dialog;
    public static ImageComponent currentSelected = null;
    private static ImportedView ref = null;
    public ImageProgress imageProgress;
    JProgressBar j;

    private List<List<Box>> scheduledHitboxes = new ArrayList<List<Box>>();
    private List<List<Box>> scheduledHurtboxes = new ArrayList<List<Box>>();
    private List<File> scheduledFiles = new ArrayList<File>();
    private List<AtomicReference<Float>> scheduledPivotX = new ArrayList<AtomicReference<Float>>();
    private List<AtomicReference<Float>> scheduledPivotY = new ArrayList<AtomicReference<Float>>();

    public static void setSelected(ImageComponent img) 
    {
        if (img == currentSelected)
            return;
        if (currentSelected != null)
            unselect();
        if (img == null)
            return;
        currentSelected = img;
        if (ref != null && ref.editorRef != null)
            ref.editorRef.setCurrentEditing(img);
        img.setOpaque(true);
        img.setForeground(Color.CYAN);
        img.repaint();
    }

    private static void unselect() 
    {
        currentSelected.setForeground(Color.WHITE);
        currentSelected.setOpaque(false);
        currentSelected.repaint();
        currentSelected = null;
    }

    public void scheduleCreation(List<Box> hitboxes, List<Box> hurtboxes, AtomicReference<Float> scheduledPX, AtomicReference<Float> scheludedPY, String imageName) 
    {
        scheduledHitboxes.add(hitboxes);
        scheduledHurtboxes.add(hurtboxes);
        scheduledPivotX.add(scheduledPX);
        scheduledPivotY.add(scheludedPY);
        try {scheduledFiles.add(new File(imageName));}
        catch (Exception e) {e.printStackTrace();}
    }

    public void executeScheduled() 
    {
        File[] files = scheduledFiles.toArray(new File[scheduledFiles.size()]);
        scheduledFiles.clear();
        addImportedImages(files);
    }

    public void tryLoadOperation(String path) 
    {
        Loader.loadProject(Paths.get(path).toString(), this);
    }

    public ImportedView(Editor editor) 
    {
        super();
        if (ref == null)
            ref = this;
        editorRef = editor;
        images = new HashMap<String, ImageComponent>();
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
        panel = new JPanel(true);
        panel.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
        

        dialog = new JDialog((JDialog) null, "Load");
        dialog.setBackground(UIDefaults.DARKER_GRAY);
        dialog.setForeground(Color.DARK_GRAY);
        dialog.setSize(400, 100);
        j = new JProgressBar();
        j.setBackground(Color.DARK_GRAY);
        j.setForeground(UIDefaults.DARKER_GRAY);
        dialog.add(j);
        j.setMaximum(100);
        j.setMinimum(0);
        j.setStringPainted(true);
        setViewportBorder(new LineBorder(new Color(0x1e1e1e)));
    	setViewportView(panel);
    	validate();

        
        imageProgress = new ImageProgress(j);
        ImportedView imp = this;
        imageProgress.setOnCompleteHandler(new Callable<String>()
        {
            @Override
            public String call()
            {
                String name = ImageProgress.currentScheduledFile.getName();
                if(images.get(name) != null)
                {
                    panel.remove(images.get(name));
                }
                ImageComponent im = new ImageComponent(name, imageProgress.imageRead);
                im.absolutePath = ImageProgress.currentScheduledFile.getAbsolutePath();

                if(ref.scheduledHitboxes.size() > 0)
                {
                    List<Box> imHitboxes = ref.scheduledHitboxes.get(0);
                    for(int i = 0, len = imHitboxes.size(); i < len; i++)
                    {
                        Box hitbox = im.addHitbox();
                        imHitboxes.get(i).copyInto(hitbox);
                    }
    
                    List<Box> imHurtboxes = ref.scheduledHurtboxes.get(0);
                    for(int i = 0, len = imHurtboxes.size(); i < len; i++)
                    {
                        Box hurtbox = im.addHurtbox();
                        imHurtboxes.get(i).copyInto(hurtbox);
                    }
                    im.setAnchor(scheduledPivotX.get(0).get(), scheduledPivotY.get(0).get());
                    ref.scheduledHurtboxes.remove(0);
                    ref.scheduledHitboxes.remove(0);
                    ref.scheduledPivotX.remove(0);
                    ref.scheduledPivotY.remove(0);
                }
                images.put(name,im);
                panel.add(im, null);
                panel.validate();
                imp.validate();
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
        imageProgress.setImagesToRead(f);
        try 
        {
            imageProgress.readImages();
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(null);
            
        }
        catch (IOException e)
        {
			e.printStackTrace();
		}
    }
}