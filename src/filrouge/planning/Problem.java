package filrouge.planning;

import java.util.Set;

/**
 * A representation of a Problem.
 */
public class Problem {

    /**
     * A Set of Action applicable to the Problem.
     */
    private Set<Action> actions;

    /**
     * The State to reach.
     */
    private State goal;

    /**
     * The initial State.
     */
    private State initialState;

    public Problem(Set<Action> actions, State goal, State initialState) {
        this.setGoal(goal);
        this.setActions(actions);
        this.setInitialState(initialState);
    }

    /**
     * Gets the Set of Action.
     * @return A Set of Action.
     */
    public Set<Action> getActions() {return this.actions;}

    /**
     * Gets the goal.
     * @return A goal State.
     */
    public State getGoal() {return this.goal;}

    /**
     * Gets the initial State.
     * @return The initial State.
     */
    public State getInitialState(){return this.initialState;}


    /**
     * Sets the goal attribute
     * @param goal A goal State.
     */
    public void setGoal(State goal) {this.goal = goal;}

    /**
     * Sets the actions attribute.
     * @param actions A Set of Action.
     */
    public void setActions(Set<Action> actions) {this.actions = actions;}

    /**
     * Sets the initial State attribute.
     * @param initState A State.
     */
    public void setInitialState(State initState){this.initialState = initState;}


    @Override
    public String toString()
    {
        String res = "";
        res += "goal: " + this.goal + " intialState : " + this.initialState;
        return res;
    }

}
