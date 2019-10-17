package app.global;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Globals
{
    public static void addHotkey(JMenuItem item, int letter, int mask)    
    {
        KeyStroke ks = KeyStroke.getKeyStroke(letter, mask);
        item.setAccelerator(ks);
    }
}