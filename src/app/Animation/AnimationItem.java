package app.Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import app.ImageComponent;

public class AnimationItem extends JMenuItem
{

    private List<ImageComponent> imgComponents = new ArrayList<ImageComponent>();
    public String animationName;

    public AnimationItem(String animName)
    {
        super(animName);
        animationName = animName;
    }

    public AnimationItem(String animName, Icon ic)
    {
        super(animName, ic);
        animationName = animName;
    }

    public void addImageComp(ImageComponent img)
    {
        imgComponents.add(img);
    }

    public List<BufferedImage> getAnimationFrames()
    {
        List<BufferedImage> imgs = new ArrayList<BufferedImage>();
        for(ImageComponent img : imgComponents)
            imgs.add(img.texture);
        return imgs;
    }

}