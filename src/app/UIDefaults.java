package app;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class UIDefaults
{
    public static void setUIDefaults()
    {
        setOptionPaneDefault();
        setPanelDefault();
        setButtonDefault();

        setMenuBarDefault();
        setMenuDefault();
        setMenuItemDefault();

    }

    private static void setOptionPaneDefault()
    {
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(Color.WHITE));
        UIManager.put("OptionPane.foreground", new ColorUIResource(Color.DARK_GRAY));
    }

    private static void setButtonDefault()
    {
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
    }

    private static void setPanelDefault()
    {
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Panel.foreground", new ColorUIResource(Color.DARK_GRAY));
    }
    private static void setMenuBarDefault()
    {
        UIManager.put("MenuBar.foreground", SystemColor.textHighlight);
		UIManager.put("MenuBar.background", Color.DARK_GRAY);
    }
    private static void setMenuDefault()
    {
		UIManager.put("Menu.background", Color.DARK_GRAY);
        UIManager.put("Menu.foreground", Color.WHITE);
    }

    private static void setMenuItemDefault()
    {
        UIManager.put("MenuItem.background", Color.DARK_GRAY);
        UIManager.put("MenuItem.foreground", Color.WHITE);
        UIManager.put("MenuItem.acceleratorForeground", Color.RED);
    }
}