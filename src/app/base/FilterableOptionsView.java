package app.base;

import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import app.ImageComponent;
import app.Animation.AnimationItem;
import app.Animation.AnimationMenu;
import app.Animation.AnimationViewer;
import app.global.Globals;
import app.global.UIDefaults;

public class FilterableOptionsView 
{
    public static JTable tab = null;
    JPanel controllerContainer;
    JButton addButton;
    JButton stopButton;
    JButton playButton;
    JButton loopButton;
    JTextField filter;
    JTextField currentAnimNameHeader;
    static FilterableOptionsView instance = null;
    static ImageIcon loopOn;
    static ImageIcon loopOff;
    JDialog dialog;
    JScrollPane scrollPane;
    JSplitPane splitPane;
    JPanel panel;

    private FilterableOptionsView(Window t) 
    {
        dialog = new JDialog(t, "Animation View");
        
      

        tab = new JTable(new DefaultTableModel(new Object[]{"Frames"}, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        });
        Globals.addKeyListener(tab, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0).toString(), "hideEscape", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dialog.setVisible(false);
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setBackground(UIDefaults.DARKER_GRAY);
        centerRenderer.setForeground(UIDefaults.DARKER_GRAY);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tab.setDefaultRenderer(String.class, centerRenderer);

        tab.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            {
                //if(tab.getSelectedRow() != -1)
                  //  AnimationViewer.globalChangeFrame(tab.getSelectedRow());
            }
        });
        
        currentAnimNameHeader = new JTextField("Select an animation to start viewing");
        currentAnimNameHeader.setHorizontalAlignment(JTextField.CENTER);
        currentAnimNameHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentAnimNameHeader.setEditable(false);
        Font f = currentAnimNameHeader.getFont();
        currentAnimNameHeader.setFont(new Font(f.getName(), Font.PLAIN, (int)(f.getSize() * 1.5)));
        addButton = new JButton("Add Frames");
        controllerContainer = new JPanel(true);
        controllerContainer.setLayout(new BoxLayout(controllerContainer, BoxLayout.X_AXIS));
        controllerContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AnimationViewer.playAnimation();
            }
        });
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AnimationViewer.stopAnimation();
            }
        });
        loopButton = new JButton("Loop");
        loopButton.setIcon(loopOn);
        loopButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AnimationViewer.loopAnimation();
                loopButton.setIcon((AnimationViewer.IS_LOOPING ? loopOn : loopOff));
            }
        });
        controllerContainer.add(stopButton);
        controllerContainer.add(playButton);
        controllerContainer.add(loopButton);

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

    public static void startFilterableOptionsView(Window f)
    {
        if(instance == null)
        {
            loopOn = new ImageIcon(Globals.createImage("loopOn.png", "Toggles loop"));
            loopOff = new ImageIcon(Globals.createImage("loopOff.png", "Toggles loop"));
            instance = new FilterableOptionsView(f);
        }
        else
            instance.dialog.setVisible(true);
        AnimationItem item = AnimationMenu.getCurrentAnimation();
        if(item != null)
            setTableToAnimation(item);
    }

    public static void setFrameSelected(int frameNumber)
    {
        if(tab != null && tab.getSelectedRow() != frameNumber)
        {
            tab.clearSelection();
            tab.setRowSelectionInterval(frameNumber, frameNumber);
        }
    }

    public void addToDialog()
    {
        panel.add(currentAnimNameHeader);
        panel.add(scrollPane);
        panel.add(filter);
        panel.add(controllerContainer);
        panel.add(addButton);
        dialog.add(splitPane);
        dialog.pack();
        dialog.setVisible(true);
    }

    private static void addRow(String name)
    {
        DefaultTableModel tb = (DefaultTableModel)tab.getModel();
        tb.addRow(new Object[]{name});
    }
    public static void setTableToAnimation(AnimationItem item)
    {
        if(tab != null)
        {
            DefaultTableModel tb = (DefaultTableModel)tab.getModel();
            int i = 0;
            tb.setRowCount(0);
            instance.currentAnimNameHeader.setText(item.animationName);
            for(ImageComponent ig : item.imgComponents)
            {
                addRow(ig.imgName);
            }
        }
    }

    public void Destroy()
    {
        dialog.dispose();
    }
}