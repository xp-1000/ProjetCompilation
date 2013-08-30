package LOnGOal.parser;

import java.util.List;

import net.dechelle.jlogo.LogoInterpreter;

/*public abstract class Primitive {
	
	public abstract int getArgCount();
	
	public abstract Object eval(LogoInterpreter interpreter, List<Object> args);

}*/

public interface Primitive {

	public int getArgCount();

	public Object execute(LogoInterpreter interpreter, List<Object> args);

}

