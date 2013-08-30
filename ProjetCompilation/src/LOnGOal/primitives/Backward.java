package LOnGOal.primitives;

import net.dechelle.jlogo.primitives.BackwardPrimitive;
import LOnGOal.parser.Primitive;

public class Backward extends BackwardPrimitive implements Primitive{

	@Override
	public int getArgCount()
	{
		return 1;
	}
}