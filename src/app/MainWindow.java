package app;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.SystemColor;

public class MainWindow extends JFrame 
{
	public static Color darkerGray = new Color(0x1e1e1e);
	public static MainWindow mainRef;
	public static enum MODE
    {
        POINTER,
        ANCHOR,
        HITBOX,
        HURTBOX 
    }
	

    public static void handleError()
    {
    	
	}
	

	


    public static MODE currentMode;


	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					MainWindow frame = new MainWindow();
					MainWindow.mainRef = frame;
			        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			        frame.getContentPane().setBackground(Color.BLACK);
			        frame.getContentPane().setForeground(darkerGray);
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() 
	{
		
		setForeground(Color.BLACK);
		setTitle("HitboxEditor by Hipreme");
		
		
		final MainWindow window = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 609, 476);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(SystemColor.textHighlight);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0xccccff), 2, true));
		menuBar.setBackground(Color.DARK_GRAY);
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setForeground(Color.WHITE);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setForeground(Color.WHITE);
		mntmSave.setBackground(Color.DARK_GRAY);
		mnNewMenu.add(mntmSave);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.setForeground(Color.WHITE);
		mntmLoad.setBackground(Color.DARK_GRAY);
		mnNewMenu.add(mntmLoad);
		contentPane = new JPanel();
		contentPane.setForeground(darkerGray);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		
		final ImportedView scrollPane = new ImportedView();
		scrollPane.panel.setBackground(Color.DARK_GRAY);
		scrollPane.setBackground(Color.GRAY);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 13;
		
		contentPane.add(scrollPane, gbc_scrollPane);
		
		
		
		JButton btnNewButton = new JButton("Import Images");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				File[] f = CrossPlatformFunctions.crossPlatformSelectMulti("Select images/frames to create hitbox", "png");
				if(f != null && f.length != 0)
					scrollPane.addImportedImages(f);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Hitbox");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(Color.DARK_GRAY);
		
		btnNewButton_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				MainWindow.currentMode = MainWindow.MODE.HITBOX;
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 1;
		contentPane.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton btnHurtbox = new JButton("Hurtbox");
		btnHurtbox.setBackground(Color.DARK_GRAY);
		btnHurtbox.setForeground(Color.WHITE);
		btnHurtbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				window.currentMode = MainWindow.MODE.HURTBOX;
			}
		});
		GridBagConstraints gbc_btnHurtbox = new GridBagConstraints();
		gbc_btnHurtbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHurtbox.insets = new Insets(0, 0, 5, 5);
		gbc_btnHurtbox.gridx = 0;
		gbc_btnHurtbox.gridy = 2;
		contentPane.add(btnHurtbox, gbc_btnHurtbox);
		
		JButton btnNewButton_1 = new JButton("Anchor");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.DARK_GRAY);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				window.currentMode = MainWindow.MODE.ANCHOR;
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 3;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnPointer = new JButton("Pointer");
		btnPointer.setBackground(Color.DARK_GRAY);
		btnPointer.setForeground(Color.WHITE);
		btnPointer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				window.currentMode = MainWindow.MODE.POINTER;
			}
		});
		GridBagConstraints gbc_btnPointer = new GridBagConstraints();
		gbc_btnPointer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPointer.insets = new Insets(0, 0, 5, 5);
		gbc_btnPointer.gridx = 0;
		gbc_btnPointer.gridy = 4;
		contentPane.add(btnPointer, gbc_btnPointer);
		
	}
	
    public void drawOptions()
    {
        ImageComponent img = ImportedView.currentSelected;
        

    }

}
