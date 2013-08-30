package LOnGOal.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.dechelle.jlogo.LogoInterpreter;


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
	
	private PrimitiveMap primitives;
	private LogoInterpreter interpreter; 
	
	public LogoParser(PrimitiveMap map, LogoInterpreter interpreter)
	{
		this.primitives = map;
		this.interpreter = interpreter;
	}
	
	/*public void parse(List<String> script)
	{
		System.err.println(primitives.get("AV"));
		
		String instruction = new String();
		boolean working = false;
		Iterator<String> itScript = script.iterator();
		while (itScript.hasNext()) 
	    {
	    	String word = itScript.next();
	    	//if(this.isCommand(word))
	    	if(primitives. get(word))
	    	{
	    		if(working)
	    		{
	    			this.evalCmd(instruction);
	    			instruction = new String();
	    			working = false;
	    		}
	    		System.out.println(word + " est une commande");
	    	}
	    	else if(this.isOperation(word))
	    		System.out.println(word + " est une opération");
	    	else
	    		System.out.println(word + " n'est ni une commande ni une opération");
	    	if(working)
	    		instruction += " " + word;
	    	else
	    	{
	    		instruction += word;
	    		working = true;
	    	}
	    }
		this.evalCmd(instruction);
		while (itScript.hasNext()) 
	    {
			String word = itScript.next();
			if (primitives.get(word) != null)
			{
				instruction = word;
				for (int i=0; i<primitives.getArgCount(word); i++)
				{
					instruction += itScript.next();
				}
				System.out.println(instruction);
			}
	    }
		
	}
	
	private void evalCmd(String cmd)
	{
		System.out.println("Appel \"" + cmd + "\" par le parser");
	}
	*/
	// Nouvel Algo :
	
	/*public List<String> parse(List<String> script)
	{
		splitInstruction(script);
		
		return null;
	}
	
	private void splitInstruction(List<String> script)
	{
		System.out.println("*** DECOUPAGE DES INSTRUCTIONS ***");
		Iterator<String> itScript = script.iterator();
		while (itScript.hasNext()) 
	    {
			String primitive = itScript.next();
			System.out.println("Traitement du token " + primitive);
			if (primitives.get(primitive) != null)
			{
				System.out.println("Evaluation de la primitive " + primitive);
				this.eval(primitives.get(primitive), itScript);
			}
			else
			{
				System.out.println("ERREUR : token innattendu � la place d'une primitive");
			}
	    }
	}
	
	private Object eval(Primitive primitive, Iterator<String> itScript)
	{
		List<String> argsToEvaluate = new ArrayList<String>();
		// for (int i=0; i<primitives.getArgCount(word); i++)
		for (int i=0; i<primitive.getArgCount(); i++)
			if(itScript.hasNext())
				argsToEvaluate.add(itScript.next());
		List<Object> args = new ArrayList<Object>();
		for (Object token : argsToEvaluate)
			if (primitives.get(token.toString()) != null)
				args.add(eval(primitives.get(token.toString()), itScript));
			else
				args.add(token.toString());
		return primitive.execute(interpreter, args);
	}
	*/
}
