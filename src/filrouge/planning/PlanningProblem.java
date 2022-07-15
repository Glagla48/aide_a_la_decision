package filrouge.planning;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import filrouge.representations.Variable;

/**
 * 
 */
public class PlanningProblem {
	private int probeDFS = 0;
	private int probeBFS = 0;

    public PlanningProblem() {

    }




    /**
     * Returns the assigned value of a certain Variable in a given State.
     * @param v A variable.
     * @param state A State.
     * @return The value of the Variable.
     */
    public String assignment(Variable v, State state)
    {
        return state.getState().get(v);
    }

    public Set<State> eff(State rule)
    {
        Set<State> res = new HashSet<>();
        res.add(rule);
        return res;
    }


    /**
     * Indicates if a partial State can satisify a given State.
     * @param state A State to satisfy.
     * @param partial_state A State.
     * @return true if state can be satisfied, false otherwise.
     */

     
    public boolean satisfies(State partial_state, State state)
    {
        if(partial_state == null || state == null)
        {
            System.out.println("caca");
            System.exit(0);
        }

        for(Variable v : partial_state.getState().keySet())
        {
            if(!state.getState().keySet().contains(v))
                return false;
            if(!this.assignment(v, partial_state).equals(this.assignment(v, state)))
                return  false;
        }
        return true;
    }

    /**
     * Checks if an Action can be applied to a given State.
     * @param action An Action to apply.
     * @param state A State to apply the Action.
     * @return true if the Action is applicable, false otherwise.
     */
    public boolean is_applicable(Action action, State state)
    {
        for(State partial_state : action.getPreconditions())
        {
            //sSystem.out.println("action: " + partial_state + " /State : " + state);
            if(this.satisfies(partial_state, state))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Applies an Action to a given State.
     * @param action An Action.
     * @param state A State.
     * @return A new State where the Action has been applied.
     */
    public State apply(Action action, State state)
    {
        State newState = new State(state);
        
        if(this.is_applicable(action, state))
        {
            for(State rule : action.getPreconditions())
            {
                if(this.satisfies(rule, state))
                {
                        newState.addState(action.getEffet(rule));
                }
            }
        }
        return newState;
    }

	public void resetProbes() {
		probeBFS = 0;
		probeDFS = 0;
	}

    /**
     * Searches for the solution of a given Problem.
     * @param problem The Problem to solve.
     * @param state A State to verify.
     * @param plan A List of Action.
     * @param closed A Set of explored State.
     * @return A LinkedList of Action to apply to the initial State to solve the Problem.
     */
    public LinkedList<Action> dfs(Problem problem, State state, LinkedList<Action> plan, Set<State> closed)
    {
        if(satisfies(state, problem.getGoal()))
            return plan;
        else{
            for(Action action : problem.getActions())
            {
                if(is_applicable(action, state))
                {
					probeDFS++;
                    State next = this.apply(action, state);
                    if(!closed.contains(next))
                    {
                        plan.add(action);
                        closed.add(next);
                        LinkedList<Action> subPlan = this.dfs(problem, next, plan, closed);

                        if(!subPlan.isEmpty())
                        {
                            return subPlan;
                        }
                        else
                        {
                            plan.pop();
                        }
                    }
                }
            }
            return plan;
        }

    }
    
    /**
     * Searches for the solution of a given Problem.
     * @param problem A Problem to solve.
     * @return A LinkeList of Action to solve the initial State of the Problem.
     */
    public LinkedList<Action> bfs(Problem problem) {
        Map<State,State> father = new HashMap<>();
        Map<State,Action> plan  = new HashMap<>();
        Queue<State> open = new LinkedList<>();
        Queue<State> closed = new LinkedList<>();
        State next = problem.getInitialState();
        open.add(problem.getInitialState());
        father.put(problem.getInitialState(), null);
        while(!open.isEmpty()){
            State state = open.poll();
            closed.add(state);
            for(Action action: problem.getActions()) {
                if(is_applicable(action, state))
                {
					probeBFS++;
                    next = apply(action, state);
                    if(!closed.contains(next) && !open.contains(next))
                    {
                        father.put(next, state);
                        plan.put(next, action);
                        if(satisfies(next, problem.getGoal()))
                        {
                            return get_bfs_plan(father,plan,next);
                        }
                        else
                        {
                            open.add(next);
                        }
                    }
                }

            }
        }
        System.out.println("je vais return nul");
        return null;
    }


    /**
     * Returns the solution of bfs.
     * @param father A Map of parrent State and child State.
     * @param action A Map of State and Action that indicates which Action led to the key State.
     * @param goal The current goal State.
     * @return  A LinkeList of Action to solve the initial State of the Problem of bfs.
     */
    public LinkedList<Action> get_bfs_plan(Map<State, State> father, Map<State, Action> action, State goal){
        LinkedList<Action> plan = new LinkedList<>();

        while(action.get(goal) != null){

            plan.add(action.get(goal)); 
            //System.out.println("action :" + father.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(plan);
        return plan;
    }

	/**
     * Get the number of node traversed with DFS.
     * @return The number of node traversed.
     */
	public int getDFSProbe() {
		return probeDFS;
	}

   /**
     * Get the number of node traversed with BFS
     * @return The number of node traversed.
     */
	public int getBFSProbe() {
		return probeBFS;
	}

}