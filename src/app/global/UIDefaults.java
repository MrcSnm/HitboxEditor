package app.global;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

public class UIDefaults
{
    public static Color DARKER_GRAY = new Color(0x1e1e1e);
    public static Color BIT_DARKER_GRAY = new Color(0x282828);

    public static ImageIcon checkedIcon = new ImageIcon(Globals.createImage("checked.png", "Checkicon"));
    public static ImageIcon uncheckedIcon = new ImageIcon(Globals.createImage("unchecked.png", "Uncheckicon"));

    public static void setUIDefaults()
    {
        setPanelDefault();
        setOptionPaneDefault();
        setButtonDefault();
        setScrollPaneDefault();

        setMenuBarDefault();
        setMenuDefault();
        setMenuItemDefault();
        setRadioMenuItemDefault();
        setCheckMenuItemDefault();

        setTextAreaDefault();
        setTextFieldDefault();
        setTableDefault();
    }

    private static void setOptionPaneDefault()
    {
        UIManager.put("OptionPane.background", BIT_DARKER_GRAY);
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(Color.WHITE));
        UIManager.put("OptionPane.foreground", new ColorUIResource(BIT_DARKER_GRAY));

    }

    private static void setButtonDefault()
    {
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
    }
    
    private static void setTextAreaDefault()
    {
        UIManager.put("TextArea.border", new LineBorder(Color.RED));
        UIManager.put("TextArea.background", Color.DARK_GRAY);
        UIManager.put("TextArea.caretForeground", Color.WHITE);
        UIManager.put("TextArea.foreground", Color.WHITE);
    }

    private static void setTextFieldDefault()
    {
        UIManager.put("TextField.border", new LineBorder(Color.RED));
        UIManager.put("TextField.background", Color.DARK_GRAY);
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.caretForeground", Color.WHITE);
    }

    private static void setPanelDefault()
    {
        UIManager.put("Panel.background", BIT_DARKER_GRAY);
        UIManager.put("Panel.foreground", BIT_DARKER_GRAY);
        UIManager.put("SplitPane.dividerSize", 5);
    }

    private static void setScrollPaneDefault()
    {
        UIManager.put("ScrollPane.background", Color.DARK_GRAY);
        UIManager.put("ScrollBar.background", Color.DARK_GRAY);
        UIManager.put("ScrollBar.foreground", Color.WHITE);
    }

    private static void setMenuBarDefault()
    {
        UIManager.put("MenuBar.foreground", SystemColor.textHighlight);
		UIManager.put("MenuBar.background", Color.DARK_GRAY);
    }
    private static void setMenuDefault()
    {
        UIManager.put("Menu.background", DARKER_GRAY);
        UIManager.put("Menu.selectionBackground", Color.BLACK);
        UIManager.put("Menu.selectionForeground", Color.RED);
        UIManager.put("Menu.foreground", Color.WHITE);
    }

    private static void setMenuItemDefault()
    {
        UIManager.put("MenuItem.background", Color.DARK_GRAY);
        UIManager.put("MenuItem.foreground", Color.WHITE);
        UIManager.put("MenuItem.selectionBackground", Color.BLACK);
        UIManager.put("MenuItem.selectionForeground", Color.WHITE);
        UIManager.put("MenuItem.acceleratorSelectionForeground", Color.RED);
        UIManager.put("MenuItem.acceleratorForeground", Color.RED);
    }

    private static void setRadioMenuItemDefault()
    {
        UIManager.put("RadioButtonMenuItem.background", Color.DARK_GRAY);
        UIManager.put("RadioButtonMenuItem.foreground", Color.WHITE);
        UIManager.put("RadioButtonMenuItem.acceleratorForeground", Color.RED);
        UIManager.put("RadioButtonMenuItem.acceleratorSelectionForeground", Color.RED);
        UIManager.put("RadioButtonMenuItem.checkIcon", null);
    }

    private static void setCheckMenuItemDefault()
    {
        UIManager.put("CheckBoxMenuItem.background", Color.DARK_GRAY);
        UIManager.put("CheckBoxMenuItem.foreground", Color.WHITE);
        UIManager.put("CheckBoxMenuItem.acceleratorForeground", Color.RED);
        UIManager.put("CheckBoxMenuItem.acceleratorSelectionForeground", Color.RED);
    }

    private static void setTableDefault()
    {
        UIManager.put("TableHeader.background", BIT_DARKER_GRAY);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("Table.background", Color.DARK_GRAY);
        UIManager.put("Table.selectionBackground", new Color(255, 0, 0, 100));
        UIManager.put("Table.foreground", Color.WHITE);
        UIManager.put("Table.selectionForeground", Color.WHITE);
    }
}