package filrouge.representations;

import java.util.HashSet;
import java.util.Set;

public class Disjonction extends Rule {

	public Disjonction(Set<RestrictedDomain> atoms) {
		super(new HashSet<RestrictedDomain>(), atoms);
	}
}