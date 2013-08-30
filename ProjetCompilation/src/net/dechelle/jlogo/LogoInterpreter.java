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

import java.util.List;

import net.dechelle.jlogo.ui.JLogoPanel;

import LOnGOal.parser.Primitive;
import LOnGOal.parser.PrimitiveMap;


public class LogoInterpreter {

	private Turtle turtle;
	private PrimitiveMap primitives;
	
	public LogoInterpreter(JLogoPanel ui, PrimitiveMap map)
	{
		turtle = new Turtle(ui);
		//primitives = new PrimitiveMap();
		this.primitives = map;
		//AllPrimitives.init(primitives);
	}

	public Turtle getTurtle()
	{
		return turtle;
	}
	
	@SuppressWarnings("rawtypes")
	public Object execute(String name, List args) throws NoSuchPrimitiveException
	{
		Primitive p = primitives.get(name);
		
		if (p == null)
			throw new NoSuchPrimitiveException(name);
		
		return p.execute(this, args);	
	}

}
