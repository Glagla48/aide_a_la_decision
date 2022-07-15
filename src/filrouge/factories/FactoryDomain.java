package filrouge.factories;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import filrouge.representations.RestrictedDomain;
import filrouge.representations.Variable;

public class FactoryDomain {
	public static Set<String> CreateDomain(String... args) {
		return new HashSet<String>(Arrays.asList(args));
	}

	public static RestrictedDomain CreateRestrictedDomain(Variable variable, String... args) {
		return new RestrictedDomain(variable, CreateDomain(args));
	}

	public static RestrictedDomain CreateRestrictedDomainOUI(Variable variable) {
		return CreateRestrictedDomain(variable, Utilities.OUI);
	}

	public static RestrictedDomain CreateRestrictedDomainNON(Variable variable) {
		return CreateRestrictedDomain(variable, Utilities.OUI);
	}
}