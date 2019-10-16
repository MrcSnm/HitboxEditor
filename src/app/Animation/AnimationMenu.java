package app.Animation;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationMenu extends JMenu {
    public static AnimationMenu instance;
    private JMenuItem addButton;

    private AnimationMenu()
    {
        super("Animations");
        setForeground(Color.WHITE);
        setBackground(Color.DARK_GRAY);
        addButton = new JMenuItem("Add new animation");
        addButton.setForeground(Color.WHITE);
		addButton.setBackground(Color.DARK_GRAY);
        add(addButton);
        JSeparator sep = new JSeparator();
        sep.setBackground(Color.WHITE);
        sep.setForeground(Color.DARK_GRAY);
        add(sep);

        addButton.addActionListener(new ActionListener()
        {
			@Override
            public void actionPerformed(ActionEvent e) 
            {
                //JOptionPane.showInputDialog(parentComponent, message)	
                JOptionPane.showInputDialog(null, "Input an animation name", "New animation", JOptionPane.PLAIN_MESSAGE);
                // pane.setBackground(Color.DARK_GRAY);
                // pane.setForeground(Color.WHITE);
			}});

    }

    public static void addTo(JMenuBar comp)
    {
        if(instance == null)
            instance = new AnimationMenu();
        comp.add(instance);
        addAnimation("Teste");
        addAnimation("Teste");
        addAnimation("Teste");
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