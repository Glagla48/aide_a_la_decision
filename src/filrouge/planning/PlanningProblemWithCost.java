package filrouge.planning;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import filrouge.example.HealthCare;
import filrouge.representations.Variable;
import filrouge.planning.estimation.*;

/**
 * An extension of PlanningProblem that takes in consideration the cost of each actions
 * to reach the solution.
 */
public class PlanningProblemWithCost extends PlanningProblem {

	private Estimation estimation;
	private int probeDijkstra = 0;
	private int probeAStar = 0;

	public PlanningProblemWithCost(Estimation estimation) {
		super();
		this.estimation = estimation;
	}


	/**
	 * Returns the cost of a given Action.
	 * @param action An Action.
	 * @return The cost of the Action.
	 */
	public static Integer getCost(Action action) {
		return HealthCare.sirops.contains(action) ? 2 : (action.equals(HealthCare.guerison) ? 0 : 1);
	}

	/**
	 * Dijkstra algorithm. Solves the problem wih minimum cost.
	 * @param problem The Problem to solve.
	 * @return A LinkedList of Action to solve the Problem.
	 */
	public LinkedList<Action> dijkstra(Problem problem){
        HashMap<State,Integer> distance = new HashMap<>();
        HashMap<State,State> father = new HashMap<>();
        HashMap<State,Action> plan  = new HashMap<>();
        Deque<State> open = new LinkedList<>();

        ArrayList<State> goals  = new ArrayList<>();
        distance.put(problem.getInitialState(), 0);
        father.put(problem.getInitialState(),null);
        State next = null;
        open.add(problem.getInitialState());
        father.put(problem.getInitialState(), null);
        while(!open.isEmpty()){
            State state = this.mapMinValue(distance, open); //edit with min distance 
            open.remove(state);
            if(satisfies(state, problem.getGoal())){
				if(!goals.contains(state))
					goals.add(state);
            }
            for(Action action : problem.getActions()){
                if(is_applicable(action, state)){
					probeDijkstra++;
                    next = apply(action,state);
                    if(!distance.containsKey(next)){
                        distance.put(next, Integer.MAX_VALUE);
                    }
                    if(distance.get(next)>distance.get(state)+PlanningProblemWithCost.getCost(action)){
                        distance.put(next,distance.get(state)+ PlanningProblemWithCost.getCost(action));
                        father.put(next, state);
						plan.put(next, action);
						if(!open.contains(next))
                        	open.add(next);
                    }
                }
            }
        }
        return this.get_dijkstra_plan(father,plan,goals,distance);
    }




	/**
	 * Returns the LinkedList of Action that solves Dijkstra's Problem.
	 * @param father A Map of State composed with the father and the chlid State.
	 * @param actions A Map of State and the Action to get to the key State.
	 * @param goals An ArrayList of State.
	 * @param distance A Map of State and their associated distance.
	 * @return A LinkedList of Action to solve the Problem.
	 */
	private LinkedList<Action> get_dijkstra_plan(Map<State, State> father, HashMap<State, Action> actions, ArrayList<State> goals, HashMap<State, Integer> distance) 
	{
		LinkedList<Action> plan = new LinkedList<>();
		State goal = this.mapMinValue(distance, goals);


		while(actions.get(goal) != null)
		{
			plan.push(actions.get(goal));
			actions.remove(goal);
			State key = goal;
			goal = father.get(goal);
			father.remove(key);
		}

		return plan;
	}

	/**
	 * Test estimation.
	 * @param currentState The current State.
	 * @param goal The State to attain.
	 * @return The estimation.
	 */
	public int estimation(State currentState, State goal)
	{
		int somme = 0;
		for(Variable variable : currentState.getState().keySet())
		{
			if(goal.getState().containsKey(variable))
			{
				if(!currentState.getState().get(variable).equals(goal.getState().get(variable)))
					somme+=2;
			}
			else
				somme++;
		}

		return somme;
	}


	/**
	 * Searches the minimum state value of the State contained in an iterable object.
	 * @param map A map of State and Integer.
	 * @param open An iterable object containing State.
	 * @return null or the State which value is the smallest.
	 */
	public State mapMinValue(Map<State, Integer> map, Iterable<State> open)
	{
		State result  = null;
		int comp = Integer.MAX_VALUE;

		Iterator<State> iter = open.iterator();
		
		while(iter.hasNext())
		{
			State key = iter.next();
			if(map.containsKey(key))
			{
				if(map.get(key) <= comp)
				{
					comp = map.get(key);
					result = key;
				}
			}

		}

		return result;
	}
	
	public void resetProbe() {
		probeAStar = 0;
		probeDijkstra = 0;
	}

	/**
	 * A star algorithm.
	 * @param problem The Problem to solve.
	 * @return A List of Action to solve the Problem.
	 */
	public LinkedList<Action> aStar(Problem problem)
	{
		HashMap<State,Integer> distance = new HashMap<>();
        HashMap<State,State> father = new HashMap<>();
        HashMap<State,Integer> value  = new HashMap<>();
        Deque<State> open = new LinkedList<>();
		HashMap<State, Action> plan = new HashMap<>();
		State state = new State();

		plan.put(problem.getInitialState(), null);
        distance.put(problem.getInitialState(), 0);
        father.put(problem.getInitialState(),null);
        open.add(problem.getInitialState());
		value.put(problem.getInitialState(), this.estimation(problem.getInitialState(), problem.getGoal()));// to change estimation

		while(!open.isEmpty())
		{
			state = this.mapMinValue(value, open);	

			if(super.satisfies(problem.getGoal(), state))	
				return super.get_bfs_plan(father, plan, state);
			else
			{
				open.remove(state);
				for(Action action : problem.getActions())
				{
					if(super.is_applicable(action, state))
					{
						probeAStar++;
						State next = super.apply(action, state);
						if(!distance.containsKey(next))
							distance.put(next, Integer.MAX_VALUE);

						if(distance.get(next)> distance.get(state) + PlanningProblemWithCost.getCost(action))
						{
							distance.put(next, distance.get(state) + PlanningProblemWithCost.getCost(action));
							value.put(next, distance.get(next) + this.estimation.work(next, problem.getGoal()));
							father.put(next, state); 
							plan.put(next, action);
							if(!open.contains(next))
								open.add(next);
						}
					}
				}
			}
		}

		return null;
	}

	/**
     * Get the number of node traversed with Dijkstra.
     * @return The number of node traversed.
     */
	public int getDijkstraProbe() {
		return probeDijkstra;
	}

   /**
     * Get the number of node traversed with AStar.
     * @return The number of node traversed.
     */
	public int getAStarProbe() {
		return probeAStar;
	}
  
}