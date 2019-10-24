package app.global;

import java.util.concurrent.Callable;

public abstract class Callback<T> implements Callable<Void>
{
    public Callback(T value)
    {
        this.value = value;
    }
    public T value;
    public abstract Void call();
}