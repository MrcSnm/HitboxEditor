package app.global.command;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import app.file.Saver;
import app.global.Globals;

public class CommandManager
{
    private int currentCommandPointer = 0;
    private int commandNumberToSetSaved = 0;
    public static CommandManager instance = null;
    public List<Command> commandStack;

    private CommandManager()
    {
        commandStack = new ArrayList<Command>();
    }

    public static void startCommandManager()
    {
        if(instance == null)
        {
            instance = new CommandManager();
        }
    }
    public static void implantCommandKey(JComponent comp)
    {
        if(instance == null)
        {
            System.err.println("CommandManager: could not implant command key, call CommandManager.startCommandManager before trying to implant command key");
            return;
        }
        Globals.addKeyListener(comp, "ctrl Z", "undo", new AbstractAction(){
        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println("Undo");
                undoCommand();
            }
        });

        Globals.addKeyListener(comp, "ctrl shift Z", "redo", new AbstractAction(){
        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println("Redo");
                redoCommand();
            }
        });
    }

    public static void execute(Command cmd)
    {
        instance.commandStack.add(cmd);
        cmd.doCommand();
        instance.currentCommandPointer++;
        if(Saver.isSaved)
        {
            instance.commandNumberToSetSaved = instance.currentCommandPointer - 1;
            Saver.setUnsaved();
        }
    }

    private static void checkIfReachedSavePoint()
    {
        if(instance.commandNumberToSetSaved == instance.currentCommandPointer)
            Saver.setSaved();
        else
            Saver.setUnsaved();
    }

    public static void redoCommand()
    {
        if(instance.currentCommandPointer == instance.commandStack.size())
            return;
        instance.commandStack.get(instance.currentCommandPointer).redoCommand();
        instance.currentCommandPointer++;
        checkIfReachedSavePoint();
    }

    public static void undoCommand()
    {
        if(instance.currentCommandPointer == 0)
            return;
        instance.currentCommandPointer--;
        instance.commandStack.get(instance.currentCommandPointer).undoCommand();
        checkIfReachedSavePoint();

    }

}