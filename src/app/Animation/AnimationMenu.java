package app.Animation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import app.ImageComponent;
import app.base.FilterableOptionsView;
import app.global.UIDefaults;

public class AnimationMenu extends JMenu 
{
    public static AnimationMenu instance;
    public static ButtonGroup currentBtn;
    private JMenuItem addButton;
    private static AnimationItem selectedItem = null;

    private AnimationMenu() {
        super("Animations");
        addButton = new JMenuItem("Create animation");
        add(addButton);
        JSeparator sep = new JSeparator();
        sep.setBackground(Color.WHITE);
        sep.setForeground(Color.DARK_GRAY);
        add(sep);

        currentBtn = new ButtonGroup();

        KeyStroke hotkey = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
        addButton.setAccelerator(hotkey);
        addButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String animName = "";
                animName = JOptionPane.showInputDialog(null, "Input an animation name", "New animation",
                        JOptionPane.PLAIN_MESSAGE);
                if (animName != null && !animName.equals("")) 
                    addAnimation(animName);
            }
        });

    }

    public static void addTo(JMenuBar comp) {
        if (instance == null)
            instance = new AnimationMenu();
        comp.add(instance);
        addAnimation("Teste");
    }

    public static void addAnimation(String animationName) 
    {
        AnimationItem item = new AnimationItem(animationName, UIDefaults.uncheckedIcon);
        item.setModel(new JToggleButton.ToggleButtonModel());
        item.addItemListener(new ItemListener() 
        {

            @Override
            public void itemStateChanged(ItemEvent e) 
            {
                switch(e.getStateChange())
                {
                    case ItemEvent.SELECTED:
                        Enumeration<AbstractButton> btns = currentBtn.getElements();
                        while(btns.hasMoreElements())
                        {
                            btns.nextElement().setIcon(UIDefaults.uncheckedIcon);
                        }
                        JMenuItem currentItem = (JMenuItem)e.getItem();
                        selectedItem = (AnimationItem)currentItem;
                        currentItem.setIcon(UIDefaults.checkedIcon);
                        AnimationViewer.setAnimation(getCurrentAnimation());
                    break;
                }
            }
            
        });
        instance.add(item);
        currentBtn.add(item);
    }

    public static AnimationItem getCurrentAnimation()
    {
        return selectedItem;
    }

    public static void addImageToAnimation(ImageComponent img)
    {
        if(selectedItem != null)
        {
            selectedItem.addImageComp(img);
            AnimationViewer.setAnimation(selectedItem);
            
        }
    }
}