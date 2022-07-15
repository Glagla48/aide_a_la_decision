import static filrouge.representations.Data.constraints;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

import filrouge.ppc.Backtracking;
import filrouge.representations.Constraint;
import filrouge.representations.RestrictedDomain;
import filrouge.representations.Rule;
import filrouge.representations.Variable;

public class BacktrackingTest {

	private static List<?> product(List<List<String>> a) {
		if(a.size() >= 2) {
			List<?> product = a.get(0);
			for (int i = 1; i < a.size(); i++) {
				product = product2(product, a.get(i));
			}
			return product;
		}
		return emptyList();
	}

	private static <A, B> List<?> product2(List<A> a, List<B> b) {
		return of(a.stream()
		.map(e1 -> of(b.stream().map(e2 -> asList(e1, e2)).collect(toList())).orElse(emptyList()))
		.flatMap(List::stream)
		.collect(toList())).orElse(emptyList());
	}


	@Test
	public void run() {
		Backtracking backtrack = new Backtracking(constraints);

		List<Variable> variables = new ArrayList<Variable>(backtrack.getScope());
		List<List<String>> domaines = variables.stream().map(v -> new ArrayList<>(v.getDomaine())).collect(Collectors.toList());
		Integer nbCombinaisons = product(new ArrayList<>(domaines)).size();

		Set<Map<Variable, String>> solutions = backtrack.allSolutions();
		
		System.out.println("Nombre de solutions = " + solutions.size() + " sur " + nbCombinaisons + " combinaisons");
		assertTrue("Should be 6421, got : " + nbCombinaisons.toString(), solutions.size()==6421);
	}

	public static void main(String[] arg)
	{
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

		// BACKTRACK Testes
		Set<Constraint> constraints;
		Backtracking search;
		Set<Map<Variable, String>> allSolutions;

		constraints = new HashSet<>(Arrays.asList(rule));
		System.out.print("Testing search: ");
		search = new Backtracking(constraints);

		allSolutions = search.allSolutions();
		System.out.println("Nombre de solutions = " + allSolutions.size());
		for (Map<Variable, String> sol : allSolutions)
			for (Entry<Variable, String> ent : sol.entrySet())
				System.out.println(ent.getKey() + " : " + ent.getValue());

		Backtracking backtrack = new Backtracking(constraints);

		List<Variable> variables = new ArrayList<Variable>(backtrack.getScope());
		List<List<String>> domaines = variables.stream().map(v -> new ArrayList<>(v.getDomaine())).collect(Collectors.toList());
		Integer nbCombinaisons = product(new ArrayList<>(domaines)).size();

		Set<Map<Variable, String>> solutions = backtrack.allSolutions();
		
		System.out.println("Nombre de solutions = " + solutions.size() + " sur " + nbCombinaisons + " combinaisons");
	}
}