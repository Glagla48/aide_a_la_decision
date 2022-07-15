package filrouge.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import filrouge.factories.FactoryVariable;
import filrouge.factories.Utilities;
import filrouge.planning.Action;
import filrouge.planning.State;
import filrouge.representations.Variable;
import filrouge.utils.Pair;

public class HealthCare {
	static List<Variable> variables = Arrays.asList(new Variable[] {
		FactoryVariable.CreateBooleanVariable("ANGINA"),
		FactoryVariable.CreateBooleanVariable("FLU"),
		FactoryVariable.CreateBooleanVariable("POX"),
		FactoryVariable.CreateBooleanVariable("PLAGUE"),

		FactoryVariable.CreateLevelVariable("FEVER"),
		FactoryVariable.CreateLevelVariable("COUGH"),
		FactoryVariable.CreateLevelVariable("BUTTONS"),
	});

	public final static List<Variable> maladies = Arrays.asList(new Variable[] {
		variable("ANGINA"), variable("FLU"), variable("POX"), variable("PLAGUE")
	});

	public final static List<Variable> symptomes = Arrays.asList(new Variable[] {
		variable("FEVER"), variable("COUGH"), variable("BUTTONS")
	});

	public final static List<Action> sirops = Arrays.asList(new Action[] {
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("BUTTONS"), Utilities.ELEVE); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("BUTTONS"), Utilities.MOYEN); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("BUTTONS"), Utilities.MOYEN); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("BUTTONS"), Utilities.FAIBLE ); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("BUTTONS"), Utilities.FAIBLE); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("BUTTONS"), Utilities.AUCUN); }}) 
				);
		}}),
//==============================================================================================================
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("FEVER"), Utilities.ELEVE); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("FEVER"), Utilities.MOYEN); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("FEVER"), Utilities.MOYEN); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("FEVER"), Utilities.FAIBLE ); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("FEVER"), Utilities.FAIBLE); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("FEVER"), Utilities.AUCUN); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("COUGH"), Utilities.ELEVE); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("COUGH"), Utilities.MOYEN); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("COUGH"), Utilities.MOYEN); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("COUGH"), Utilities.FAIBLE ); }}) 
				);
		}}),
		new Action(new HashMap<State, State>() {{
			put(new State(new HashMap<Variable, String>() {{ put(variable("COUGH"), Utilities.FAIBLE); }}),
				new State(new HashMap<Variable, String>() {{ put(variable("COUGH"), Utilities.AUCUN); }}) 
				);
		}}),
		new Action(new HashMap<State, State>(){{
			put(new State(new HashMap<Variable, String>() {{ put(variable("ANGINA"), Utilities.OUI);}}),
				new State(new HashMap<Variable, String>() {{ put(variable("ANGINA"), Utilities.NON);}})
				);
		}}),
		new Action(new HashMap<State, State>(){{
			put(new State(new HashMap<Variable, String>() {{ put(variable("FLU"), Utilities.OUI);}}),
				new State(new HashMap<Variable, String>() {{ put(variable("FLU"), Utilities.NON);}})
				);
		}}),
		new Action(new HashMap<State, State>(){{
			put(new State(new HashMap<Variable, String>() {{ put(variable("POX"), Utilities.OUI);}}),
				new State(new HashMap<Variable, String>() {{ put(variable("POX"), Utilities.NON);}})
				);
		}}),
		new Action(new HashMap<State, State>(){{
			put(new State(new HashMap<Variable, String>() {{ put(variable("PLAGUE"), Utilities.OUI);}}),
				new State(new HashMap<Variable, String>() {{ put(variable("PLAGUE"), Utilities.NON);}})
				);
		}}),
//==============================================================================================================
	});





	
	public final static Action guerison = new Action(new HashMap<State, State>() {{
		put(new State(new HashMap<Variable, String>() {{ 
				for(Variable symptome : symptomes)
					put(symptome, Utilities.AUCUN); }}),
			new State(new HashMap<Variable, String>() {{ 
				for(Variable maladie : maladies)
					put(maladie, Utilities.NON); }})
		);
	}});

	public static Variable variable(String name) {
		return variables.stream().filter(v -> v.getName() == name).findFirst().get();
	}

	public static List<Action> generateRandomMedecines(Integer nbMedecines) {
		List<Action> medecines = new ArrayList<>();
		State emptyState = new State(new HashMap<>());
		Random rand = new Random();
		for (int i = 0; i < nbMedecines; i++) {
			HashMap<State, State> rules = new HashMap<>();

			for (Variable var : symptomes) {
				rules.put(emptyState, new State(new HashMap<Variable, String>() {{ put(var, var.getRandom()); }}));
			}

			Variable symptome = symptomes.get(rand.nextInt(symptomes.size()));
			rules.put(emptyState, new State(new HashMap<Variable, String>() {{ put(symptome, Utilities.AUCUN); }}));
			medecines.add(new Action(rules));
		}
		return medecines;
	}

	public static State generateRandomeState() {
		Random rand = new Random();
		Variable maladie = maladies.get(rand.nextInt(maladies.size()));

		HashMap<Variable, String> assignement = new HashMap<>();
		assignement.put(maladie, Utilities.OUI);

		for (Variable var : symptomes) {
			assignement.put(var, var.getRandom());
		}

		return new State(assignement);
	}

	public static Pair<State,State> generateNotSoRandomeState()
	{
		Random rand = new Random();
		HashMap<Variable, String> assignement1 = new HashMap<>();
		HashMap<Variable, String> assignement2 = new HashMap<>();
		for(Variable var : symptomes)
		{
			if(rand.nextInt(3) == 2)
			{
				assignement1.put(var, Utilities.ELEVE);
			}
			if(rand.nextInt(3) == 1)
			{
				assignement1.put(var, Utilities.MOYEN);
			}
			else
			{
				assignement1.put(var, Utilities.FAIBLE);
			}

		}

		for(Variable var : maladies)
		{
			if(rand.nextInt(10) < 3)
				assignement1.put(var, Utilities.NON);
			else
				assignement1.put(var, Utilities.OUI);
		}

		State initState = new State(assignement1);

		for(Variable var : symptomes)
		{
				assignement2.put(var, Utilities.AUCUN);
		}

		for(Variable var : maladies)
		{
			assignement2.put(var, Utilities.NON);
		}

		State goal = new State(assignement2);

		return new Pair<State,State>(initState, goal);
	}
}