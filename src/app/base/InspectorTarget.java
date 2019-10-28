package app.base;


public interface InspectorTarget
{
    public String getTargetName();
    public void onTargeted(Inspector inspector);
    // public String preSetOperation(String value);
    public void onTargetedSetters(Inspector inspector);
    public void postSetOperation();
}