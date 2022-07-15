package filrouge.planning.estimation;

import filrouge.representations.Variable;
import filrouge.planning.State;


/**
 * {@inheritDoc}
 */
public class SimpleEstimation implements Estimation{

    /**
     * Creates a SimpleEstimation object. 
     */
    public SimpleEstimation(){}

    /**
     * {@inheritDoc}
     */
    public int work(State initialState, State goal)
    {		
        int somme = 0;
		for(Variable variable : initialState.getState().keySet())
		{
			if(goal.getState().containsKey(variable))
			{

                if(!initialState.getState().get(variable).equals(goal.getState().get(variable)))
                {
                    somme+=3;
                }
                else
                    somme++;

			}
		}

		return somme;
    }




}