package Parser;

import java.util.HashMap;
import java.util.Map;

import net.dechelle.jlogo.primitives.BackwardPrimitive;
import net.dechelle.jlogo.primitives.ChangeColorPrimitive;
import net.dechelle.jlogo.primitives.ForwardPrimitive;
import net.dechelle.jlogo.primitives.LeftTurnPrimitive;
import net.dechelle.jlogo.primitives.RightTurnPrimitive;

public class PrimitiveMap {
	
	private Map<String, Primitive> primitives;

	public PrimitiveMap()
	{
		primitives = new HashMap<String, Primitive>();
		
		primitives.put("AV", new AVPrimitive());
		/*primitives.put("AV", new ForwardPrimitive());
		primitives.put("RE", new BackwardPrimitive());
		primitives.put("TD", new RightTurnPrimitive());
		primitives.put("TG", new LeftTurnPrimitive());
		primitives.put("FCC", new ChangeColorPrimitive());*/
	}
	
	public int getArgCount(String key)
	{
		Primitive p = primitives.get(key);
		
		return p.getArgCount();
	}
}
