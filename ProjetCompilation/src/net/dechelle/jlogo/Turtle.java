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

package net.dechelle.jlogo;

import java.awt.Color;

import net.dechelle.jlogo.ui.JLogoPanel;

public class Turtle {

	enum TurtleState {
		PEN_UP,
		PEN_DOWN,
	};

	private TurtleState state;
	private double prevX, prevY;
	private double x, y;
	private double theta;
	private Color penColor;
	private JLogoPanel ui;

	public Turtle(JLogoPanel ui)
	{
		state = TurtleState.PEN_DOWN;
		prevX = 0.0;
		prevY = 0.0;
		x = 0.0;
		y = 0.0;
		theta = 0.0;
		penColor = Color.BLACK;
		// Pour partir vers le haut
		leftTurn(90);
		this.ui = ui;
	}
	
	private void move(double r, double sign)
	{
	    prevX = x;
	    prevY = y;
	    x += sign * r * Math.cos(theta);
	    y += sign * r * Math.sin(theta);

	    if (state == TurtleState.PEN_DOWN)
	    	ui.drawLine(penColor, prevX, prevY, x, y);
	}
	
	public void forward(double r)
	{
		move(r, 1.0);
	}

	public void backward(double r)
	{
		move(r, -1.0);
	}
	
	private void turn(double alpha, double sign)
	{
		theta += sign * 2.0 * Math.PI * alpha / 360.0;
	}
	   
	public void rightTurn(double alpha)
	{
		turn(alpha, 1.0);
	}

	public void leftTurn(double alpha)
	{
		turn(alpha, -1.0);
	}

	public void penUp()
	{
		state = TurtleState.PEN_UP;
	}

	public void penDown()
	{
		state = TurtleState.PEN_DOWN;
	}

	public void origin()
	{
		prevX = 0.0;
		prevY = 0.0;
		x = 0.0;
		y = 0.0;
	}
	
	public void reInit()
	{
		origin();
		clear();
	}
	
	public void clear()
	{
		System.out.println("clear");
		ui.clear();

	}

	public void changePenColor(int rgb)
	{
		penColor = translateColor(rgb);
	//	penColor = Color.red;
	}

	public void changeBackgroundColor(int rgb)
	{
		penColor = translateColor(rgb);
		ui.changeBackground(penColor);
		//ui.updateUI();
	}
	
	private Color translateColor(int rgb)
	{
		switch(rgb)
		{
		case 0: 
			return Color.black;
		case 1: 
			return Color.blue;
		case 2: 
			return Color.red;
		case 3: 
			return Color.green;
		case 4: 
			return Color.yellow;
		case 5: 
			return Color.MAGENTA;
		default :
			return Color.black;
		}
	}
	
	@Override
	public String toString() {
		return "Turtle [state=" + state + ", prevX=" + prevX + ", prevY="
				+ prevY + ", x=" + x + ", y=" + y + ", theta=" + theta
				+ ", penColor=" + penColor + "]";
	}

}
