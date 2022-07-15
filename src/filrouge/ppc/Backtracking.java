package filrouge.ppc;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import filrouge.ppc.heuristic.Heuristic;
import filrouge.ppc.heuristic.HeuristicGrandPetit;
import filrouge.ppc.heuristic.HeuristicRandom;
import filrouge.representations.Constraint;
import filrouge.representations.RestrictedDomain;
import filrouge.representations.Variable;

//import filrouge.utils.Pair;

/**
 * A class made to solve a problem with given variable 
 * and constraints using a backtracking algorithm.
 */
public class Backtracking {

	/**
	 * The variables of the problem to solve.
	 */
	protected Set<Variable> variables;

	/**
	 * The constraints applied to the variables.
	 */
	protected Set<Constraint> constraints;

	public Backtracking(Set<Variable> variables, Set<Constraint> contraints) {
		this.variables = variables;
		this.constraints = contraints;
	}

	public Backtracking(Set<Constraint> contraints) {
		this.constraints = contraints;
		this.variables = new HashSet<>();

		for (Constraint c : this.constraints) {
			for (Variable var : c.getScope())
				this.variables.add(var);
		}
		for (Variable c : this.variables) {
			System.out.println(c.getName() + c.getDomaine());
		}
		System.out.println("---------------------");
	}

	/**
	 * Gets the scope of the variables attribute.
	 * @return An HashSet of Variable.
	 */
	public Set<Variable> getScope() {
		return new HashSet<>(this.variables);
	}

	/**
	 * Finds all solutions using the backtracking method.
	 * @return A Set containing the solutions.
	 */
	public Set<Map<Variable, String>> allSolutions() {
		Set<Map<Variable, String>> solutions = new HashSet<>();

		this.solution(new HashMap<>(), new LinkedList<>(this.setVariableToRestricteDomain(this.variables)), solutions);
		return solutions;
	}

	/**
	 * Turns a Set of Variable into a Set of RestrictedDomain. 
	 * The subDomain of the RestrictedDomain is equal to the domain of the Variable.
	 * @param variables The Set to transform.
	 * @return A Set of RestrictedDomain created from a Set of Variable.
	 */
	public Set<RestrictedDomain> setVariableToRestricteDomain(Set<Variable> variables)
	{
		HashSet<RestrictedDomain> result = new HashSet<>();
		for(Variable var : variables)
		{
			RestrictedDomain rd = new RestrictedDomain(var, var.getDomaine());
			result.add(rd);
		}
		return result;
	}

	/**
	 * Turns constraints attribute into a List of RestrictedDomain.
	 * @return A list of RestrictedDomain.
	 */
	public List<RestrictedDomain> createListRestrictedDomain()
	{
		List<RestrictedDomain> res = new ArrayList<>();
		for(Constraint c : this.constraints)
		{
			for(RestrictedDomain rd : c.getPremise())
			{
				if(!res.contains(rd))
					res.add(rd);
			}

			for(RestrictedDomain rd : c.getConclusion())
			{
				if(!res.contains(rd))
					res.add(rd);
			}
		}

		return res;
	}


	/**
	 * Transform a Queue of RestrictedDomain into an HashMap of Variable and Set of String.
	 * @param queu The Queue to transform.
	 * @return A new HashMap containing the values of the Queu.
	 */
	public HashMap<Variable, Set<String>> dequeToMap(Deque<RestrictedDomain> queu)
	{
		HashMap<Variable, Set<String>> res = new HashMap<>();
		for(RestrictedDomain rd : queu)
		{
			res.put(rd.getVariable(), rd.getDomain());
		}
		return res;
	}

	/**
	 * Filters given RestrictedDomains by removing values which are not arc consistent
	 * with respect to a given set of constraints.
	 * @param non_ass RestrictedDomains to filter.
	 * @return True if at least one domain has changed, false otherwise.
	 */
	public boolean filter(Deque<RestrictedDomain> non_ass)
	{
		/*List<RestrictedDomain> validDomains = this.createListRestrictedDomain();
		HashMap<Variable, Set<String>> map = this.dequeToMap(non_ass);
		boolean result = false;
		for(Constraint c : this.constraints)
		{
			result &= c.filter(validDomains, map);
		}

		Deque<RestrictedDomain> linkedList = new LinkedList<>();
		for(Variable key : map.keySet())
		{
			linkedList.add(new RestrictedDomain(key, map.get(key)));
		}


		return new Pair<Boolean,Deque<RestrictedDomain>> (result, linkedList);*/
		GeneralizedArcConsistency filter  = new GeneralizedArcConsistency(this.constraints);
		boolean res = filter.enforceArcConsistency(new ArrayList<RestrictedDomain>(non_ass));

		return res;
	}

	/**
	 * Finds the solution of the problem using a backtracking method.
	 * @param ass_partiel Map formed by the tested RestrictedDoamin.
	 * @param non_ass Deque formed by the untested RestrictedDomain. 
	 * @param accumulator A Set containing found solutions.
	 */
	public void solution(Map<Variable, String> ass_partiel, Deque<RestrictedDomain> non_ass, Set<Map<Variable, String>> accumulator) {
		if (non_ass.isEmpty()) {
			accumulator.add(ass_partiel);
		} else {
				Deque<RestrictedDomain> newNon_ass = new LinkedList<>(non_ass);
				//this.filter(newNon_ass);
				//Heuristic heuristic = new HeuristicGrandPetit();
				//Variable var = heuristic.work(this.dequeToMap(newNon_ass));
				//RestrictedDomain choice = new RestrictedDomain(var, var.getDomaine());
				RestrictedDomain choice = newNon_ass.poll();
				//System.out.println(ass_partiel +"/"+non_;ass+"/"+accumulator);
				//if(!newNon_ass.remove(choice))
				//	System.err.println("BOOM");
				for (String value : choice.getDomain()) {
					Map<Variable, String> newAss_partiel = new HashMap<>(ass_partiel);
					newAss_partiel.put(choice.getVariable(), value);

					Boolean isSatisfied = this.constraints.stream().allMatch(c -> c.isSatisfiedBy(newAss_partiel));

					if (isSatisfied) {
						
						this.solution(newAss_partiel, newNon_ass, accumulator);
					}
				}
			}
		}
	}

	/*public HashMap<Variable, String> backtracking(HashMap<Variable, String> node) {
		for (Variable var : this.variables) {
			if(node.containsKey(var)) continue;
			HashMap<Variable, String> nextNode = new HashMap<>(node);
			for (String state : var.getDomaine()) {
				nextNode.put(var, state);

				boolean valid = true;
				for (Constraint constraint : this.constraints) {
					if(!constraint.isSatisfiedBy(nextNode)) {
						valid = false;
						break;
					}
				}
				if(valid ) {
					if((nextNode.size() == this.variables.size()) && !this.solutions.contains(node)){
						System.out.println(nextNode);
						this.solutions.add(new HashMap<>(nextNode));
					}
					this.backtracking(nextNode);
				}
			}
		}

		return node;
	}*/
