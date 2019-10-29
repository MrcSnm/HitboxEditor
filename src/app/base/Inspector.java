package app.base;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import app.file.Saver;
import app.global.AbstractSetter;
import app.global.Callback;

public class Inspector extends JPanel 
{
    public boolean isUpdatingSetter = false;
    
    JLabel title;
    JScrollPane scrollPane;
    JTable inspector;
    JButton removeButton;
    InspectorTarget currentTarget;
    private Map<String, Callable<String>> valuesGetter;
    private Map<String, AbstractSetter> valuesSetter;
    ConcurrentUpdate valueChecker;

    String lastValue = null;
    String valueBeforeEdit = null;
    int lastColumn = 0;
    int lastRow = 0;

    public Inspector() 
    {
        super(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        inspector = new JTable(new DefaultTableModel(new Object[] { "Parameter", "Value" }, 0) {

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1;
            }
        });

        inspector.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println("Mudado");
            }
        });

        valueChecker = new ConcurrentUpdate(30) 
        {

            @Override
            public void update(double deltaTime) 
            {
                if(isUpdatingSetter)
                {
                    JTextField field = (JTextField)inspector.getEditorComponent();
                    Document dom = field.getDocument();
                    String val = "";
                    try {val = dom.getText(0, dom.getLength());}
                    catch (BadLocationException e) {e.printStackTrace();}
                    updateValue(val);
                }
                else
                {
                    try{updateEveryRow();}
                    catch(Exception e){e.printStackTrace();}
                }
                
                //System.out.println(inspector.getValueAt(row, 1));
            }
        };
        valueChecker.start();

        inspector.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent e)
            {
                //  A cell has started/stopped editing

                if ("tableCellEditor".equals(e.getPropertyName()))
                {
                    if (inspector.isEditing())
                    {
                        valueBeforeEdit = (String)inspector.getValueAt(inspector.getSelectedRow(), 1);
                        System.out.println("Value before edit: " + valueBeforeEdit);
                        isUpdatingSetter = true;
                    }
                    else
                        isUpdatingSetter = false;
                }
            }
        });
        inspector.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
        inspector.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new CellEditorListener(){
        
            @Override
            public void editingStopped(ChangeEvent e) 
            {
            }
        
            @Override
            public void editingCanceled(ChangeEvent e) 
            {
                recoverLastValue();
                System.out.println("Cancelled");
                // TODO Auto-generated method stub
            }
        });

        System.out.println(inspector.getColumnModel().getColumn(1).getCellEditor());

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setAlignmentX(JLabel.CENTER);
        inspector.setDefaultRenderer(String.class, renderer);

        scrollPane = new JScrollPane(inspector);
        valuesGetter = new HashMap<String, Callable<String>>();
        valuesSetter = new HashMap<String, AbstractSetter>();
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

    private void updateValue(String newValue)
    {
        if(lastValue == null)
            lastValue = newValue;
        else if(lastValue.equals(newValue))
            return;
        else
            lastValue = newValue;
        try{setCurrentValue();}
        catch(BadLocationException e){e.printStackTrace();}
        System.out.println(newValue);
        //valuesSetter.get(key)
    }

    private void setCurrentValue() throws BadLocationException
    {
        int row = inspector.getSelectedRow();
        String val = (String)inspector.getValueAt(row, 0);
        if(val != null)
        {
            JTextField field = (JTextField)inspector.getEditorComponent();
            Document dom = field.getDocument();
            String currentStr = valuesSetter.get(val).setValue(lastValue);
            if(!dom.getText(0, dom.getLength()).equals(currentStr))
            {
                dom.remove(0, dom.getLength());
                dom.insertString(0, currentStr, null);
            }
            currentTarget.postSetOperation();
        }
    }

    private void recoverLastValue()
    {
        inspector.editCellAt(lastRow, lastColumn);
        ((DefaultTableModel)inspector.getModel()).setValueAt(valueBeforeEdit, lastRow, lastColumn);
        updateValue(valueBeforeEdit);
        
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

    public void addTargetSetter(String valueName, AbstractSetter valueSetter)
    {
        //DefaultTableModel tb = (DefaultTableModel)inspector.getModel();
        valuesSetter.put(valueName, valueSetter);
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

    public int getRowFromGetter(String getterKey)
    {
        for(int i = 0, len = valuesGetter.size(); i < len; i++)
        {
            if(inspector.getValueAt(i, 0).toString().equals(getterKey))
                return i;
        }
        System.err.println("Could not get row for the key '" + getterKey + "' for the current inspected target");
        return -1;
    }

    public void updateEveryRow() throws Exception
    {
        if(currentTarget != null)
            for(int i = 0, len = inspector.getRowCount(); i < len; i++)
            {
                Object propertyName = inspector.getValueAt(i, 0);
                Object propertyValue = inspector.getValueAt(i, 1);
                String newValue = valuesGetter.get(propertyName).call();
                if(propertyValue != null && newValue != null && !propertyValue.equals(newValue))
                    inspector.setValueAt(valuesGetter.get(propertyName).call(), i, 1);
            }
    }

    private void checkSetImplemented()
    {
        boolean canContinue = false;
        for(Map.Entry<String, Callable<String>> getters : valuesGetter.entrySet())
        {
            canContinue = false;
            for(Map.Entry<String, AbstractSetter> setters : valuesSetter.entrySet())
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
        if(inspector.isEditing())
        {
            inspector.getCellEditor().cancelCellEditing();
        }
        ((DefaultTableModel)inspector.getModel()).setRowCount(0);
        currentTarget = target;
        target.onTargeted(this);
        target.onTargetedSetters(this);
        setName(target.getTargetName());
        checkSetImplemented();
        try{restartTable();}
        catch(Exception e){e.printStackTrace();}
    }


}