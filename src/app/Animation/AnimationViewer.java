package app.Animation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimationViewer extends JPanel
{

    private int speed = 32;
    private volatile int frameCounter = 0;
    private volatile int currentFrame = 0;
    public String currentAnimationName;


    public static AnimationViewer instance;
    private AnimationItem item;

    private JLabel currentImageName;

    private AnimationFrame activeAnimationFrame = null;
    private List<AnimationFrame> animationFrames = new ArrayList<AnimationFrame>();

    private static boolean scheduledUpdate = false;
    private static String scheduledName = "";
    private static List<BufferedImage> scheduledBufferedImages = new ArrayList<BufferedImage>();


    private AnimationViewer()
    {
        super(true);
        currentImageName = new JLabel("Animation ");
        currentImageName.setForeground(Color.WHITE);
        add(currentImageName);

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    if(canUpdate())
                        updateFrame();
                    if(scheduledUpdate)
                        setAnimationFrames(scheduledBufferedImages, scheduledName);
                        
                }
            }
        });
        t.start();
    }

    public boolean canUpdate()
    {
        return(instance.animationFrames.size() != 0);
    }

    public static void startAnimationView()
    {
        if(instance == null)
            instance = new AnimationViewer();
    }

    public static void setAnimation(AnimationItem item)
    {
        instance.item = item;
        setAnimationFramesSchedule(item.getAnimationFrames(), item.animationName);
    }

    private void changeFrame(int frameNumber)
    {
        if(activeAnimationFrame != null)
            remove(activeAnimationFrame);
 
        activeAnimationFrame = animationFrames.get(frameNumber);
        add(activeAnimationFrame);
    }

    private void nextImage()
    {
        currentImageName.setText(currentAnimationName + "_" + currentFrame);
        changeFrame(currentFrame);
        if(this.isVisible())
            repaint();
    }

    private void updateFrame()
    {
        frameCounter++;
        frameCounter%= speed;
        if(frameCounter == 0)
        {
            currentFrame++;
            currentFrame%= animationFrames.size();
            nextImage();
        }
    }

    private void addAnimationFrame(BufferedImage frame)
    {
        animationFrames.add(new AnimationFrame(frame));
    }

    private void addAnimationFrames(List<BufferedImage> frames)
    {
        for(BufferedImage frame : frames)
            addAnimationFrame(frame);
    }

    public static void setAnimationFramesSchedule(List<BufferedImage> frames, String animationName)
    {
        scheduledName = animationName;
        scheduledBufferedImages = frames;
        scheduledUpdate = true;
    }

    private void setAnimationFrames(List<BufferedImage> frames, String animationName)
    {
        currentAnimationName = animationName;
        if(activeAnimationFrame != null)
            remove(activeAnimationFrame);
        currentFrame = 0;
        frameCounter = 0;
        animationFrames.clear();
        addAnimationFrames(frames);
        scheduledUpdate = false;
    }

}