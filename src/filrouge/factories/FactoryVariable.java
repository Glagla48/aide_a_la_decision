package filrouge.factories;

import java.util.Arrays;
import java.util.HashSet;

import filrouge.representations.Variable;

public class FactoryVariable {

	public static Variable CreateLevelVariable(String name) {
		return new Variable(name, Utilities.LEVEL);
	}

	public static Variable CreateBooleanVariable(String name) {
		return new Variable(name, Utilities.BOOLEAN);
	}

	public static Variable CreateVariable(String name, String[] states) {
		return new Variable(name, new HashSet<String>(Arrays.asList(states)));
	}
}