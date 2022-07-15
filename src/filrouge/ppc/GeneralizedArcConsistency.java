package filrouge.ppc;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import filrouge.representations.Constraint;
import filrouge.representations.RestrictedDomain;
import filrouge.representations.Variable;

/**
 * A class for enforcing generalized arc consistency to domains wrt to variables.
 * The algorithms are brute-force: for deciding whether a value is GAC wrt a constraint, at
 * worst all tuples in the Cartesian product of the variables in the scope of the
 * constraint are tested. 
 */
public class GeneralizedArcConsistency {

	protected Set<Constraint> constraints;
	
	public GeneralizedArcConsistency(Set<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * Filters given domains by removing values which are not arc consistent
	 * with respect to a given set of constraints.
	 * @param domains A map from (at least) the variables occurring in the scope of
	 * the constraint to domains.
	 * @return true if at least one domain has changed, false otherwise.
	 * @throws IllegalArgumentException If a variable occurring in the scope of some
	 * constraint is mapped to no domain.
	 */
	public boolean enforceArcConsistency (List<RestrictedDomain> domains)
	throws IllegalArgumentException {
		boolean hasChanged = false;
		while (hasChanged) {
			for (Constraint constraint: this.constraints) {
				hasChanged = hasChanged || GeneralizedArcConsistency.enforceArcConsistency(constraint, domains);
			}
		}

		return hasChanged;
	}
	
    /**
	 * Filters given domains by removing values which are not arc consistent
	 * with respect to a given constraint.
	 * @param constraint A constraint
	 * @param domains A map from (at least) the variables occurring in the scope of
	 * the constraint to domains.
	 * @return true if at least one domain has changed, false otherwise.
	 * @throws IllegalArgumentException If a variable occurring in the scope of the
	 * constraint is mapped to no domain.
	 */
	public static boolean enforceArcConsistency (Constraint constraint, List<RestrictedDomain> domains)
	throws IllegalArgumentException {

		boolean hasChanged = false;
		boolean contains = false;

        for(Variable var : constraint.getScope())
        {
            for(RestrictedDomain rd : domains)
            {
                if(rd.getVariable().equals(var))
                    contains = true;
			}
			if(!contains)
				throw new IllegalArgumentException("Variable " + var + " occurs in " + constraint + " but has no domain in " + domains);
			contains = false;

        }
		
		for(RestrictedDomain rd : domains)
		{
			for(String value : rd.getDomain())
			{
				if ( ! GeneralizedArcConsistency.isConsistent(rd.getVariable(), value, constraint, domains) )
				{
					rd.getDomain().remove(value);
					if(rd.getDomain().size() == 0)
					{
						domains.remove(rd);
						break;
					}
					hasChanged = true;
				}
			}
		}

		return hasChanged;
		// on boucle sur les variables de la contraintes (var)
            // si var n'est pas dans le domains donnée, alors erreurs done
			// si oui 
                // récupérer les valeurs de var dans domains 
                   // if ( ! GeneralizedArcConsistency.isConsistent(var, value, constraint, domains) ) {
                        // faites les modification nécéssaire
                // enlever toutes les valeurs non viables
        // en cas de changement
            // appel récusive sur le rest de domains
	}


	/**
	 * Decides whether a given value is arc consistent for a given variable wrt a given constraint
	 * and given domains.
	 * @param variable A variable.
	 * @param value A value.
	 * @param constraint A constraint.
	 * @param domains A map from (at least) the variables occurring in the scope of
	 * the constraint to domains (except possibly the given variable).
	 * @return true if the given value is arc consistent for the given variable.
	 * @throws IllegalArgumentException if a variable occurring in the scope of the
	 * constraint (except possibly the given variable) is mapped to no domain.
	 */
	public static boolean isConsistent (Variable variable, String value, Constraint constraint, List<RestrictedDomain> domains)
	throws IllegalArgumentException {
		Deque<Variable> unassignedVariables = new LinkedList<>(constraint.getScope());
		unassignedVariables.remove(variable);
		Map<Variable, String> partialAssignment = new HashMap<>();
		partialAssignment.put(variable, value);
		return GeneralizedArcConsistency.isConsistent(partialAssignment, unassignedVariables, constraint, domains);
	}
	
	// Helper method ===================================================
	
	protected static boolean isConsistent (Map<Variable, String> partialAssignment, Deque<Variable> unassignedVariables, Constraint constraint, List<RestrictedDomain> domains) {
		if (unassignedVariables.isEmpty()) {
			return constraint.isSatisfiedBy(partialAssignment);
		} else {
			boolean contains = false;
			Variable var = unassignedVariables.pop();
			//if ( ! domains.containsVar(var) ) { // ajout containsVar dans RestrictedDomain
            //    
            for(RestrictedDomain rd : domains)
            {
				if(rd.getVariable().equals(var))
					contains = true;
			}
			if(!contains)
				 throw new IllegalArgumentException("Variable " + var + " occurs in " + constraint + " but has no domain in " + domains);
                // vérifier la consistance (satisfiabilité) par un appel récursif 
                //partialAssignment sur les variables du domains, 
                //sachant que le test d'arrêt est déjà fait
                // et que si tout est vrai on retour vrai
                
                // sinon on retourne false 
			return GeneralizedArcConsistency.isConsistent(partialAssignment, unassignedVariables, constraint, domains) && true;
		}
	}

}