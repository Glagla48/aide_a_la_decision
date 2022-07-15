package filrouge.dbextractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import filrouge.representations.Variable;


public abstract class DBReader<T> {

	protected Set<Variable> variables;
	protected List<Map<Variable, T>> instances;
	protected Map<Variable, T> currentInstance;
	protected Map<Variable, Map<String, Variable>> transposedVariables;
    
    public DBReader(Set<Variable> variables) {
        this.variables = variables;
    }
    
    /**
     * Reads a database, that is, a list of instantiations, from a CSV file.
	 * @param filename filename of the CSV file.
	 * @return Return a database of the parsed file as T object.
	 * @throws IOException If the file is not found or an error occured.
     */
    public Database<T> importDB (String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader (new FileReader (filename))) {
            Database<T> res = this.readDB(reader);
            reader.close();
            return res;
        }
	}
	
	public abstract T transposeVariable(Variable variable, String data);

	public abstract T readInstanceValue(Variable variable, String data) throws IOException;
    
    public Database<T> readDB(BufferedReader in) throws IOException {
        // Reading variables
		List<Variable> orderedVariables = new ArrayList<>();
		transposedVariables = new HashMap<>();
        String variableLine = in.readLine();
        for (String variableName : variableLine.split(";")) {
			boolean found = false;
			variableName = variableName.replace("_", " ");
            for (Variable variable: this.variables) {
                if (variable.getName().toLowerCase().equals(variableName.toLowerCase())) {
                    orderedVariables.add(variable);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IOException("Unknown variable name: " + variableName);
            }
        }
        // Reading instances
        instances = new ArrayList<>();
        String line;
        int lineNb = 1;
        while ( (line = in.readLine()) != null ) {
            String [] parts = line.split(";");
            if (parts.length != orderedVariables.size()) {
                throw new IOException("Wrong number of fields on line " + lineNb);
            }
            currentInstance = new HashMap<> ();
            for (int i = 0; i < parts.length; i++) {
				Variable variable = orderedVariables.get(i);
				T value = readInstanceValue(variable, parts[i]);
				if (value == null) 
					transposeVariable(variable, parts[i]);
				else
					currentInstance.put(variable, value);
            }
            instances.add(currentInstance);
            lineNb++;
		}
		
		for (Entry<Variable, Map<String, Variable>> transposedVariables : transposedVariables.entrySet()) {
			orderedVariables.remove(transposedVariables.getKey());
			for (Variable transposedVariable : transposedVariables.getValue().values()) {
				orderedVariables.add(transposedVariable);
			}
		}

        return new Database<T>(orderedVariables, instances);
    }

}