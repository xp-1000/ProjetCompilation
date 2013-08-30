package LOnGOal.primitives;

import net.dechelle.jlogo.primitives.ChangeColorPrimitive;
import LOnGOal.parser.Primitive;

public class ChangeColor extends ChangeColorPrimitive implements Primitive{

	@Override
	public int getArgCount()
	{
		return 1;
	}
}