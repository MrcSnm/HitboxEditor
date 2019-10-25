package app.base;


public interface InspectorTarget
{
    public String getTargetName();
    public void onTargeted(Inspector inspector);
    public void onTargetedSetters(Inspector inspector);
    public void postSetOperation();
}