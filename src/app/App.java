package app;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class App 
{

    public static void handleError()
    {

    }
    public static void main(String[] args) throws Exception 
    {
        JFrame f = new JFrame();
        f.setTitle("HitboxEditor by Hipreme");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize((int)dimension.getWidth(), (int)dimension.getHeight());
        
        Window w = new Window(f);
        f.setVisible(true);        
    }
}