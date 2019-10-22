package app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import app.Animation.AnimationMenu;
import app.Animation.AnimationViewer;
import app.base.FilterableOptionsView;
import app.file.Loader;
import app.file.Saver;
import app.global.Globals;
import app.global.KeyChecker;
import app.global.UIDefaults;

public class MainWindow extends JFrame 
{
    public static MainWindow mainRef;
    public static String scheduledProject = "";


	public static String PROJECT_PATH = null;
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

    public static MODE currentMode = MODE.POINTER;


	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
        if(args.length > 0)
        {
            if(!args[0].equals("") && Files.exists(Paths.get(args[0])))
                scheduledProject = Paths.get(args[0]).toString();
        }
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				UIDefaults.setUIDefaults();
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					MainWindow frame = new MainWindow();
					MainWindow.mainRef = frame;
			        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			        frame.getContentPane().setBackground(Color.BLACK);
			        frame.getContentPane().setForeground(UIDefaults.DARKER_GRAY);
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
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0xccccff), 2, true));
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

        JMenuItem mntmSave = new JMenuItem("Save");
        Globals.addHotkey(mntmSave, KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		mnNewMenu.add(mntmSave);

		JMenuItem mntmLoad = new JMenuItem("Load");
        Globals.addHotkey(mntmLoad, KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        mnNewMenu.add(mntmLoad);

        JMenu mnNewMenuView = new JMenu("View");
		menuBar.add(mnNewMenuView);

		AnimationMenu.addTo(menuBar);

		JMenuItem mntmAnimationView = new JMenuItem("Animation View");
		mnNewMenuView.add(mntmAnimationView);

		
		AnimationViewer.startAnimationView();
	

        mntmAnimationView.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FilterableOptionsView.startFilterableOptionsView(window);
            }
        });


		contentPane = new JPanel();
		KeyChecker.start((JComponent)contentPane);
		Globals.addKeyListener(contentPane, "alt ALT", "altFocus", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				menuBar.getMenu(0).doClick();
			}
		});

		contentPane.setForeground(UIDefaults.DARKER_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);


		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		final Editor editor = new Editor();
		//editor.panel.setBorder(null);
		//editor.panel.setBa
		editor.panel.setBackground(Color.DARK_GRAY);
		editor.setBackground(Color.GRAY);
		GridBagConstraints gbc_editor = new GridBagConstraints();
		gbc_editor.fill = GridBagConstraints.BOTH;
		gbc_editor.gridx = 1;
		gbc_editor.gridy = 1;

		contentPane.add(editor, gbc_editor);


        final ImportedView scrollPane = new ImportedView(editor);
        scrollPane.tryLoadOperation(MainWindow.scheduledProject);
		scrollPane.panel.setBackground(Color.DARK_GRAY);
		scrollPane.setBackground(Color.GRAY);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 13;

		contentPane.add(scrollPane, gbc_scrollPane);



		JButton btnNewButton = new JButton("Import Images");
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
		btnHurtbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				MainWindow.currentMode = MainWindow.MODE.HURTBOX;
			}
		});
		GridBagConstraints gbc_btnHurtbox = new GridBagConstraints();
		gbc_btnHurtbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHurtbox.insets = new Insets(0, 0, 5, 5);
		gbc_btnHurtbox.gridx = 0;
		gbc_btnHurtbox.gridy = 2;
		contentPane.add(btnHurtbox, gbc_btnHurtbox);

		JButton btnNewButton_1 = new JButton("Anchor");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				MainWindow.currentMode = MainWindow.MODE.ANCHOR;
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 3;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);

		JButton btnPointer = new JButton("Pointer");
		btnPointer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				MainWindow.currentMode = MainWindow.MODE.POINTER;
			}
		});
		GridBagConstraints gbc_btnPointer = new GridBagConstraints();
		gbc_btnPointer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPointer.insets = new Insets(0, 0, 5, 5);
		gbc_btnPointer.gridx = 0;
		gbc_btnPointer.gridy = 4;
		contentPane.add(btnPointer, gbc_btnPointer);

		mntmSave.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				Saver.saveProject(scrollPane);
			}
		});

		mntmLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Loader.loadProject(CrossPlatformFunctions.crossPlatformSelect("Load project file", "*.json"), scrollPane);
			}
		});

	}

    public void drawOptions()
    {
        ImageComponent img = ImportedView.currentSelected;


    }

}