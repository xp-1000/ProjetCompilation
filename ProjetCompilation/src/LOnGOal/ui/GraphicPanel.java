package LOnGOal.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.Transient;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicPanel extends JPanel
{
	private Dimension dimension;
	private Image offScreen;

	public GraphicPanel(int width, int height)
	{
		
		this.dimension = new Dimension(width,height);
		/*this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, new JPanel());*/
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				offScreen = null;
			}
		});
		
		offScreen = null;		
	}
	
	@Override
	@Transient
	public Dimension getMinimumSize()
	{
		return dimension;
	}	

	@Override
	@Transient
	public Dimension getPreferredSize()
	{
		return dimension;
	}

	private Image getImage()
	{
		if (offScreen == null)
			offScreen = createImage((int)dimension.getWidth(),(int)dimension.getHeight());
		
		
		return offScreen;
	}
	
	public void drawLine(Color color, double x0, double y0, double x1, double y1)
	{
		int ix0, iy0, ix1, iy1;
		
		
		Graphics g = getImage().getGraphics();
		
		g.setColor(color);
		
		ix0 = (int)x0 + getWidth() / 2;
		iy0 = (int)y0 + getHeight() / 2;
		ix1 = (int)x1 + getWidth() / 2;
		iy1 = (int)y1 + getHeight() / 2;
				
		g.drawLine(ix0, iy0, ix1, iy1);
		
		g.dispose();
		
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawImage(getImage(), 0, 0, this);
	}
}
