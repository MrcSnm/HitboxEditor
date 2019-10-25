package app.global;

import java.util.concurrent.Callable;

public abstract class Callback<T> implements Callable<Void>
{
    public Callback(T value)
    {
        this.value = value;
    }
    public T value;

    public void setValue(String value)
    {
        this.value = (T)value;
    }

    public abstract T castFunction();
    public abstract Void call();
}