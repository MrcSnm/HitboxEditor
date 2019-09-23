package app;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class AnimationViewer extends JDialog
{

    private int speed = 16;
    private int frameCounter = 0;
    private int currentFrame = 0;
    public String animationName;
    private List<BufferedImage> animationFrames = new ArrayList<BufferedImage>();
    private JLabel currentImageName;
    private JPanel imageView;

    private AnimationFrame activeAnimationFrame;

    private List<AnimationFrame> imageBuffer = new ArrayList<AnimationFrame>();

    AnimationViewer(Frame owner, String animationName)
    {
        super(owner, animationName);
        this.animationName = animationName;
        currentImageName = new JLabel();
        imageView = new JPanel();
        add(imageView);
        imageView.add(currentImageName);
    }

    private void changeFrame(int frameNumber)
    {
        if(activeAnimationFrame != null)
            imageView.remove(activeAnimationFrame);
        activeAnimationFrame = imageBuffer.get(frameNumber);
        imageView.add(activeAnimationFrame);
        validate();
    }

    private void nextImage()
    {
        currentImageName.setText(animationName + currentFrame);
        changeFrame(currentFrame);
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

    public void addAnimationFrame(BufferedImage frame)
    {
        animationFrames.add(frame);
        imageBuffer.add(new AnimationFrame(frame));
    }
    public void addAnimationFrames(List<BufferedImage> frames)
    {
        animationFrames.addAll(frames);

        for(BufferedImage frame : frames)
        {
            imageBuffer.add(new AnimationFrame(frame));
        }
    }

    public void setAnimationFrame(BufferedImage frame, String animationName)
    {
        this.animationName = animationName;
        animationFrames.clear();
        addAnimationFrame(frame);
    }

    public void setAnimationFrames(List<BufferedImage> frames, String animationName)
    {
        this.animationName = animationName;
        animationFrames.clear();
        addAnimationFrames(frames);
    }
}