package filrouge.planning.estimation;


import filrouge.planning.State;

/**
 * Calulates an estimed cost to go from the intialState to the goal State.
 */
public interface Estimation{

    /**
     * Returns an estimation of the cost to go from an itial State to a goal State.
     * @param initialState The initial State.
     * @param goal The goal State.
     * @return An int value of an estimed cost.
     */
    public int work(State initialState, State goal);

}