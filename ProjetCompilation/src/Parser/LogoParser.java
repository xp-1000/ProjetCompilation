package Parser;

import java.util.ArrayList;
import java.util.Iterator;


public class LogoParser 
{
	/* INFOS LOGO :
	 * 2 type d'objets : mots (chaines de caractères) et listes (suites ordonnées d'objets)
	 * Impératif dans une ligne : instruction
	 * Instruction = 	CommandeUnique 	Objet 	Opération1 .. 	Opération2 
	 * Exemple :     	ECRIS          	5     	+             	5        	=>		out = 10
	 * 
	 * 
	 * 
	 */

	// Collecteurs par type de mots
	ArrayList<String> lsCommands, lsOperations;
	// Collecteur de l'hisotrique des commandes
	ArrayList<String> CommandsHistory;
	
	public LogoParser()
	{
		lsCommands = new ArrayList<String>();
		lsOperations = new ArrayList<String>();
		
		lsCommands.add("ECRIS"); lsCommands.add("AVANCE");
		lsOperations.add("-"); lsOperations.add("+");
	}
	
	public void parse(ArrayList<String> script)
	{
		String cmd = new String();
		boolean working = false;
		Iterator<String> itScript = script.iterator();
		while (itScript.hasNext()) 
	    {
	    	String word = itScript.next();
	    	if(this.isCommand(word))
	    	{
	    		if(working)
	    		{
	    			this.evalCmd(cmd);
	    			cmd = new String();
	    			working = false;
	    		}
	    		System.out.println(word + " est une commande");
	    	}
	    	else if(this.isOperation(word))
	    		System.out.println(word + " est une opération");
	    	else
	    		System.out.println(word + " n'est ni une commande ni une opération");
	    	if(working)
	    		cmd += " " + word;
	    	else
	    	{
	    		cmd += word;
	    		working = true;
	    	}
	    }
		this.evalCmd(cmd);
	}
	
	public boolean isCommand(String arg)
	{
		Iterator<String> itCommands = lsCommands.iterator();
		while (itCommands.hasNext()) 
	    {
	    	if(itCommands.next() == arg)
	    		return true;
	    }
		return false;
	}
	
	public boolean isOperation(String arg)
	{
		Iterator<String> itOperations = lsOperations.iterator();
		while (itOperations.hasNext()) 
	    {
	    	if(itOperations.next() == arg)
	    		return true;
	    }
		return false;
	}
	
	private void evalCmd(String cmd)
	{
		System.out.println("Appel \"" + cmd + "\" par le parser");
	}
	
}
