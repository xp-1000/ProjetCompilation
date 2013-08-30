package Parser;

import java.util.List;

import net.dechelle.jlogo.LogoInterpreter;

public class AVPrimitive extends Primitive {

	@Override
	public int getArgCount()
	{
		return 1;
	}

	@Override
	public Object eval(LogoInterpreter interpreter, List<Object> args) {
		double d = (Double)args.get(0);
		
		interpreter.getTurtle().forward(d);
		
		return d;
	}

}
