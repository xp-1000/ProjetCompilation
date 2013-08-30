package LOnGOal.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import net.dechelle.jlogo.LogoInterpreter;
import net.dechelle.jlogo.primitives.BackwardPrimitive;
import net.dechelle.jlogo.primitives.ChangeColorPrimitive;
import net.dechelle.jlogo.primitives.ClearPrimitive;
import net.dechelle.jlogo.primitives.FixePositionPrimitive;
import net.dechelle.jlogo.primitives.ForwardPrimitive;
import net.dechelle.jlogo.primitives.LeftTurnPrimitive;
import net.dechelle.jlogo.primitives.OriginePrimitive;
import net.dechelle.jlogo.primitives.PenDownPrimitive;
import net.dechelle.jlogo.primitives.PenUpPrimitive;
import net.dechelle.jlogo.primitives.ReInitPrimitive;
import net.dechelle.jlogo.primitives.RightTurnPrimitive;

import LOnGOal.ui.HistoryPanel;


public class Parser 
{
	protected static boolean renvoi_instruction = false; 
	public static Stack<String> calcul = new Stack<String>(); 
	protected static Stack<HashMap<String,String>> stockvariable = new Stack<HashMap<String,String>>();
	protected static boolean stop = false;
	protected static Stack<String> nom = new Stack<String>(); 
	public static Stack<String> en_cours = new Stack<String>(); 
	private InstructionBuffer instructionBuffer=new InstructionBuffer();
	public static StringBuffer actionInstruction=new StringBuffer();
	protected static HashMap<String,String> locale = new HashMap<String,String>();
	
	protected static boolean operande = false; 
	protected static boolean operateur = false;
	protected static boolean drapeau_ouvrante = false;
	protected static boolean drapeau_fermante = false; 
	public static String lineNumber="";
	
	private LogoInterpreter interpreter;
	private HistoryPanel history;
	private List<Integer> noToHistory;
	
	public Parser(LogoInterpreter interpreter)
	{
		this.interpreter = interpreter;
		noToHistory = new ArrayList<>();
		noToHistory.add(5); // repete
		noToHistory.add(30); // * 
		noToHistory.add(31); // /
		noToHistory.add(32); // + 
		noToHistory.add(33); // -
	}
	
	public void setHistoryPanel(HistoryPanel history)
	{
		this.history = history;
	}
	
