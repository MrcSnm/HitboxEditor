package app.global.command;

public abstract class ReferenceCommand<T> implements Command
{
    public T reference;
    public ReferenceCommand(T referenceToStore)
    {
        reference = referenceToStore;
    }
}