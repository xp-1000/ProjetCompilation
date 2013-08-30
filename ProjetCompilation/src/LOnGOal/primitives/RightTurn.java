package LOnGOal.primitives;

import net.dechelle.jlogo.primitives.RightTurnPrimitive;
import LOnGOal.parser.Primitive;

public class RightTurn extends RightTurnPrimitive implements Primitive{

	@Override
	public int getArgCount()
	{
		return 1;
	}
}