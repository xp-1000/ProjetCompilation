package LOngGOal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import net.dechelle.jlogo.ui.JLogoPanel;

public class GUI extends JFrame implements ActionListener 
{		
	JPanel mainPanel, commandPanel;
	HistoryPanel historyPanel;
	JTextField cmd;
	JButton buttonRun, buttonClear;
	
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	
	public GUI (String parName,JLogoPanel graphicPanel)
	{
		super(parName);
		// Default configuration of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600,400));
		this.setResizable(false);
		this.setJMenuBar(this.createMenus());
				
		this.createPanels(graphicPanel);
		
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		// Resizing the windows considering the new elements
		this.pack();
		// Showing the windows
		this.setVisible(true);
		//GraphicPanel graphicPanel = new GraphicPanel(500,600);
		
	}
	
	private void createPanels(JLogoPanel graphicPanel)
	{
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// Création d'un faux panel pour le contour (non fonctionnel sur graphicPanel)
		JPanel panelEggs = new JPanel();
		panelEggs.add(graphicPanel);
		panelEggs.setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		//mainPanel.add(BorderLayout.CENTER,graphicPanel);
		mainPanel.add(BorderLayout.CENTER,panelEggs);
		
		cmd = new JTextField("Tapez votre commande ici");
		buttonRun = new JButton("Send");
		buttonClear = new JButton("Clear");
		buttonRun.addActionListener(this);
		buttonClear.addActionListener(this);
		commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout());
		commandPanel.add(BorderLayout.NORTH, new JSeparator(SwingConstants.HORIZONTAL));
		commandPanel.add(BorderLayout.CENTER,cmd);
		commandPanel.add(BorderLayout.EAST,buttonRun);
		commandPanel.add(BorderLayout.WEST,buttonClear);
		
		mainPanel.add(BorderLayout.SOUTH, commandPanel);
		historyPanel = new HistoryPanel();
		mainPanel.add(BorderLayout.EAST,historyPanel);
	}
	
	private JMenuBar createMenus()
	{
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Fichier");
		//menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item",
		                         KeyEvent.VK_T);
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(
		//        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "This doesn't really do anything");
		menu.add(menuItem);

		//menuItem = new JMenuItem("Both text and icon",
		//                         new ImageIcon("images/middle.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_B);
		//menu.add(menuItem);

		//menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_D);
		//menu.add(menuItem);

		//a group of radio button menu items

		//a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(
		//        KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
		        "This menu does nothing");
		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent evt) 
	{
		if(evt.getSource() == buttonClear)
		{
			System.out.println("Clear");
		}
		
		if(evt.getSource() == buttonRun)
		{
			System.out.println("Send : " + cmd.getText());
		}
	}
}
