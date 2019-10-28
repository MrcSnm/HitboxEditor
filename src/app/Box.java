package app;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import app.base.Inspector;
import app.base.InspectorTarget;
import app.global.AbstractSetter;
import app.global.Callback;;

public class Box extends JComponent implements InspectorTarget
{
   public int RECT_X, RECT_Y, RECT_X_2, RECT_Y_2;
   private boolean isNormallized = false;


   public int startX, startY;
   private boolean isSelected = false;

   public Color selectedBoxBorderColor;
   public Color selectedBoxFillColor;
   public Color boxBorderColor;
   public Color boxFillColor;
   private List<Box> boxContainer;
   private JComponent boxParent;

   public Box(int x, int y, int X_2, int Y_2)
   {
       RECT_X = x;
       RECT_Y = y;
       RECT_X_2 = X_2; 
       RECT_Y_2 = Y_2;

       addMouseListener(new MouseAdapter()
       {
          @Override
          public void mouseClicked(MouseEvent e)
          {
             System.out.println("Clicou numa box");
          }
       });
   }

   public void onTargeted(Inspector inspector)
   {
      Box b = this;
      inspector.addTargetValue("X", new Callable<String>()
      {
         public String call()
         {
            return String.valueOf(b.RECT_X);
         }
      });

      inspector.addTargetValue("Y", new Callable<String>()
      {
         public String call()
         {
            return String.valueOf(b.RECT_Y);
         }
      });

      inspector.addTargetValue("WIDTH", new Callable<String>()
      {
         public String call()
         {
            return String.valueOf(b.RECT_X_2 - b.RECT_X);
         }
      });

      inspector.addTargetValue("HEIGHT", new Callable<String>()
      {
         public String call()
         {
            return String.valueOf(b.RECT_Y_2 - b.RECT_Y);
         }
      });
   }

   public void onTargetedSetters(Inspector inspector)
   {
      Box b = this;
      inspector.addTargetSetter("X", new AbstractSetter()
      {
         public String setValue(String value)
         {
            int val = getInt(value);
            b.RECT_X_2 = val + (b.RECT_X_2 - b.RECT_X);
            b.RECT_X = val;
            return String.valueOf(val);
         }
      });

      inspector.addTargetSetter("Y", new AbstractSetter()
      {
         public String setValue(String value)
         {
            int val = getInt(value);
            b.RECT_Y_2 = val + (b.RECT_Y_2 - b.RECT_Y);
            b.RECT_Y = val;
            return String.valueOf(val);
         }
      });

      inspector.addTargetSetter("WIDTH", new AbstractSetter()
      {
         public String setValue(String value)
         {
            int val = getInt(value);
            b.RECT_X_2 = b.RECT_X + val;
            return String.valueOf(val);
         }
      });

      inspector.addTargetSetter("HEIGHT", new AbstractSetter()
      {
         public String setValue(String value)
         {
            int val = getInt(value);
            b.RECT_Y_2 = b.RECT_Y + val;
            return String.valueOf(val);
         }
      });
   }
   
   public void postSetOperation()
   {
      setDefaultBounds();
      revalidate();
      repaint();
   }

   public String getTargetName()
   {
      return "Box";
   }

   public Box(List<Box> container, JComponent parent)
   {
	   boxContainer = container;
	   boxParent = parent;	   
   }

   public void unNormallize(Editor editor)
   {
      if(!isNormallized)
         return;
      isNormallized = false;
      RECT_X = editor.unNormallizeX(RECT_X);
      RECT_Y = editor.unNormallizeY(RECT_Y);
      RECT_X_2 = editor.unNormallizeX(RECT_X_2);
      RECT_Y_2 = editor.unNormallizeY(RECT_Y_2);
      setDefaultBounds();
   }

   public void normallize(Editor editor)
   {
      if(isNormallized)
         return;
      isNormallized = true;
      RECT_X = editor.normallizeX(RECT_X);
      RECT_Y = editor.normallizeY(RECT_Y);
      RECT_X_2 = editor.normallizeX(RECT_X_2);
      RECT_Y_2 = editor.normallizeY(RECT_Y_2);
      setDefaultBounds();
   }

   public void toggleNormallize(Editor editor)
   {
      if(isNormallized)
         unNormallize(editor);
      else
         normallize(editor);
   }

   public String toString()
   {
      return "X: " + RECT_X + " X_2: " + RECT_X_2 + " Y: " + RECT_Y + " Y_2: " + RECT_Y_2;
   }

   
   public boolean pointIntersection(int x, int y)
   {
	   return (x >= RECT_X && x <= RECT_X_2 && y>= RECT_Y && y<= RECT_Y_2);
   }

   public boolean canSave()
   {
      return isNormallized;
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

   public Box setSelected(boolean selected)
   {
      this.isSelected = selected;
      revalidate();
      repaint();
      return this;
   }

   public void setSizeByPoint(int x, int y)
   {

      //RELATIVE
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
      
      setDefaultBounds();
   }

   public void setDefaultBounds()
   {
      setBounds(RECT_X, RECT_Y, RECT_X_2 - RECT_X, RECT_Y_2 - RECT_Y);
   }

   public void copyInto(Box b)
   {
      b.RECT_X = this.RECT_X;
      b.RECT_X_2 = this.RECT_X_2;
      b.RECT_Y = this.RECT_Y;
      b.RECT_Y_2 = this.RECT_Y_2;
      setDefaultBounds();
   }

   /**
    * Reserved for loading purposes
    */
   public void loadCopyInto(Box b)
   {
      copyInto(b);
      b.isNormallized = true;
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
      g.setColor(((isSelected) ? selectedBoxFillColor : boxFillColor));
      g.fillRect(0, 0, RECT_X_2 - RECT_X, RECT_Y_2 - RECT_Y);
      g.setColor(((isSelected) ? selectedBoxBorderColor : boxBorderColor));
      if(!isSelected)
         g.drawRect(0, 0, (RECT_X_2 - RECT_X) - 1, (RECT_Y_2 - RECT_Y) - 1);
      else
      {
         Graphics2D g2d = (Graphics2D)g;
         Stroke old = g2d.getStroke();
         g2d.setStroke(new BasicStroke(4));
         g2d.drawRect(2, 2, (RECT_X_2 - RECT_X) - 4, (RECT_Y_2 - RECT_Y) - 4);
         g2d.setStroke(old);
      }
   }

   @Override
   public Dimension getPreferredSize() 
   {
      return new Dimension(RECT_X_2 - RECT_X, RECT_Y_2 - RECT_Y);
   }

   @Override
   public Rectangle getBounds(Rectangle r)
   {
      super.getBounds(r);
      r.width = RECT_X_2 - RECT_X;
      r.height = RECT_Y_2 - RECT_Y;
      return r;
      
   }
}