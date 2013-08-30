package LOnGOal.primitives;

import net.dechelle.jlogo.primitives.LeftTurnPrimitive;
import LOnGOal.parser.Primitive;

public class LeftTurn extends LeftTurnPrimitive implements Primitive{

	@Override
	public int getArgCount()
	{
		return 1;
	}
}