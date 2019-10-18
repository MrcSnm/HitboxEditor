package app.global;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

public class KeyChecker
{
    public static volatile boolean IS_CONTROL_PRESSED = false;
    public static volatile boolean IS_SHIFT_PRESSED = false;
    public static volatile boolean IS_ALT_PRESSED = false;
    private static boolean hasStarted = false;

    private KeyChecker(JComponent target)
    {
        Globals.addKeyListener(target, "ctrl CONTROL", "ctrlPressed", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                Globals.debugLog("CONTROL PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl alt CONTROL", "ctrlAlt", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("CONTROL | ALT PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl alt ALT", "altCtrl", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("CONTROL | ALT PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl shift CONTROL", "ctrlShift", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_SHIFT_PRESSED = true;
                Globals.debugLog("CONTROL | SHIFT  PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl shift SHIFT", "shiftCtrl", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_SHIFT_PRESSED = true;
                Globals.debugLog("CONTROL | SHIFT  PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl alt shift CONTROL", "ctrlAltShift", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_SHIFT_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("CONTROL | ALT | SHIFT PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl alt shift ALT", "altCtrlShift", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_SHIFT_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("CONTROL | ALT | SHIFT PRESSED");
            }
        });

        Globals.addKeyListener(target, "ctrl alt shift SHIFT", "shiftCtrlAlt", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = true;
                IS_SHIFT_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("CONTROL | ALT | SHIFT PRESSED");
            }
        });

        Globals.addKeyListener(target, "alt shift SHIFT", "shiftAlt", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_SHIFT_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("ALT | SHIFT PRESSED");
            }
        });

        Globals.addKeyListener(target, "alt shift ALT", "altShift", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_SHIFT_PRESSED = true;
                IS_ALT_PRESSED = true;
                Globals.debugLog("ALT | SHIFT PRESSED");
            }
        });

        Globals.addKeyListener(target, "released CONTROL", "ctrlReleased", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_CONTROL_PRESSED = false;
                Globals.debugLog("CONTROL RELEASED");
            }
        });

        Globals.addKeyListener(target, "shift SHIFT", "shiftPressed", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_SHIFT_PRESSED = true;
                Globals.debugLog("SHIFT PRESSED");
            }
        });

        Globals.addKeyListener(target, "released SHIFT", "shitReleased", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_SHIFT_PRESSED = false;
                Globals.debugLog("SHIFT RELEASED");
            }
        });

        Globals.addKeyListener(target, "alt ALT", "altPressed", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_ALT_PRESSED = true;
                Globals.debugLog("ALT PRESSED");
            }
        });

        Globals.addKeyListener(target, "released ALT", "altReleased", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IS_ALT_PRESSED = false;
                Globals.debugLog("ALT RELEASED");
            }
        });
    }

    public static void start(JComponent target)
    {
        if(!hasStarted)
            new KeyChecker(target);
        hasStarted = true;
    }
}