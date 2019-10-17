package app.Animation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class AnimationMenu extends JMenu 
{
    public static AnimationMenu instance;
    private JMenuItem addButton;

    private AnimationMenu()
    {
        super("Animations");
        addButton = new JMenuItem("Add new animation");
        add(addButton);
        JSeparator sep = new JSeparator();
        sep.setBackground(Color.WHITE);
        sep.setForeground(Color.DARK_GRAY);
        add(sep);

        KeyStroke hotkey = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
        addButton.setAccelerator(hotkey);
        addButton.addActionListener(new ActionListener()
        {
			@Override
            public void actionPerformed(ActionEvent e) 
            {
                String animName = "";
                animName = JOptionPane.showInputDialog(null, "Input an animation name", "New animation", JOptionPane.PLAIN_MESSAGE);
                if(animName != null && !animName.equals(""))
                {
                    addAnimation(animName);
                }
			}});

    }

    public static void addTo(JMenuBar comp)
    {
        if(instance == null)
            instance = new AnimationMenu();
        comp.add(instance);
        addAnimation("Teste");
    }

    public static void addAnimation(String animationName)
    {
        JMenuItem item = new JMenuItem(animationName);
        item.setForeground(Color.WHITE);
        item.setBackground(Color.DARK_GRAY);
        instance.add(item);
    }
}