package app.global.command;

public interface Command
{
    public void doCommand();
    public void redoCommand();
    public void undoCommand();
}