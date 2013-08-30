/***********************************************************************************
  	Copyright (C) François Déchelle (francois@dechelle.net), 2013.
	Tous droits réservés.
	La redistribution du code source, modifié ou non, sous forme de binaire 
	est soumise aux conditions suivantes :

 * Le copyright ci-dessus, la présente liste des conditions et 
    l'avertissement qui la suit doivent figurer dans le code source.
 * La documentation et/ou les fichiers accompagnant le code source distribué 
	sous forme de binaire doivent faire apparaître le copyright ci-dessus, 
	la présente liste des conditions et l'avertissement qui la suit.
 * Le nom des auteurs, pas plus que ceux des contributeurs, 
	ne sauraient être utilisés dans le but de promouvoir ou de légitimer un 
	produit dérivé de ce programme sans autorisation écrite préalable à cet effet.

	CE PROGRAMME EST FOURNI « TEL QU'EN L'ÉTAT » PAR LES AUTEURS ET CONTRIBUTEURS
	ET IL N'EST DONNÉ AUCUNE GARANTIE, IMPLICITE OU EXPLICITE, QUANT À SON 
	UTILISATION COMMERCIALE, PROFESSIONNELLE OU AUTRE. LES AUTEURS ET CONTRIBUTEURS
	NE PEUVENT EN AUCUN CAS ÊTRE TENUS POUR RESPONSABLES DE QUELQUE DOMMAGE OU 
	PRÉJUDICE DIRECT, INDIRECT, SECONDAIRE OU ACCESSOIRE (Y COMPRIS LES PERTES
	FINANCIÈRES DUES AU MANQUE À GAGNER, À L'INTERRUPTION D'ACTIVITÉS, OU 
	LA PERTE D'INFORMATIONS ET AUTRES) DÉCOULANT DE L'UTILISATION DU PROGRAMME,
	OU DE L'IMPOSSIBILITÉ D'UTILISER CELUI-CI, ET DONT L'UTILISATEUR ACCEPTE 
	L'ENTIÈRE RESPONSABILITÉ.
 ************************************************************************************
	Copyright (C) François Déchelle (francois@dechelle.net), 2013.
	All rights reserved.
	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright
  	notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
	notice, this list of conditions and the following disclaimer in the
 	documentation and/or other materials provided with the distribution.
 * Neither the name of the authors nor the names of the contributors 
 	may be used to endorse or promote products derived from this software 
 	without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE AUTHORS AND CONTRIBUTORS ``AS IS'' AND ANY
	EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
	DISCLAIMED. IN NO EVENT SHALL THE AUTHORS AND CONTRIBUTORS BE LIABLE FOR ANY
	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***********************************************************************************/

package net.dechelle.jlogo.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.Transient;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class JLogoPanel extends JPanel {

	private Dimension dimension;
	private Image offScreen;

	public JLogoPanel(int width, int height)
	{
		dimension = new Dimension(width, height);
		offScreen = null;

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				offScreen = newImage();
			}
		});
	}

	public JLogoPanel()
	{
		this(600, 400);
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

	private Image newImage()
	{
		int offScreenWidth = (offScreen != null) ? offScreen.getWidth(this) : 0;
		int newWidth = Math.max(getWidth(), offScreenWidth);
		int offScreenHeight = (offScreen != null) ? offScreen.getHeight(this) : 0;
		int newHeight = Math.max(getHeight(), offScreenHeight);

		Image img = createImage(newWidth, newHeight);
		Graphics g = img.getGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, newWidth, newHeight);

		if (offScreen != null) {
			int prevWidth = offScreen.getWidth(this);
			int x = (newWidth - prevWidth) / 2;
			int prevHeight = offScreen.getHeight(this);
			int y = (newHeight - prevHeight) / 2;

			g.drawImage(offScreen, x, y, this);
			g.dispose();
		}

		repaint();

		return img;
	}

	private Image getImage()
	{
		if (offScreen == null)
			offScreen = newImage();

		return offScreen;
	}

	private class DrawLineRunnable implements Runnable {

		private Color color;
		private double x0;
		private double y0;
		private double x1;
		private double y1;

		public DrawLineRunnable(Color color, double x0, double y0, double x1, double y1)
		{
			this.color = color;
			this.x0 = x0;
			this.y0 = y0;
			this.x1 = x1;
			this.y1 = y1;
		}

		@Override
		public void run()
		{
			Image img = getImage();
			int width = img.getWidth(JLogoPanel.this);
			int height = img.getHeight(JLogoPanel.this);
			int ix0 = (int)x0 + width / 2;
			int iy0 = (int)y0 + height / 2;
			int ix1 = (int)x1 + width / 2;
			int iy1 = (int)y1 + height / 2;

			Graphics g = img.getGraphics();
			g.setColor(color);
			g.drawLine(ix0, iy0, ix1, iy1);
			g.dispose();

			repaint();
		}
	}

	public void drawLine(Color color, double x0, double y0, double x1, double y1)
	{
		SwingUtilities.invokeLater(new DrawLineRunnable(color, x0, y0, x1, y1));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		int x = (getWidth() - getImage().getWidth(this)) / 2;
		int y = (getHeight() - getImage().getHeight(this)) / 2;

		g.drawImage(getImage(), x, y, this);
	}

}
