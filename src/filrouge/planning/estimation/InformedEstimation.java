package filrouge.planning.estimation;


import filrouge.representations.Variable;
import filrouge.planning.State;

import filrouge.factories.Utilities;


/**
 * inheritDoc
 */
public class InformedEstimation implements Estimation{

    public InformedEstimation(){}

    /**
     * Helpher function of work. It helps calculates the informed estimation between 2 value of Variables wich values are contained in Utilities.LEVEL.
     * @param v1 The value of the first Variable.
     * @param v2 The value of the second Variable.
     * @return The cost to go from the first value to the second.
     */
    public int calculateCost(String v1, String v2)
    {
        int token1 = 0; 
        int token2 = 7;
        int count = 1;

        for(String value : Utilities.LEVEL)
        {
            if(value.equals(v1)) token1 = 1;
            if(value.equals(v2)) token2 = 1;
            if(token1 == token2)break;
            
            count++;
        }

        return count * 2;
    }

    /**
     * {@inheritDoc}
     */
    public int work(State initialState, State goal)
    {
        int cost = 0;
        int count = 0;

        for(Variable variable : initialState.getState().keySet())
		{
			if(goal.getState().containsKey(variable))
			{

                if(!initialState.getState().get(variable).equals(goal.getState().get(variable)))
                {
                    String initialState_value = initialState.getState().get(variable);
                    String goal_value = goal.getState().get(variable);
                    if(initialState_value.equals(Utilities.OUI) || goal_value.equals(Utilities.OUI))
                        cost+=5;
                    else
                        cost+= this.calculateCost(initialState_value, goal_value);

                    count++;
                }
                else
                    cost++;
			}
        }
        
        return cost + count;
    }
}