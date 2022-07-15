/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filrouge.dbextractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import filrouge.representations.Variable;

/**
 *
 * @author lea
 */
public class Database<T> {
    private List<Variable> orderedVariables;
    private List<Map<Variable, T>> instances;
    

    public Database(List<Variable> orderedVariables, List<Map<Variable, T>> instances) {
        this.instances = instances;
        this.orderedVariables = orderedVariables;
	}

	public Database() {
		this(new ArrayList<>(), new ArrayList<>());
	}
	
	/**
     * Gets orderedVariables attribute.
     * @return A List of Variable.
     */
	public List<Variable> getVariables() {
		return this.orderedVariables;
	}

    /**
     * Gets instances attribute.
     * @return A List of instances.
     */
	public List<Map<Variable, T>> getInstances() {
		return this.instances;
	}

	/**
     * Sets the variables attribute.
     * @param variables A List of Variable.
     */
    public void setVariables(List<Variable> variables) {
		this.orderedVariables = variables;
	}

	/**
    * Sets instances attribute.
    * @param instances A List of instance.
    */
    public void setInstances(List<Map<Variable, T>> instances) { 
		this.instances = instances;
	}

	/**
    * Add variable to orderedVariables.
    * @param variable A variable to add.
    */
    public void addVariable(Variable variable){
        this.orderedVariables.add(variable);
    }

	/**
    * Add an instance to instances.
    * @param instance An instance to add.
    */
    public void addInstance(Map<Variable, T> instance){
        this.instances.add(instance);
    }
}