	public String decoupe(String st) {  
		StringBuffer buffer = new StringBuffer();
		boolean espace=false;
		boolean backslash=false;
		boolean mot=false;
		int crochet_liste=0;

		for(int i=0;i<st.length();i++){
			char c=st.charAt(i);
			if (c==' ') {
				if (!espace&&buffer.length()!=0) {
					if (backslash) buffer.append("\\e");
					else {
						buffer.append(c);
						espace=true;
						mot=false;
					}
					backslash=false;
				}
			}
			else if(c=='\\'&&!backslash) {
				espace=false;
				backslash=true;
			}
			else if(c=='\"'){
				if (espace&&crochet_liste<=0){
					mot=true;
				}
				buffer.append(c);
				espace=false;
				backslash=false;
			}
			else if (c==':'){
				buffer.append(c);
				espace=false;
				backslash=false;
			}
			else if (c=='['||c==']'||c=='('||c==')'){
				if (backslash) {
					buffer.append("\\"+c);
					backslash=false;
				}
				else {
					if (c=='[') crochet_liste++;
					else if (c==']') crochet_liste--;
					if (espace||buffer.length()==0) {buffer.append(c+" ");espace=true;}
					else {
						buffer.append(" "+c+" ");
						mot=false;
						espace=true;
					}
				}
			}
			else if (c=='+'||c=='-'||c=='*'||c=='/'||c=='='||c=='<'||c=='>'||c=='&'||c=='|'){
				if (mot||crochet_liste>0) {
					buffer.append(c);
					if (espace) espace=false;
				}
				else { 
					String op=String.valueOf(c);
					if (c=='<'||c=='>'){
						if (i+1<st.length()){
							if (st.charAt(i+1)=='='){
								op+="=";
								i++;
							}
						}
					}
					if (espace) buffer.append(op+" ");
					else {
						espace=true;
						if (buffer.length()!=0) buffer.append(" "+op+" ");
						else buffer.append(op+" ");
					}
				}
			}
			else{
				if (backslash){
					if (c=='n')	buffer.append("\\n");
					else if (c=='\\') buffer.append("\\\\");
					else if (c=='#') buffer.append("\\#");
					else { 
						buffer.append(c);
					}
				}
				else {
					buffer.append(c);	
				}
				backslash=false;
				espace=false;
			}
		}
		try {
			return execute(buffer);
		} catch (myException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getIndicePrimitives(String el){
		return Integer.valueOf(	PrimitiveMap.primitives.get(el)).intValue()% PrimitiveMap.PRIMITIVE_NUMBER;
	}
	
	String execute(StringBuffer instructions) throws myException {
		System.err.println(instructions);
		if (!instructions.equals("")) {
			instructionBuffer.insertCode(instructions);
			
		}
		System.out.println("debut : instruction buffer : " + instructionBuffer);
		while (instructionBuffer.getLength()!= 0) {
			if (instructionBuffer.getLength() == 0)
				break;
			String element = instructionBuffer.getNextWord();
			String element_minuscule = element.toLowerCase();
			int i = -1;
			if (PrimitiveMap.primitives.containsKey(element_minuscule) || i > -1) {
				i = getIndicePrimitives(element_minuscule);
				Stack<String> param = new Stack<String>();
				if (isInfixedOperator(i)) { 
					System.out.println(element_minuscule + " - 1");
					deleteLineNumber();
					operateur = true;
					operande = false;
					if (calcul.isEmpty()) { 
						if (i != 32 && i != 33)
							System.err.println(element + "error.ne_peut_etre");
						if (nom.isEmpty())
							param.push("0");
						else {
							String st = nom.peek();
							if (!testoperateur(st))
								param.push("0");
							else if ("*/".indexOf(st) > -1) { 
								instructionBuffer.deleteFirstWord(element);
								if (st.equals("*"))
									instructionBuffer.insert("* ");
								else
									instructionBuffer.insert("/ ");
								if (i == 32)
									return ("1"); 
								else
									return ("-1"); 
							} else
								param.push("0");
						}
					} else if (nom.isEmpty()) {
						param.push(calcul.pop());
					} else {
						String st = nom.peek();
						if (testoperateur(st)) {
							if (prioriteinf(st, element)) {
								param.push(calcul.pop());
							} else
								return (calcul.pop());
						} else
							param.push(calcul.pop());
					}
				} else if (operande && i != 204) {
					System.out.println(element_minuscule + " - 2");
					checkParenthesis();
					operande = false;
					break;
				} 
				if (!element.equals("\n")) deleteLineNumber();
				instructionBuffer.deleteFirstWord(element);
				if (drapeau_ouvrante) {
					System.out.println(element_minuscule + " - 3");
					drapeau_ouvrante = false;
					int constantNumber = -1;
						if (i > -1)
							constantNumber = PrimitiveMap.numberArgs[i];
					nom.push(element);
					int j = 0;
					while (true) {
						if (constantNumber==0) break;
							operande = operateur = drapeau_ouvrante = false;
							if (instructionBuffer.getNextWord().equals(")")) {
								if (constantNumber != -1) {
									if (j > constantNumber){
											System.err.println("too_much_arguments");
										}
									else if (j < constantNumber)
										System.err.println("pas_assez_de"+ " " + nom.peek());
								}
								break;
							}
							String a = execute(new StringBuffer());
							
							param.push(a);
						j++;
					}
				}
				else {
					System.out.println(element_minuscule + " - 4");
					drapeau_ouvrante = false;
					int nbparametre = 0;
					if (i > -1){System.out.println("good1");
						nbparametre = PrimitiveMap.numberArgs[i];}
					int j = 0;
					System.out.println("good1 " + nom);
					nom.push(element);
					System.out.println("good1 "+ nom);
					System.out.println("nb : " + nbparametre);
					while (j < nbparametre) {
						System.out.println("boucle" + j);
							operande = operateur = drapeau_ouvrante = false;
							System.out.println("instruction buffer : " + instructionBuffer);
							String a = execute(new StringBuffer());
							param.push(a);
							System.out.println("sauveteur : " + a);
							j++;
					}
				nom.pop();
				System.out.println("ECECUTION de :" + element_minuscule+ " ++ " + i + " " + param);
			/*	if(element_minuscule.equals("repete"))
				{
					repete(param.toString());
				}
				else
				{*/
					List<String> args = new ArrayList<String>();
					for (String arg : param)
						args.add(arg);
					runPrimitive(i, args);
					if(history != null && history.scriptMode == false && mustBeInHistory(i) == true)
						history.addToHistory(element_minuscule + " " + args.toString().replaceAll("\\[|\\]",""));
						//history.addToHistory(element_minuscule + " " + args.toString().replaceAll("\\[|\\]",""));
				//}
					
				if (drapeau_fermante && !calcul.empty()) {
					System.out.println(element_minuscule + " - 5");
					drapeau_fermante = false;
					operande = false;
					return calcul.pop();
				}
				if (!operande) {
					System.out.println(element_minuscule + " - 6");
					if (renvoi_instruction) {
						System.out.println(element_minuscule + " - 6.1");
						renvoi_instruction = false;
					} else {
						System.out.println(element_minuscule + " - 6.2 " + nom);
						if (!nom.isEmpty()
								&& !nom.peek().equals("\n")) {
							System.out.println(element_minuscule + " - 6.2.1");
							if (!element.equals("\n")) {
								if (element.equals("\\")) {
									int offset=instructionBuffer.indexOf(" \\ ");
									instructionBuffer.delete(0, offset+1);
									System.out.println("pas_assez_de" + " " + nom.peek());
								}
								if (!nom.peek().equals("("))
									System.out.println(element + " " + "ne_renvoie_pas" + " " + nom.peek());
									
								
							}
						}
					}
				}
				else{
					System.out.println(element_minuscule + " - 7");
					if (!nom.isEmpty()&&nom.peek().equals("\n")) 
						System.out.println("error.whattodo" +" "+ calcul.peek()+" ?"); 			 
				}
			}}
			else if (element.substring(0, 1).equals(":")
					&& element.length() > 1) {
				if (operande) {
					checkParenthesis();
					operande = false;
					break;
				}
				else deleteLineNumber();
				String value = null;
				String variableName = element_minuscule.substring(1,element_minuscule.length());
				if (!locale.containsKey(variableName)) {
						System.out.println(variableName + " error.novalue");
				}
				else {
					value = locale.get(variableName);
				}

				if (null == value)
					System.out.println(variableName + "error.novalue");
				calcul.push(value);
				operande = true;
				operateur = false;
				drapeau_ouvrante = false;
				instructionBuffer.deleteFirstWord(element);
			} else {
				System.out.println(element_minuscule + " - 8");
				try {
					Double.parseDouble(element);
					boolean deleteEndZero=false;
					if (element.endsWith(".0")) {
						deleteEndZero=true;
						element = element.substring(0, element.length() - 2);
					}
					calcul.push(element);
					if (operande) {
						checkParenthesis();
						calcul.pop();
						operande = false;
						break;
					}
					else {deleteLineNumber();System.out.println(element_minuscule + " - 8.3");}
					operande = true;
					operateur = false;
					drapeau_ouvrante = false;
					 if (deleteEndZero) instructionBuffer.deleteFirstWord(element + ".0");
					else instructionBuffer.deleteFirstWord(element);
					
				} catch (NumberFormatException e) {
					if (element.equals("[")) {
						if (operande) {
							checkParenthesis();
							break;
						}
						else deleteLineNumber();
						operande = true;
						operateur = false;
						drapeau_ouvrante = false;
						instructionBuffer.deleteFirstWord(element);
						String a = chercheListe();
						calcul.push(a);
					}
					else if (element.equals("(")) {
						if (operande) {
							checkParenthesis();
							break;
						}
						else deleteLineNumber();
						drapeau_ouvrante = true;

						Parser.en_cours.push("(");
						int pos = chercheParenthese();
						if (pos == -1) {
								System.out.println("parenthese_fermante");
						}
						instructionBuffer.deleteFirstWord(element);
						Parser.en_cours.push("(");
					}
					else if (element.substring(0, 1).equals("\"")) {
						try {
							String el = element.substring(1);
							Double.parseDouble(el);
							calcul.push(el);
						} catch (NumberFormatException e1) {
							calcul.push(element);
						}
						if (operande) {
							checkParenthesis();
							calcul.pop();
							operande = false;
							break;
						}
						else deleteLineNumber();
						operande = true;
						operateur = false;
						drapeau_ouvrante = false;
						instructionBuffer.deleteFirstWord(element);
					}
					else if (element_minuscule.equals("pour")) {
						instructionBuffer.deleteFirstWord(element);
						if (instructionBuffer.getLength()!= 0)
							{
								element = instructionBuffer.getNextWord();
								element_minuscule=element.toLowerCase();
							}
						else
							System.out.println("pas_assez_de"+ " "+ "\""+ "pour" + "\"");
							String definition = "pour"
									+ " " + element + " ";
							instructionBuffer.deleteFirstWord(element);
							while (instructionBuffer.getLength() != 0) {
								element = instructionBuffer.getNextWord().toLowerCase();
								if (null == element)
									break;
								if (!element.substring(0, 1).equals(":")
										|| element.length() == 1)
									System.out.println(element + " pas_argument");
								definition += element + " ";
								instructionBuffer.deleteFirstWord(element);
							}
					}
					else if (element.startsWith("\\l")){
						if (operande) {
							break;	
						}
						instructionBuffer.deleteFirstWord(element);
						lineNumber=element+" ";
						element=instructionBuffer.getNextWord();
				
					}
					else {
						deleteLineNumber();
						initBuffer();
						throw new myException(element + " -> token non reconnu");
					}
				}
			}
		}
		System.out.println("1.nom" + nom);
		if (calcul.isEmpty()) {
			System.out.println("9");
			System.out.println("2.nom" + nom);
			if (!nom.isEmpty()) {
				while ((!nom.isEmpty()) && nom.peek().equals("\n"))
					nom.pop();
				
				if (!nom.isEmpty()) {
					System.out.println("!!pas_assez_de"+ " " + nom.peek());
				}
			}
		}
		if (!calcul.isEmpty()) {
			System.out.println("10");
			if ((!nom.isEmpty()) && nom.peek().equals("\n")) {
				String up = "";
				int id=0;
				while (!nom.isEmpty()&&nom.peek().equals("\n"))	{
					nom.pop();
					id++;
				}
				if (!nom.isEmpty())	{
					up=nom.peek().toString();
						System.out.println(en_cours.get(en_cours.size()-id) + " "+ "ne_renvoie_pas" + up);
				}
				else {
						System.out.println("error.whattodo"	+ " " + calcul.peek() + " ?");

				}
			}
			else {
				operande = false;
				String iii = calcul.pop();
				System.out.println("eee" + iii);
				return (iii);
			}
		}
		return ("");
	}
	
	private boolean mustBeInHistory(int i) 
	{
		System.err.println("mustbeinhistory : " + i);
		boolean test = true;
		for (int el : noToHistory)
		{
			if(i==el)
				test = false;
		}
			System.err.println("return : " + test);
		return test;	
	}

	private boolean isInfixedOperator(int id){
		boolean b1=(29<id)&&(id<39);
		boolean b2=(id==273)||(id==274);
		return b1||b2;
	}
	
	private boolean testoperateur(String st) { 
		int i = "+-*/<>=!&|".indexOf(st);
		if (i == -1)
		return (false);
		return (true);
	}
	
	private boolean prioriteinf(String op, String str) {
		if (isTimesDiv(str) && !isTimesDiv(op))
			return (true); 
		else if (isPlusMinus(str) &&isLogicOperator(op))
			return (true);
		else if (">=<=".indexOf(str) > -1 && "|&".indexOf(op) > -1)
			return (true);
		return (false);
	}
	
	private boolean isTimesDiv(String op){
		return (op.equals("*")||op.equals("/"));
	}
	
	private boolean isPlusMinus(String op){
		return (op.equals("+")||op.equals("-"));
	}
	
	private boolean isLogicOperator(String op){
		return ("|&>=<=".indexOf(op)!=-1);
		
	}
	
	private void checkParenthesis(){
		if (!nom.isEmpty()){
			String name=nom.peek();
			if (name.equals("(")) {
					System.err.println("too_much_arguments");
			}
		}
	}
	
	private void deleteLineNumber(){
		lineNumber="";
	}
	
	protected String chercheListe() {
		String liste = "[ ";
		String element = "";
		while (instructionBuffer.getLength() != 0) {
			element = instructionBuffer.getNextWord();
			if (element.equals("[")) {
				calcul.push("[");
				instructionBuffer.deleteFirstWord(element);
				liste += "[ ";
			}

			else if (element.equals("]")) { 
				instructionBuffer.deleteFirstWord(element);
				liste += "] ";
				if (calcul.empty()) {
					return (liste);
				}
				else if (!calcul.peek().toString().equals("[")) {
					return (liste);
				} 
				else
					calcul.pop(); 
			} 
			else {
				instructionBuffer.deleteFirstWord(element);
				liste += element + " ";
			}
		}
		if (true)
			System.out.println("erreur_crochet");
		return (null);
	}
	
	private int chercheParenthese() { 
		boolean continuer = true;
		int of_ouvrant;
		int of_fermant = 0;
		int from_index_ouvrant = 1;
		int from_index_fermant = 1;
		
		while (continuer) {
			of_ouvrant = instructionBuffer.indexOf("(", from_index_ouvrant);
			of_fermant = instructionBuffer.indexOf(")", from_index_fermant);
			if (of_fermant == -1)
				break;
			if (of_ouvrant != -1 && of_ouvrant < of_fermant) {
				from_index_ouvrant = of_ouvrant + 1;
				from_index_fermant = of_fermant + 1;
			} else
				continuer = false;
			;
		}
		return of_fermant;
	}
	
	public void runPrimitive(int id, List<String> args)
	{	
		int res;
		String newParam;
		switch (id) {
		case 0: 
			new ForwardPrimitive().execute(interpreter, args);
			break;
		case 1: 
			new BackwardPrimitive().execute(interpreter, args);
			break;
		case 2:
			new RightTurnPrimitive().execute(interpreter, args);
			break;
		case 3:
			new LeftTurnPrimitive().execute(interpreter, args);
			break;
		case 75:
			new ChangeColorPrimitive().execute(interpreter, args);
			break;
		case 6:
			new ReInitPrimitive().execute(interpreter, args);
			break;
		case 164:
			new ClearPrimitive().execute(interpreter, args);
			break;
		case 18:
			new PenUpPrimitive().execute(interpreter, args);
			break;
		case 19:
			new PenDownPrimitive().execute(interpreter, args);
			break;
		case 12:
			new OriginePrimitive().execute(interpreter, args);
			break;
		case 13:
			new FixePositionPrimitive().execute(interpreter, args);
			break;
		case 5:
			repete(args.toString());
			break;
		case 30:
			operande=true;
			String mulRes1 = args.get(0);
			String mulRes2 = args.get(1);
			res = Integer.valueOf(mulRes1);
			res *= Integer.valueOf(mulRes2);
			newParam = String.valueOf(res);
			calcul.push(newParam);
			break;
		case 31:
			operande=true;
			String divRes1 = args.get(0);
			String divRes2 = args.get(1);
			res = Integer.valueOf(divRes1);
			res /= Integer.valueOf(divRes2);
			newParam = String.valueOf(res);
			calcul.push(newParam);
			break;
		case 32:
			operande=true;
			res = 0;
			for(String el : args)
				res += Integer.valueOf(el);
			newParam = String.valueOf(res);
			calcul.push(newParam);
			break;
		case 33:
			operande=true;
			String res1 = args.get(0);
			String res2 = args.get(1);
			res = Integer.valueOf(res1);
			res -= Integer.valueOf(res2);
			newParam = String.valueOf(res);
			calcul.push(newParam);
			break;
		}
	/*	case 77:
			operande=true;
			int i = Integer.valueOf(args.get(0));
			i = (int) Math.floor(Math.random() * i);
			calcul.push(String.valueOf(i));
			break;
		}*/
	}
	
	protected void repete(String st) 
	{
		int idOpen = -1; 
		int idClose = -1; 
		for(int i=0;i<st.length();i++)
		{
			char c=st.charAt(i);
			if(c == '[')
			{
				idOpen = i;
			}
			if(c == ']')
			{
				if(idClose == -1)
				{
					idClose = i;
				}
			}
		}
		 String strN = st.substring(1,2);
		int n = Integer.valueOf(strN); 
		for (int i=0; i<n; i++)
			decoupe(st.substring(idOpen+1,idClose));
	}
	
	public void initBuffer()
	{
		instructionBuffer = new InstructionBuffer();
	}
}
