package filrouge.representations;

import java.util.HashSet;
import java.util.Set;

public class IncompatibilityConstraint extends Rule {

	public IncompatibilityConstraint(Set<RestrictedDomain> atoms) {
		super(atoms, new HashSet<RestrictedDomain>());
	}
}