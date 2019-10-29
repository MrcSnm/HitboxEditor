package app.global.command;

public class CommandManager
{
    private int currentCommandPointer = 0;
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

    public static void doCommand(Command cmd)
    {
        instance.commandStack.add(cmd);
        cmd.doCommand();
        instance.currentCommandPointer++;
    }

    public static void redoCommand()
    {
        if(instance.currentCommandPointer == instance.commandStack.size())
            return;
        instance.commandStack.get(instance.currentCommandPointer).doCommand();
        instance.currrentCommandPointer++;
    }

    public static void undoCommand()
    {
        if(instance.currentCommandPointer == 0)
            return;
        instance.commandStack.get(instance.currentCommandPointer).undoCommand();
        instance.curentCommandPointer--;
    }

}