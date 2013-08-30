package LOngGOal.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class HistoryPanel extends JPanel
	{
		JPanel commandPanel, displayPanel;
		JButton runButton, stopButton;
		ArrayList<JButton> history;

		public HistoryPanel()
		{
			
			this.setLayout(new BorderLayout());
			createCommandPanel();
			createDisplayPanel();
			this.setPreferredSize(new Dimension(200, 400));
			
		}
		
		private void createCommandPanel()
		{
			commandPanel = new JPanel();
			runButton = new JButton("Run");
			stopButton = new JButton("Stop");
			commandPanel.add(new JSeparator(SwingConstants.VERTICAL));
			commandPanel.add(runButton);
			commandPanel.add(stopButton);
			this.add(BorderLayout.NORTH,commandPanel);
			
		}
		
		private void createDisplayPanel()
		{
			displayPanel = new JPanel();
			displayPanel.setPreferredSize(new Dimension(600, 400));
			history = new ArrayList<JButton>();
			for (int i=0;i<40;i++)
				history.add(new JButton("bouton " + i));
			
			for (int i=0; i<history.size(); i++)
				displayPanel.add(history.get(i));
			
			JScrollPane scroller = new JScrollPane();
			scroller.setViewportView(displayPanel);
			this.add(BorderLayout.CENTER,scroller);
			
		}
}
