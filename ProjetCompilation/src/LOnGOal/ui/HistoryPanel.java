package LOnGOal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import LOnGOal.parser.Parser;
import LOnGOal.util.ScriptProcess;

@SuppressWarnings("serial")
public class HistoryPanel extends JPanel implements ActionListener
	{
		public static boolean scriptMode = false;
		JPanel commandPanel = null, displayPanel = null;
		JButton runButton, clearButton;
		DefaultListModel model;
		JList <String> list;
		JScrollPane scrollPane;
		Parser parser;
		List<String> script = null;

		public HistoryPanel(Parser parser)
		{
			this.parser = parser;
			this.setLayout(new BorderLayout());
			//createCommandPanel();
			model = new DefaultListModel();
			
			//createDisplayPanel();
			this.setPreferredSize(new Dimension(200, 400));
		}
		
		protected void importScript(File file)
		{
			
		//	String [] script = {"av 100 td 90 av 100" , "av 100 av 100", "av 100 td 90 av 100"};
			int rep = 0;
			if (model.getSize() > 0)
				rep = JOptionPane.showConfirmDialog(null, "Ceci effacera l'historique des commandes, etes vous sure ?");
			if (rep == 0 )
			{
				script = ScriptProcess.readScript(file);
				model.clear();
				for (String el : script)
					addToHistory(el);
			}
		}
		
		protected void exportScript(File file)
		{
		//	String [] script = {"av 100 td 90 av 100" , "av 100 av 100", "av 100 td 90 av 100"};
			ScriptProcess.writeScript(file, model);
		}
		
		public void createCommandPanel()
		{
			commandPanel = new JPanel();
			runButton = new JButton("Lancer");
			runButton.addActionListener(this);
			clearButton = new JButton("Effacer");
			clearButton.addActionListener(this);
			commandPanel.add(new JSeparator(SwingConstants.VERTICAL));
			commandPanel.add(runButton);
			commandPanel.add(clearButton);
			this.add(BorderLayout.NORTH,commandPanel);	
		}
		
		public File chooseScript()
		{
			JFileChooser defautChooser = new JFileChooser();
			if (defautChooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION)
				return defautChooser.getSelectedFile();
			return null;
		}
		
		private void createDisplayPanel()
		{
			displayPanel = new JPanel();
	//		displayPanel.setPreferredSize(new Dimension(600, 400));
			displayPanel.setLayout(new BorderLayout());
			list = new JList<String>(model);

			    MouseListener mouseListener = new MouseAdapter() {
			      public void mouseClicked(MouseEvent mouseEvent) {
			        JList theList = (JList) mouseEvent.getSource();
			        if (mouseEvent.getClickCount() == 2) {
			          int index = theList.locationToIndex(mouseEvent.getPoint());
			          if (index >= 0) {
			            Object o = theList.getModel().getElementAt(index);
			            String cmd [] =  o.toString().split(" ");
			            String element_minuscule = cmd[0];
			            List<String> listArgs = new ArrayList<String>();
			            for(int i=1; i<cmd.length; i++)
			            	listArgs.add(cmd[i]);
			            for(String test : listArgs)
			            	System.out.println("for : " + test);
			            parser.runPrimitive(parser.getIndicePrimitives(element_minuscule), listArgs);
			            addToHistory(element_minuscule + " " + listArgs.toString().replaceAll("\\[|\\]",""));
			            System.out.println("Double-clicked on: " + o.toString());
			          }
			        }
			      }
			    };
			    list.addMouseListener(mouseListener);
			/*list.addMouseListener(new MouseAdapter(){
	            @Override
	            public void mouseClicked(MouseEvent e){
	            	if(e.getClickCount() == 2)
	            		System.out.println("element selectionné : " + list.getSelectedIndex()); //getSelectedValue());
	            }
	        });*/
			scrollPane = new JScrollPane(list);
			//model = new DefaultListModel();
			/*list = new JList<String>(model);
			list.addMouseListener(new MouseAdapter(){
	            @Override
	            public void mouseClicked(MouseEvent e){
	            	if(e.getClickCount() == 2)
	            		System.out.println("element selectionné : " + list.getSelectedValue());
	            }
	        });
			scrollPane = new JScrollPane(list);*/
			//for (int i = 0; i < 100; i++)
			//      model.addElement("Element " + i);
			
			
			//history = new ArrayList<JButton>();
			
			
			/*for (int i=0;i<100;i++)
			{
				JButton button =  new JButton("bouton " + i);
				button.setBackground(Color.white);
				button.setHorizontalAlignment(SwingConstants.LEFT);
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setPreferredSize(new Dimension(40,40));
				button.setSize(new Dimension(40,40));
				history.add(button);
			}
			
			for (int i=0; i<history.size(); i++)
				displayPanel.add(history.get(i), BorderLayout.CENTER);
			*/
			displayPanel.setPreferredSize(new Dimension(600 , 400));
			
			scrollPane = new JScrollPane(list);
			this.add(BorderLayout.CENTER,scrollPane);
			
			/*JScrollPane scroller = new JScrollPane();
			scroller.setViewportView(displayPanel);
			this.add(BorderLayout.CENTER,scroller);*/
			
		}
		
		public void addToHistory(String cmd)
		{
			model.addElement(cmd);
			if(displayPanel != null)
				this.remove(displayPanel);
			if(commandPanel == null)
				createCommandPanel();
			createDisplayPanel();
			this.updateUI();
			/*this.repaint();
			displayPanel.repaint();
			displayPanel.updateUI();*/
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() == runButton)
			{
				/*if(list.getSelectedIndex() == -1) 
				{*/
					if(script == null)
					{
						script = new ArrayList<String>();
						for (int i=0; i<model.size(); i++)
						{
							script.add((String) model.get(i));
						}
					}
					System.err.println(script);
					scriptMode = true;
					for (String el : script)
					{
						parser.decoupe(el);
						/*try{
							Thread.sleep(4000);
						}
						catch(InterruptedException ie){
						}*/
					}
					scriptMode = false;
				//}
			/*	else
				{
					String cmd = (String) model.get(list.getSelectedIndex());
				}*/
			}
			
			if (arg0.getSource() == clearButton)
			{
				model.clear();
				if(displayPanel != null)
					this.remove(displayPanel);
				createDisplayPanel();
				this.updateUI();
			}
		}
}
