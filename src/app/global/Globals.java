package app.global;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Globals
{
    public static boolean IS_DEBUG_ACTIVE = true;

    public static void debugLog(String output)
    {
        if(IS_DEBUG_ACTIVE)
            System.out.println(output);
    }

    public static void addHotkey(JMenuItem item, int letter, int mask)    
    {
        KeyStroke ks = KeyStroke.getKeyStroke(letter, mask);
        item.setAccelerator(ks);
    }

    public static void addKeyListener(JComponent jc, String keyName, String actionName, AbstractAction act)
    {
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyName), actionName);
        jc.getActionMap().put(actionName, act);
    }

    /**
     * Ignores modifiers (alt | shift | control) -> Useful for using to check only if the key was pressed
     */
    public static void addKeyListenerIgnore(JComponent jc, String keyName, String actionName, AbstractAction act)
    {
        addKeyListener(jc, "ctrl " + keyName, "ctrl" + actionName, act);
        addKeyListener(jc, "ctrl alt " + keyName, "ctrlAlt" + actionName, act);
        addKeyListener(jc, "ctrl shift " + keyName, "ctrlShift" + actionName, act);
        addKeyListener(jc, "ctrl alt shift " + keyName, "ctrlAltShift" + actionName, act);
        addKeyListener(jc, "shift " + keyName, "shift" + actionName, act);
        addKeyListener(jc, "alt " + keyName, "alt" + actionName, act);
        addKeyListener(jc, "alt shift " + keyName, "alt" + actionName, act);
    }
}