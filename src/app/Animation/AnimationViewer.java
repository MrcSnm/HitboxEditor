package app.Animation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.base.ConcurrentUpdate;
import app.base.FilterableOptionsView;

public class AnimationViewer extends JPanel 
{

    private int speed = 8;
    private volatile int frameCounter = 0;
    private volatile int currentFrame = 0;
    public String currentAnimationName;

    public static AnimationViewer instance;
    private AnimationItem item;

    private JLabel currentImageName;

    private AnimationFrame activeAnimationFrame = null;
    private List<AnimationFrame> animationFrames = new ArrayList<AnimationFrame>();

    private static boolean scheduledUpdate = false;

    public static boolean IS_STOPPED = true;
    public static boolean IS_LOOPING = true;

    private static String scheduledName = "";
    private static List<BufferedImage> scheduledBufferedImages = new ArrayList<BufferedImage>();

    public ConcurrentUpdate updater;

    private AnimationViewer() 
    {
        super(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        currentImageName = new JLabel("Animation ");
        currentImageName.setForeground(Color.WHITE);
        add(currentImageName);

        updater = new ConcurrentUpdate()
        {
            @Override
            public void update(double deltaTime)
            {
                if (canUpdate())
                    updateFrame();
                if (scheduledUpdate)
                    setAnimationFrames(scheduledBufferedImages, scheduledName);
            }
        };
        updater.start();
    }

    public static void stopAnimation()
    {
        if(instance == null)
            return;
        IS_STOPPED = true;
        instance.updater.setUpdating(false);
    }

    public static void playAnimation()
    { 
        if(instance == null)
            return;
        IS_STOPPED = false;
        instance.updater.setUpdating(true);
    }

    public static void loopAnimation()
    {
        IS_LOOPING = !IS_LOOPING;
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
        FilterableOptionsView.setTableToAnimation(item);
        setAnimationFramesSchedule(item.getAnimationFrames(), item.animationName);
    }

    private void changeFrame(int frameNumber)
    {
        if(activeAnimationFrame != null)
            remove(activeAnimationFrame);

        FilterableOptionsView.setFrameSelected(frameNumber);
        activeAnimationFrame = animationFrames.get(frameNumber);
        add(activeAnimationFrame);
    }

    public static void globalChangeFrame(int frameNumber)
    {
        if(IS_STOPPED)
        {
            instance.changeFrame(frameNumber);
            instance.currentFrame = frameNumber;
            instance.revalidate();
            instance.repaint();
        }
        System.out.println("Change: " + frameNumber);
    }

    private void nextImage()
    {
        currentImageName.setText(currentAnimationName + "_" + currentFrame);
        changeFrame(currentFrame);
        AnimationViewer an = this;
        if(this.isVisible())
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    an.revalidate();
                    an.repaint();
                }
            });
    }

    private void updateFrame()
    {
        frameCounter++;
        frameCounter%= speed;
        if(frameCounter == 0)
        {
            currentFrame++;
            currentFrame%= animationFrames.size();
            if(!IS_LOOPING && currentFrame == 0)
                stopAnimation();
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