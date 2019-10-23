package app;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;

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
		setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/app/icon.png")));

		setForeground(Color.BLACK);
		setTitle("HitboxEditor by Hipreme");		

		final MainWindow window = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 948, 597);

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
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
														                        
            JPanel buttonContainer = new JPanel();
            contentPane.add(buttonContainer);
            buttonContainer.setAlignmentX(0.0f);
            buttonContainer.setAlignmentY(Component.TOP_ALIGNMENT);
            
            
            
            		JButton btnImport = new JButton("Import Images");
            		
			
					JButton btnHitbox = new JButton("Hitbox");
					btnHitbox.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							MainWindow.currentMode = MainWindow.MODE.HITBOX;
						}
					});
					
							JButton btnHurtbox = new JButton("Hurtbox");
							btnHurtbox.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{
									MainWindow.currentMode = MainWindow.MODE.HURTBOX;
								}
							});
							
									JButton btnNewButton_1 = new JButton("Anchor");
									btnNewButton_1.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) 
										{
											MainWindow.currentMode = MainWindow.MODE.ANCHOR;
										}
									});
									
											JButton btnPointer = new JButton("Pointer");
											GroupLayout gl_buttonContainer = new GroupLayout(buttonContainer);
											gl_buttonContainer.setAutoCreateContainerGaps(true);
											gl_buttonContainer.setHonorsVisibility(false);
											int btnImpWid = btnImport.getPreferredSize().width;
											gl_buttonContainer.setHorizontalGroup(
												gl_buttonContainer.createParallelGroup(Alignment.LEADING)
													.addComponent(btnImport, GroupLayout.PREFERRED_SIZE, btnImpWid, GroupLayout.PREFERRED_SIZE)
													.addComponent(btnHitbox, GroupLayout.PREFERRED_SIZE, btnImpWid, GroupLayout.PREFERRED_SIZE)
													.addComponent(btnHurtbox, GroupLayout.PREFERRED_SIZE, btnImpWid, GroupLayout.PREFERRED_SIZE)
													.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, btnImpWid, GroupLayout.PREFERRED_SIZE)
													.addComponent(btnPointer, GroupLayout.PREFERRED_SIZE, btnImpWid, GroupLayout.PREFERRED_SIZE)
											);
											gl_buttonContainer.setVerticalGroup(
												gl_buttonContainer.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_buttonContainer.createSequentialGroup()
														.addComponent(btnImport, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(btnHitbox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(btnHurtbox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(btnPointer, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
											);
											buttonContainer.setLayout(gl_buttonContainer);
											btnPointer.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) 
												{
													MainWindow.currentMode = MainWindow.MODE.POINTER;
												}
											});
            												
            
            JPanel panel = new JPanel();
            contentPane.add(panel);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            		final Editor editor = new Editor();
            		panel.add(editor);
            		//editor.panel.setBorder(null);
            		//editor.panel.setBa
            		editor.panel.setBackground(Color.DARK_GRAY);
            		editor.setBackground(Color.GRAY);
            		final ImportedView scrollPane = new ImportedView(editor);
            		scrollPane.tryLoadOperation(MainWindow.scheduledProject);
            		panel.add(scrollPane);
            		btnImport.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							File[] f = CrossPlatformFunctions.crossPlatformSelectMulti("Select images/frames to create hitbox", "png");
							if(f != null && f.length != 0)
								scrollPane.addImportedImages(f);
						}
					});

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