package app.base;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import app.global.Callback;

public class Inspector extends JPanel
{
    JLabel title;
    JScrollPane scrollPane;
    JTable inspector;
    JButton removeButton;
    private Map<String, Callable<String>> valuesGetter;
    private Map<String, Callback<Object>> valuesSetter;
    
    public Inspector()
    {
        super(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        inspector = new JTable(new DefaultTableModel(new Object[]{"Parameter", "Value"}, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return col == 1;
            }
        });

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setAlignmentX(JLabel.CENTER);
        inspector.setDefaultRenderer(String.class, renderer);

        scrollPane = new JScrollPane(inspector);
        valuesGetter = new HashMap<String, Callable<String>>();
        valuesSetter = new HashMap<String, Callback<Object>>();
        removeButton = new JButton("Remove");

        title = new JLabel("Select target to inspect");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);

        Font f = title.getFont();
        title.setFont(new Font(f.getName(), Font.PLAIN, (int)(f.getSize() * 2)));

        add(title);
        add(scrollPane);
        add(removeButton);
        

        

    }

    public void setName(String name)
    {
        title.setText(name);
    }

    public void addTargetValue(String valueName, Callable<String> valueGetter)
    {
        //DefaultTableModel tb = (DefaultTableModel)inspector.getModel();
        valuesGetter.put(valueName, valueGetter);
    }

    public void addTargetSetter(String valueName, Callback<Object> valueGetter)
    {
        //DefaultTableModel tb = (DefaultTableModel)inspector.getModel();
        valuesSetter.put(valueName, valueGetter);
    }

    public void restartTable() throws Exception
    {
        DefaultTableModel tb = (DefaultTableModel)inspector.getModel();
        tb.setRowCount(0);
        for(Map.Entry<String, Callable<String>> entry : valuesGetter.entrySet())
        {
            tb.addRow(new Object[]{entry.getKey(), entry.getValue().call()});
        }
    }

    private void checkSetImplemented()
    {
        boolean canContinue = false;
        for(Map.Entry<String, Callable<String>> getters : valuesGetter.entrySet())
        {
            canContinue = false;
            for(Map.Entry<String, Callback<Object>> setters : valuesSetter.entrySet())
            {
                if(setters.getKey().equals(getters.getKey()))
                    canContinue = true;
            }
            if(!canContinue)
            {
                System.err.println("Setter not implemented for " + getters.getKey());
                return;
            }
        }
    }

    public void setTarget(InspectorTarget target)
    {
        ((DefaultTableModel)inspector.getModel()).setRowCount(0);
        target.onTargeted(this);
        target.onTargetedSetters(this);
        setName(target.getTargetName());
        checkSetImplemented();
        try{restartTable();}
        catch(Exception e){e.printStackTrace();}
    }


}