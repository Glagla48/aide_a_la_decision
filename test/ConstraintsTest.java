import static org.junit.Assert.assertTrue;
import static filrouge.representations.Data.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import filrouge.factories.Utilities;
import filrouge.representations.Variable;
import filrouge.representations.Rule;
import filrouge.representations.RestrictedDomain;

public class ConstraintsTest {

	@Test
	public void patient00() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(angine, Utilities.NON);
		patient.put(fievre, Utilities.AUCUN);

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patient01() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(angine, Utilities.OUI);
		patient.put(fievre, Utilities.AUCUN);

		assertTrue(!c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patient10() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(angine, Utilities.NON);
		patient.put(fievre, Utilities.ELEVE);

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patient11() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(angine, Utilities.OUI);
		patient.put(fievre, Utilities.ELEVE);

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patientSP00() {
		HashMap<Variable, String> patient = new HashMap<>();

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patientSP00_2() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(angine, Utilities.NON);

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patientSP01() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(angine, Utilities.OUI);

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}

	@Test
	public void patientSP10() {
		HashMap<Variable, String> patient = new HashMap<>();
		patient.put(fievre, Utilities.ELEVE);

		assertTrue(c_angine_Fievre.isSatisfiedBy(patient));
	}


	public static void main(String[] args){
		// Variables ===================================================

		Variable x0 = new Variable("x0", new HashSet<>(Arrays.asList("true", "false")));
		Variable x1 = new Variable("x1", new HashSet<>(Arrays.asList("0", "1")));
		Variable x2 = new Variable("x2", new HashSet<>(Arrays.asList("0", "1", "2")));
		Variable x3 = new Variable("x3", new HashSet<>(Arrays.asList("0", "1", "2", "3", "6")));

		Set<Variable> allVariables = new HashSet<>(Arrays.asList(x0, x1, x2, x3));

		// Rule : (!x0 && x1=0) -> (new RestrictedDomain(x2=1 || x3=2)
		// ======================

		Set<RestrictedDomain> premise = new HashSet<>();
		premise.add(new RestrictedDomain(x0, new HashSet<>(Arrays.asList("false"))));
		premise.add(new RestrictedDomain(x1, new HashSet<>(Arrays.asList("0"))));

		Set<RestrictedDomain> conclusion = new HashSet<>();
		conclusion.add(new RestrictedDomain(x2, new HashSet<>(Arrays.asList("1"))));
		conclusion.add(new RestrictedDomain(x3, new HashSet<>(Arrays.asList("2", "6"))));

		Rule rule = new Rule(premise, conclusion);

		// Assignments =================================================

		Map<Variable, String> satisfying1 = new HashMap<>();
		satisfying1.put(x0, "false");
		satisfying1.put(x1, "0");
		satisfying1.put(x2, "0");
		satisfying1.put(x3, "2");

		Map<Variable, String> satisfying2 = new HashMap<>();
		satisfying2.put(x0, "false");
		satisfying2.put(x1, "1");
		satisfying2.put(x2, "0");
		satisfying2.put(x3, "0");

		Map<Variable, String> satisfying3 = new HashMap<>();
		satisfying3.put(x0, "true");
		satisfying3.put(x1, "1");
		satisfying3.put(x2, "1");
		satisfying3.put(x3, "2");

		Map<Variable, String> falsifying = new HashMap<>();
		falsifying.put(x0, "false");
		falsifying.put(x1, "0");
		falsifying.put(x2, "2");
		falsifying.put(x3, "1");

		// Tests =======================================================

		System.out.print("Testing scope: ");
		System.out.println(rule.getScope().equals(allVariables) ? "OK" : "KO");
		System.out.println(rule);

		System.out.print("Testing satisfying assignment: ");
		System.out.println(satisfying1);
		System.out.println(rule.isSatisfiedBy(satisfying1) ? "OK" : "KO");

		System.out.print("Testing satisfying assignment: ");
		System.out.println(satisfying2);
		System.out.println(rule.isSatisfiedBy(satisfying2) ? "OK" : "KO");

		System.out.print("Testing satisfying assignment: ");
		System.out.println(satisfying3);
		System.out.println(rule.isSatisfiedBy(satisfying3) ? "OK" : "KO");

		System.out.print("Testing falsifying assignment: ");
		System.out.println(falsifying);
		System.out.println(!rule.isSatisfiedBy(falsifying) ? "OK" : "KO");
	}
}