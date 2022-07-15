package filrouge.representations;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A constraint.
 */
public interface Constraint {
	/**
	 * Gets the scope of the the premise and conclusion attribute.
	 * @return A Set of Variable.
	 */
	public Set<Variable> getScope();

	/**
	 * Indicates if a Map of Variable and String representing given states can be satisfied.
	 * @param states A Map of Variable and String to be tested.
	 * @return True if states satisfies the premise, false otherwise.
	 */
	public Boolean isSatisfiedBy(Map<Variable, String> states);

	/**
	 * 
	 * @param validDomains viable domains for the variables.
	 * @param map non-assisgnes variables to filter.
	 * @return True if elements have been filtered.
	 */
	public Boolean filter(List<RestrictedDomain> validDomains, Map<Variable, Set<String>> map);

	/**
	 * Gets the premise attribute.
	 * @return A Set of RestrictedDomain.
	 */
	public Set<RestrictedDomain> getPremise();

	/**
	 * Gets the conclusion attribute.
	 * @return A Set of RestrictedDomain.
	 */
	public Set<RestrictedDomain> getConclusion();

}
