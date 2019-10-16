package app.base;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FilterableOptionsView
{
    JTable tab;
    JButton addButton;
    JTextField filter;
    static FilterableOptionsView instance = null;
    JDialog dialog;
    JScrollPane scrollPane;
    JPanel panel;

    private FilterableOptionsView() 
    {
        dialog = new JDialog((Frame) null, "Animation View");
        dialog.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                
            }
        });

        String[] teste = { "Frames" };
        String[][] data = { { "Frames" }, { "Specs" }, { "Teste" } };
        tab = new JTable(data, teste);

        addButton = new JButton("Add Frames");
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.DARK_GRAY);

        panel = new JPanel(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        filter = new JTextField();
        filter.setAlignmentX(Component.CENTER_ALIGNMENT);

        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        scrollPane = new JScrollPane(tab);
        // addRow("Teste");
        
        this.addToDialog();   
    }

    public static void startFilterableOptionsView()
    {
        if(instance == null)
            instance = new FilterableOptionsView();
        else
            instance.dialog.setVisible(true);
    }

    public void addToDialog()
    {
        panel.add(scrollPane);
        panel.add(filter);
        panel.add(addButton);
        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void addRow(String name)
    {
      //  DefaultTableModel model = (DefaultTableModel) tab.getModel();
      //  Object b[] = {name};
      //  model.addRow(b);
    }

    public void Destroy()
    {
        dialog.dispose();
    }
}