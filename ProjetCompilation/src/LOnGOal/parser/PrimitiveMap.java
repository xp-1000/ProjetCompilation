package LOnGOal.parser;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.TreeMap;

public class PrimitiveMap {
	
	/* private Map<String, Primitive> primitives;

	public PrimitiveMap(String lang)
	{
		primitives = new HashMap<String, Primitive>();
		this.load(lang);
	}
	
	public int getArgCount(String key)
	{
		Primitive p = primitives.get(key);
		
		return p.getArgCount();
	}
	
	public Primitive get(String name)
	{
		return primitives.get(name);
	}
	
	private void load(String lang)
	{
		switch(lang)
		{
			case "French":
				primitives.put("AV", new Forward());
				primitives.put("RE", new Backward());
				primitives.put("TD", new RightTurn());
				primitives.put("TG", new LeftTurn());
				primitives.put("FCC", new ChangeColor());
				break;
			case "English":
				primitives.put("FD", new Forward());
				primitives.put("BK", new Backward());
				primitives.put("RT", new RightTurn());
				primitives.put("LT", new LeftTurn());
				primitives.put("SETPC", new ChangeColor());
		}
	}
	
	private void reload(String lang)
	{
		primitives = new HashMap<String, Primitive>();
		load(lang);
	}*/
	
	protected static final int PRIMITIVE_NUMBER = 310;
	protected static int [] numberArgs;
	protected static TreeMap<String,String> primitives;
	
	public PrimitiveMap(String lang)
	{
		try 
		{
			FileInputStream fichier = new FileInputStream("primitives.mm");
			ObjectInputStream ois = new ObjectInputStream(fichier);
			primitives = (TreeMap<String,String>) ois.readObject();
		//	System.out.println(primitives.toString());
			fichier = new FileInputStream("number_args.mm");
			ois = new ObjectInputStream(fichier);
			numberArgs = (int[]) ois.readObject();
		//	for(int i=0;i<numberArgs.length;i++)
		//		System.out.println(numberArgs[i]);
		}
		catch (java.io.IOException e) 
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	public Primitive get(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
