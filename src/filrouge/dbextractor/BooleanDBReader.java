package filrouge.dbextractor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import filrouge.representations.Variable;


public class BooleanDBReader extends DBReader<Boolean> {

	private Set<String> trueValidators = new HashSet<>(Arrays.asList("true", "yes", "y", "1"));
	private Set<String> falseValidators = new HashSet<>(Arrays.asList("false", "no", "n", "0"));

	public BooleanDBReader(Set<Variable> variables) {
		super(variables);
	}

	public BooleanDBReader addTrueValidator(String validator) {
		trueValidators.add(validator);
		return this;
	}

	public BooleanDBReader addFalseValidator(String validator) {
		falseValidators.add(validator);
		return this;
	}

	@Override
	public Boolean transposeVariable(Variable variable, String data) {	
		String transposedName = variable.getName() + "_" + data;
		if (!transposedVariables.containsKey(variable)) {
				Variable newVariable = new Variable(transposedName, new HashSet<>(Arrays.asList("False", "True")));
				
				Map<String, Variable> transposedMap = new HashMap<>();
				transposedMap.put(transposedName, newVariable);
				this.transposedVariables.put(variable, transposedMap);
				for (Map<Variable, Boolean> instance : instances) {
					instance.put(newVariable, false);
				}	
		} else if (!transposedVariables.get(variable).containsKey(transposedName)) {
			Variable newVariable = new Variable(transposedName, new HashSet<>(Arrays.asList("False", "True")));	
			this.transposedVariables.get(variable).put(transposedName, newVariable);
			for (Map<Variable, Boolean> instance : instances) {
				instance.put(newVariable, false);
			}	
		}
		
		for (Variable transposedVariable : transposedVariables.get(variable).values()) {
			currentInstance.put(transposedVariable, false);
		}
		currentInstance.put(transposedVariables.get(variable).get(transposedName), true);
		
		return true;
	}
	
	@Override
	public Boolean readInstanceValue(Variable variable, String data) throws IOException {
		if (trueValidators.contains(data)) return true;
		if (falseValidators.contains(data)) return false;	
		return null;
	}

}