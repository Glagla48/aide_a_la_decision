
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

import filrouge.example.HealthCare;
import filrouge.planning.Action;
import filrouge.planning.PlanningProblem;
import filrouge.planning.PlanningProblemWithCost;
import filrouge.planning.Problem;
import filrouge.planning.State;
import filrouge.planning.estimation.*;

import filrouge.utils.Pair;

public class PlanningProblemTest{

	@Test
    public void planningProblem()
    {
		Set<Action> actions = new HashSet<>(HealthCare.sirops);

        Pair<State,State> states = HealthCare.generateNotSoRandomeState();

        Problem problem = new Problem(actions, states.value, states.key);

        System.out.println("Planing Problem : ");
        State initialState = problem.getInitialState();

        PlanningProblem pp = new PlanningProblem();
        LinkedList<Action> res2 = pp.bfs(problem);
        LinkedList<Action> res1 = pp.dfs(problem, initialState, new LinkedList<Action>(), new HashSet<State>()); 
        System.out.println("Initial state: "+problem.getInitialState());
        System.out.println("Goal: "+ problem.getGoal());


        System.out.println("dfs:");

        for(Action a : res1)
        {
            System.out.println(a);
        }
        System.out.println("Nb nodes traversed: " + pp.getDFSProbe());

        System.out.println("bfs:");


        for(Action a : res2)
        {
            System.out.println(a);
		}
		System.out.println("Nb nodes traversed: " + pp.getBFSProbe());
    }

	@Test
    public void planningProblemWithCost()
    {
		Set<Action> actions = new HashSet<>(HealthCare.sirops);

        Pair<State,State> states = HealthCare.generateNotSoRandomeState();

        Problem problem = new Problem(actions, states.value, states.key);

        System.out.println("Planing Problem with cost:");
        Estimation estimation = new InformedEstimation();
        PlanningProblemWithCost pp = new PlanningProblemWithCost(estimation);
        System.out.println("Initial state: "+problem.getInitialState());
        System.out.println("Goal: "+ problem.getGoal());

        LinkedList<Action> res1 = pp.dijkstra(problem);
        LinkedList<Action> res2 = pp.aStar(problem); 
        System.out.println("djk:");
        for(Action a : res1)
        {
            System.out.println(a);
        }
        System.out.println("Nb nodes traversed: " + pp.getDijkstraProbe());
        
        System.out.println("astar:");
        for(Action a : res2)
        {
            System.out.println(a);
        }
		System.out.println("Nb nodes traversed: " + pp.getAStarProbe());

    } 


    public static void main(String[] args)
    {
		PlanningProblemTest test = new PlanningProblemTest();
        test.planningProblem();
        test.planningProblemWithCost();
    }

    
}