package app.base;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import app.Animation.AnimationViewer;

public class FilterableOptionsView
{
    JTable tab;
    JButton addButton;
    JTextField filter;
    static FilterableOptionsView instance = null;
    JDialog dialog;
    JScrollPane scrollPane;
    JSplitPane splitPane;
    JPanel panel;

    private FilterableOptionsView() 
    {
        dialog = new JDialog((Frame) null, "Animation View");

        String[] teste = { "Frames" };
        String[][] data = { { "Frames" }, { "Specs" }, { "Teste" } };
        tab = new JTable(data, teste);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tab.setDefaultRenderer(String.class, centerRenderer);

        addButton = new JButton("Add Frames");
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.DARK_GRAY);

        panel = new JPanel(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, panel, AnimationViewer.instance);


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
        {
            instance = new FilterableOptionsView();
        }
        else
            instance.dialog.setVisible(true);
    }

    public void addToDialog()
    {
        panel.add(scrollPane);
        panel.add(filter);
        panel.add(addButton);
        dialog.add(splitPane);
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