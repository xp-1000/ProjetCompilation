package LOnGOal.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import LOnGOal.parser.LogoParser;
import LOnGOal.parser.Parser;

import net.dechelle.jlogo.ui.JLogoPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener 
{	
	//LogoParser parser;
	Parser parser;
	JPanel mainPanel, commandPanel;
	HistoryPanel historyPanel;
	JTextField cmd;
	JButton buttonRun, buttonClear;
	File currentFile;
	JMenuItem itemQuitter, itemImporter, itemExporter, itemSauvegarder;
	
	//public GUI (String parName,JLogoPanel graphicPanel, LogoParser parser)
	public GUI (String parName,JLogoPanel graphicPanel, Parser parser)
	{
		super(parName);
		
		this.parser = parser;

		
		// Default configuration of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600,400));
		this.setResizable(false);
		this.setJMenuBar(this.createMenus());
				
		this.createPanels(graphicPanel);
		parser.setHistoryPanel(historyPanel);
		
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
		cmd.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	cmd.setText("");
            }
        });
		this.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		        cmd.requestFocus();
		    }
		}); 
		
		KeyListener keyListener = new KeyListener() 
		{
		    public void keyPressed(KeyEvent keyEvent) {
		    	if(keyEvent.getKeyCode() == 10)
		    	{
		    		runCommand(cmd.getText());
		    		cmd.setText("");
		    	}
				if(cmd.getText().equals("Tapez votre commande ici"))
					cmd.setText("");
		    }
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		};
		cmd.addKeyListener(keyListener);
		buttonRun = new JButton("Executer");
		buttonClear = new JButton("Effacer");
		buttonRun.addActionListener(this);
		buttonClear.addActionListener(this);
		commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout());
		commandPanel.add(BorderLayout.NORTH, new JSeparator(SwingConstants.HORIZONTAL));
		commandPanel.add(BorderLayout.CENTER,cmd);
		commandPanel.add(BorderLayout.EAST,buttonRun);
		commandPanel.add(BorderLayout.WEST,buttonClear);
		
		mainPanel.add(BorderLayout.SOUTH, commandPanel);
		historyPanel = new HistoryPanel(parser);
		mainPanel.add(BorderLayout.EAST,historyPanel);
	}
	
	private void runCommand(String cmd)
	{
		System.out.println("???????????????run command : " + cmd);
		System.out.println(parser.decoupe(cmd));
	}
	private JMenuBar createMenus()
	{
		JMenuBar menuBar;
		JMenu menuFichier, menuEdition;
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menuFichier = new JMenu("Fichier");
		menuBar.add(menuFichier);

		//a group of JMenuItems
		itemQuitter = new JMenuItem("Quitter");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(
		//        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		//itemQuitter.getAccessibleContext().setAccessibleDescription(
		//        "This doesn't really do anything");
		itemQuitter.addActionListener(this);
		menuFichier.add(itemQuitter);

		menuEdition = new JMenu("Edition");
		menuBar.add(menuEdition);
		
		itemImporter = new JMenuItem("Importer");
		itemImporter.addActionListener(this);
		menuEdition.add(itemImporter);
		itemExporter = new JMenuItem("Exporter");
		itemExporter.addActionListener(this);
		menuEdition.add(itemExporter);
		itemSauvegarder = new JMenuItem("Sauvegarder");
		itemSauvegarder.addActionListener(this);
		menuEdition.add(itemSauvegarder);
		
		//menuItem = new JMenuItem("Both text and icon",
		//                         new ImageIcon("images/middle.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_B);
		//menu.add(menuItem);

		//menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_D);
		//menu.add(menuItem);

		//a group of radio button menu items

		//a submenu
		//menu.addSeparator();
		//menuQuitter = new JMenu("Quitter");
		//menuQuitter.setMnemonic(KeyEvent.VK_S);
		//menuFichier.add(menuQuitter);
		//menuItem = new JMenuItem("Fichier");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(
		//        KeyEvent.VK_2, ActionEvent.ALT_MASK));
		//menuQuitter.add(menuItem);

		/*menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);*/

		//Build second menu in the menu bar.
		/*menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
		        "This menu does nothing");
		menuBar.add(menu);*/

		return menuBar;
	}

	public void actionPerformed(ActionEvent evt) 
	{
		if(evt.getSource() == buttonClear)
		{
			cmd.setText("");
		}
		
		if(evt.getSource() == buttonRun)
		{
			runCommand(cmd.getText());
			/*StringTokenizer st = new StringTokenizer(cmd.getText());
			List<String> instruction = new ArrayList<String>();
			while (st.hasMoreTokens()) 
				instruction.add(st.nextToken()); 
			parser.parse(instruction);*/
		}
		
		if(evt.getSource() == itemQuitter)
			System.exit(0);
		
		if(evt.getSource() == itemExporter)
		{
			File file = historyPanel.chooseScript();
			if (file != null)
				historyPanel.exportScript(file);
		}
		
		if(evt.getSource() == itemSauvegarder)
		{
			if (currentFile != null)
				historyPanel.exportScript(currentFile);
			else
				JOptionPane.showMessageDialog(null, "Aucune script déjà importé");
		}
		
		if(evt.getSource() == itemImporter)
		{
			currentFile = historyPanel.chooseScript();
			if (currentFile != null)
				historyPanel.importScript(currentFile);
		}	
	}
	
	public void addToHistory(String cmd)
	{
		historyPanel.addToHistory(cmd);
	}
}
