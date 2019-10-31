package app.global.mouse;

import java.awt.event.MouseEvent;
public interface MouseDrag
{
    public static final boolean IS_DRAGGING = false;
    public abstract void onDragStart(MouseEvent e);
    public abstract void onDragEnd(MouseEvent e);
}