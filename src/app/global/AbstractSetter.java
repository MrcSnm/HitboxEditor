package app.global;

public abstract class AbstractSetter
{
    private String numberResolver(String num)
    {
        String returnString = num.replaceAll("\\D", "");
        if(returnString == null || returnString.equals(""))
            returnString = "0";
        return returnString;
    }

    public float getFloat(String num)
    {
        String n = numberResolver(num);
        return Float.parseFloat(n);
    }

    public int getInt(String num)
    {
        String n = numberResolver(num);
        return Integer.parseInt(n);
    }
    public long getLong(String num)
    {
        String n = numberResolver(num);
        return Long.parseLong(n);
    }

    public double getDouble(String num)
    {
        String n = numberResolver(num);
        return Double.parseDouble(n);
    }

    public abstract String setValue(String value); //Return the value to modify the inspector cell
}