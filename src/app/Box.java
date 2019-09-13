package app;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

public class Box extends JComponent
{
   public int RECT_X, RECT_Y, RECT_W, RECT_H;
   public Color boxBorderColor;
   public Color boxFillColor;

   Box(int x, int y, int w, int h)
   {
       RECT_X = x;
       RECT_Y = y;
       RECT_W = w; 
       RECT_H = h;
   }

   @Override
   protected void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      // draw the rectangle here
      g.setColor(boxFillColor);
      g.fillRect(RECT_X, RECT_Y, RECT_W, RECT_H);
      g.setColor(boxBorderColor);
      g.drawRect(RECT_X, RECT_Y, RECT_W, RECT_H);
   }

   @Override
   public Dimension getPreferredSize() 
   {
      return new Dimension(RECT_W + 2 * RECT_X, RECT_H + 2 * RECT_Y);
   }
}