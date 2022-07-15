package filrouge.representations;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rule implements Constraint {
	protected Set<RestrictedDomain> premise;
	protected Set<RestrictedDomain> conclusion;

	public Rule(Set<RestrictedDomain> premise, Set<RestrictedDomain> conclusion) {
		this.premise = premise;
		this.conclusion = conclusion;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<RestrictedDomain> getPremise() {
		return this.premise;
	}

	/**
	 * Sets the premise.
	 * @param premise A new Set of RestrictedDomain.
	 */
	public void setPremise(Set<RestrictedDomain> premise) {
		this.premise = premise;
	}


	/**
	 * {@inheritDoc}
	 */
	public Set<RestrictedDomain> getConclusion() {
		return this.conclusion;
	}

	/**
	 * Sets the conclusion.
	 * @param conclusion A new Set of RestrictedDomain.
	 */
	public void setConclusion(Set<RestrictedDomain> conclusion) {
		this.conclusion = conclusion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Variable> getScope() {
		Set<Variable> scope = new HashSet<Variable>();
		for (RestrictedDomain domain : this.premise) {
			scope.add(domain.getVariable());
		}
		for (RestrictedDomain domain : this.conclusion) {
			scope.add(domain.getVariable());
		}
		return scope;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isSatisfiedBy(Map<Variable, String> states) {
		Boolean premiseState = true;
		for (RestrictedDomain domain : this.premise) {
			Variable currentVariable = domain.getVariable();
			String state = states.get(currentVariable);
			if(!domain.getDomain().contains(state)) {
				premiseState = false;
				break;
			}
		}

		//Boolean isConclusionEmpty = !this.conclusion.stream().anyMatch(d -> states.get(d.getVariable()) != null);
		Boolean conclusionState = false;//isConclusionEmpty;
		for (RestrictedDomain domain : this.conclusion) {
			Variable currentVariable = domain.getVariable();
			String state = states.get(currentVariable);
			if(state == null || domain.getDomain().contains(state)) {
				conclusionState = true;
				break;
			}
		}
		return premiseState && !conclusionState ? false : true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean filter(List<RestrictedDomain> validDomains, Map<Variable, Set<String>> map) {
		boolean edited = false;
		if(validDomains.size()==0) {
			map.forEach((k, v) -> v.clear());
			return true;
		}
		
		for (RestrictedDomain domain : validDomains) {
			if(map.containsKey(domain.getVariable()))
				edited |= map.get(domain.getVariable()).retainAll(domain.getDomain());
		}
		return edited;
	}
}