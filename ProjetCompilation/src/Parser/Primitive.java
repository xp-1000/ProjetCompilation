package Parser;

import java.util.List;

public abstract class Primitive {
	
	public abstract int getArgCount();
	
	public abstract Object eval(List<Object> args);

}
