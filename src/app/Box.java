package app;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;

public class Box extends JComponent
{
   public int RECT_X, RECT_Y, RECT_X_2, RECT_Y_2;
   public int startX, startY;
   public Color boxBorderColor;
   public Color boxFillColor;
   private List<Box> boxContainer;
   private Container boxParent;

   public Box(int x, int y, int X_2, int Y_2)
   {
       RECT_X = x;
       RECT_Y = y;
       RECT_X_2 = X_2; 
       RECT_Y_2 = Y_2;
   }
   
   
   public Box(List<Box> container, Container parent)
   {
	   boxContainer = container;
	   boxParent = parent;
	   
   }
   
   
   public boolean pointIntersection(int x, int y)
   {
	   return (x >= RECT_X && x <= RECT_X_2 && y>= RECT_Y && y<= RECT_Y_2);
   }
   
   public void remove()
   {
	   boxContainer.remove(this);
   }

   public void setStartPoint(int x, int y)
   {
      startX = x;
      startY = y;
   }

   public void setSizeByPoint(int x, int y)
   {
      if(x <= startX)
      {
         RECT_X_2 = startX;
         RECT_X = x;
      }
      else 
      {
         RECT_X = startX;
         RECT_X_2 = x;
      }
      if(y <= startY)
      {
         RECT_Y_2 = startY;
         RECT_Y = y;
      }
      else
      {
         RECT_Y = startY;
         RECT_Y_2 = y;
      }
   }

   public void copyInto(Box b)
   {
      b.RECT_X = this.RECT_X;
      b.RECT_X_2 = this.RECT_X_2;
      b.RECT_Y = this.RECT_Y;
      b.RECT_Y_2 = this.RECT_Y_2;
   }

   public String getRectAsJSON()
   {
      String rect = "{\n";
      rect+= "\t" + "\"x\" : " + RECT_X + ",\"y\": " + RECT_Y + ", \"width\" : " + (RECT_X_2 - RECT_X) + ", \"height\" : " + (RECT_Y_2 - RECT_Y);
      return rect + "\n}";
   }

   @Override
   protected void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      // draw the rectangle here
      g.setColor(boxFillColor);
      g.fillRect(RECT_X, RECT_Y, RECT_X_2 - RECT_X, RECT_Y_2 - RECT_Y);
      g.setColor(boxBorderColor);
      g.drawRect(RECT_X, RECT_Y, RECT_X_2 - RECT_X, RECT_Y_2 - RECT_Y);
   }

   @Override
   public Dimension getPreferredSize() 
   {
      return new Dimension(RECT_X_2 - RECT_X, RECT_Y_2 - RECT_Y);
   }
}